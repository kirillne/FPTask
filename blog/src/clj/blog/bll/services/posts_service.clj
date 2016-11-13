(ns blog.bll.services.posts-service
	(:use     [blog.dal.protocols.posts-protocol])
	(:require [blog.bll.protocols.common-protocol :refer [common-service-protocol]]
			  [blog.bll.protocols.posts-protocol :refer [posts-service-protocol]]
			  [blog.bll.services.common-service :refer [common-service]]
              [blog.dal.repositories.posts-repository])
  	(:import  [blog.dal.repositories.posts_repository posts-repository]))

(def repository (posts-repository.))

(deftype posts-service [] 

	posts-service-protocol

	(get-posts-by-user-id [this user-id] (get-posts-by-user-id repository user-id))
	(get-posts-by-creation-date [this creation-date] (get-posts-by-creation-date repository creation-date))
)

(extend posts-service
	common-service-protocol
	(common-service repository))