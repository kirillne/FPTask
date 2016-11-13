(ns blog.bll.services.comments-service
	(:use     [blog.dal.protocols.comments-protocol])
	(:require [blog.bll.protocols.common-protocol :refer [common-service-protocol]]
			  [blog.bll.protocols.comments-protocol :refer [comments-service-protocol]]
			  [blog.bll.services.common-service :refer [common-service]]
              [blog.dal.repositories.comments-repository])
  	(:import  [blog.dal.repositories.comments_repository comments-repository]))

(def repository (comments-repository.))

(deftype comments-service [] 

	comments-service-protocol

	(get-comments-by-user-id [this user-id] (get-comments-by-user-id repository user-id))
	(get-comments-by-post-id [this post-id] (get-comments-by-post-id repository post-id))
	(delete-comment-by-post-id [this post-id] (delete-comment-by-post-id repository post-id))
)

(extend comments-service
	common-service-protocol
	(common-service repository))