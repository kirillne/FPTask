(ns blog.bll.services.users-service
	(:use     [blog.dal.protocols.users-protocol])
	(:require [blog.bll.protocols.common-protocol :refer [common-service-protocol]]
			  [blog.bll.protocols.users-protocol :refer [users-service-protocol]]
			  [blog.bll.services.common-service :refer [common-service]]
              [blog.dal.repositories.users-repository])
  	(:import  [blog.dal.repositories.users_repository users-repository]))

(def repository (users-repository.))

(deftype users-service [] 

	users-service-protocol

	(get-user-by-login [this login] (get-user-by-login repository login))
	(get-seed-by-id [this id] (get-seed-by-id repository id))
	(get-seed-by-login [this login] (get-seed-by-login repository login))
	(delete-user-by-login [this login] (delete-user-by-login repository login))
)

(extend users-service
	common-service-protocol
	(common-service repository))