(ns blog.controllers.post-controller
	(:use 
		[blog.dal.protocols.common-protocol]
		[blog.dal.protocols.posts-protocol]
		[blog.dal.protocols.profiles-protocol]
		[blog.dal.protocols.comments-protocol])
	(:require 
		[blog.dal.cache.posts-cache-repository]
		[blog.dal.repositories.profiles-repository]
		[blog.dal.repositories.comments-repository]
		[blog.layout :as layout]
		[bouncer.core :as b]
        [bouncer.validators :as v]
        [blog.utils.messages-utils :as utils]
        [ring.util.response :refer [redirect]])
	(:import  
		[blog.dal.cache.posts_cache_repository posts-cache-repository]
		[blog.dal.repositories.profiles_repository profiles-repository]
		[blog.dal.repositories.comments_repository comments-repository]))

(defn add-post-page [request post messages]
  (layout/render
    request "post/post-form.html" {:post post :messages messages}))

(defn view-post-page [request info messages]
  (layout/render
    request "post/post.html" {:info info :messages messages}))

(def post-repository (posts-cache-repository.))
(def profile-repository (profiles-repository.))
(def comment-repository (comments-repository.))

(defn validate-post [post] (b/validate post
									:name v/required))

(defn now [] (new java.util.Date))

(defn add-post [request] 
	(let [
		name (get-in request [:form-params "name"])
		text (get-in request [:form-params "text"])
		post {:name name :text text :user-id (:identity (:session request)) :creation-date (now)}
		validation-result (first (validate-post post))]
		(if (nil? validation-result)
			(redirect (str "/posts/" (create-item post-repository (assoc post :id nil))))
			(add-post-page request post (utils/fix-validation-messages validation-result)))))

(defn get-edit-post [request post-id] 
	(let [
		post (get-item post-repository {:id post-id})]
		(if (nil? post)
			(redirect "/error")
			(add-post-page request post nil))))

(defn edit-post [request post-id] 
	(let [
		name (get-in request [:form-params "name"])
		text (get-in request [:form-params "text"])
		from-db (get-item post-repository {:id post-id})
		post  (assoc from-db :name name :text text)
		validation-result (first (validate-post post))]
		(if (nil? validation-result)
			(do
				(update-item post-repository (assoc post :id post-id))
				(redirect (str "/posts/" post-id))
			)
			(add-post-page request post (utils/fix-validation-messages validation-result)))))

(defn get-comment-info [comment]
	(let [
		user (get-item profile-repository {:id (:user-id comment)})]
		(assoc comment :user user)))

(defn get-post-info [post]
	(let [
		user (get-item profile-repository {:id (:user-id post)})
		comments (into [] (map get-comment-info (get-comments-by-post-id comment-repository (:id post))))]
	{:post post :user user :comments comments}))


(defn view-post [request post-id] 
	(let [
		post (get-item post-repository {:id post-id})]
		(if (nil? post)
			(redirect "/error")
			(view-post-page request (get-post-info post) nil))))
