(ns blog.controllers.comments-controller
	(:use 
		[blog.dal.protocols.common-protocol]
		[blog.dal.protocols.posts-protocol]
		[blog.dal.protocols.profiles-protocol]
		[blog.dal.protocols.comments-protocol])
	(:require 
		[blog.dal.repositories.posts-repository]
		[blog.dal.repositories.profiles-repository]
		[blog.dal.repositories.comments-repository]
		[blog.layout :as layout]
		[bouncer.core :as b]
        [bouncer.validators :as v]
        [blog.utils.messages-utils :as utils]
        [ring.util.response :refer [redirect]])
	(:import  
		[blog.dal.repositories.posts_repository posts-repository]
		[blog.dal.repositories.profiles_repository profiles-repository]
		[blog.dal.repositories.comments_repository comments-repository]))
		
(defn add-comment-page [request post-id]
	(layout/render
		request "comment/comment.html" {:postid post-id})
)

(defn edit-comment-page [request info]
	(layout/render
		request "comment/comment.html" {:postid (:post-id info) :info info})
)

(def comment-repository (comments-repository.))
(defn now [] (new java.util.Date))

(defn add-comment [request post-id user-id]
	(
		do(create-item comment-repository 
				{
				:id nil 
				:user-id user-id :text (get-in request [:form-params "text"]) :creation-date (now) :post-id post-id})
		
			(let [next-url (str "/posts/" post-id)]
				(-> (redirect next-url))
			)		
	)
)

(defn edit-comment-view [request post-id id]
	(let 
		[
		comment-rec (get-item comment-repository {:id id})
		]
		(print id)
		(if (nil? comment-rec)
			(->(redirect "/error"))
			(edit-comment-page request comment-rec)
		)
		
	)	
) 

(defn edit-comment [request post-id id]
	(do
		(
			let
			[
			text (get-in request [:form-params "text"])
			comment-rec {:text text}
			]
			(update-item comment-repository (assoc comment-rec :id id))
			(let [next-url (str "/posts/" post-id)]
				(-> (redirect next-url))
			)
		)
	)
)

(defn delete-comment [request post-id id]
	(do
		(
			delete-item comment-repository {:id id}
		)
		(let [next-url (str "/posts/" post-id)]
				(-> (redirect next-url))
			)
	
	)
)

(defn add-rating [request user-id comment-id value post-id]
	(let [
		exists (get-comment-existing comments-repository user-id comment-id)
		bool-value (if (= value "1") (boolean true) (boolean false) )] 
		(if (nil? exists) 
			(add-comment-rating comments-repository user-id comment-id bool-value) 
			(update-comment-rating comments-repository user-id comment-id bool-value))
		(redirect (str "/posts/" post-id))
	)
)