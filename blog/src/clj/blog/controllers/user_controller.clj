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
  (layout/render
    request "user/user.html" {:info info :messages messages}))

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
		user (get-item user-repository {:id id})
		posts (get-posts-with-comments-count post-repository id)
		]
	(if (nil? user)
		(redirect "/error")
		(user-page request (get-posts-info posts id) nil))))

