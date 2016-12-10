(ns blog.controllers.user-controller
	(:use 
		[blog.dal.protocols.common-protocol]
		[blog.dal.protocols.users-protocol]
		[blog.dal.protocols.posts-protocol]
		[blog.dal.protocols.profiles-protocol]
		[blog.dal.protocols.comments-protocol])
	(:require 
		[blog.dal.repositories.users-repository]
		[blog.dal.repositories.posts-repository]
		[blog.dal.repositories.profiles-repository]
		[blog.dal.repositories.comments-repository]
		[blog.layout :as layout]
		[bouncer.core :as b]
		[clojure.string :refer [blank?]]
        [bouncer.validators :as v]
        [blog.utils.messages-utils :as utils]
        [ring.util.response :refer [redirect]])
	(:import  
		[blog.dal.repositories.users_repository users-repository]
		[blog.dal.repositories.posts_repository posts-repository]
		[blog.dal.repositories.profiles_repository profiles-repository]
		[blog.dal.repositories.comments_repository comments-repository]))

(defn users-page [request users]
	(layout/render
		request "user/users.html" {:users users}))

(defn user-page [request info messages]
  ( 
  	layout/render
    request "user/user.html" {:info info :messages messages})
 )

(defn profile-page [request profile messages]
  (layout/render
    request "user/profile.html" {:profile profile :messages messages}))

(def post-repository (posts-repository.))
(def profile-repository (profiles-repository.))
(def comment-repository (comments-repository.))
(def user-repository (users-repository.))

(defn get-posts-info [posts user-id]
	(let [
		user (get-full-user-info user-repository user-id)
		]
	{:posts posts :profile user}))

(defn view-users [request]
	(let [
		  users (get-full-users-info user-repository)]
	(users-page request users)))

(defn view-user [request id] 
	(let [
		identity-id  (:identity (:session request))
		user (get-item user-repository {:id id})
		posts (get-posts-with-comments-count-restricted post-repository id identity-id)
		]
	(if (nil? user)
		(redirect "/error")
		(user-page request (get-posts-info posts id) nil))))

(defn view-profile [request id]
	(let [
		identity-id  (:identity (:session request))
		]
	(if (= (str identity-id) id)
		(profile-page request (get-full-user-info user-repository id) nil)
		(redirect "/error"))))

(defn add-nil-members [user] 
	(let [
		user-name (if-not (contains? user :name)
					(assoc user :name nil) 
					user)
		user-surname (if-not (contains? user :surname)
					(assoc user-name :surname nil) 
					user-name)
		user-birth-date (if (or (not (contains? user :birth-date)) (blank? (:birth-date user)))
				    (assoc user-surname :birth-date nil) 
					user-surname)
		user-sex (if-not (contains? user :sex)
				    (assoc user-birth-date :sex nil) 
					user-birth-date)
		user-country (if-not (contains? user :country)
				    (assoc user-sex :country nil) 
					user-sex)
		user-city (if-not (contains? user :city)
				    (assoc user-country :city nil) 
					user-country)
		user-address (if-not (contains? user :address)
				    (assoc user-city :address nil) 
					user-city)
		user-email (if-not (contains? user :email)
				    (assoc user-address :email nil) 
					user-address)
		]
	user-email
))

(defn validate-profile [profile] (b/validate profile
									:email [[v/email :pre (comp not blank? :email)]]
									:birth-date [[v/datetime :pre (comp not blank? :birth-date)]]))

(defn update-profile [request id] 
	(let [
		profile (add-nil-members (:params request))
		validation-result (first (validate-profile profile))]
	(if (nil? validation-result)
		(do
			(update-item profile-repository profile)
			(profile-page request (get-full-user-info user-repository id) (utils/fix-validation-messages {:success "((update completed successfully))"})))
		(profile-page request (get-full-user-info user-repository id) (utils/fix-validation-messages validation-result)))))

