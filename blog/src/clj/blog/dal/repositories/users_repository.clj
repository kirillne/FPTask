(ns blog.dal.repositories.users-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.user :as user-dto]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.users-protocol :refer [users-repository-protocol]]))

(defn convert [item] 
	(if (nil? item)
	nil
	(user-dto/->user (:id item) (:login item) (:password item) (:seed item))))

(deftype users-repository []
	users-repository-protocol
	(get-user-by-login [this login] (convert (db/get-user-by-login {:login login})))
	(get-seed-by-id [this id] (db/get-seed-by-id {:id id}))
	(get-seed-by-login [this login] (db/get-seed-by-login {:login login}))
	(delete-user-by-login [this login] (db/delete-user-by-login {:login login})))

(extend users-repository
 	common-repository-protocol
  	(common-repository "user" convert))