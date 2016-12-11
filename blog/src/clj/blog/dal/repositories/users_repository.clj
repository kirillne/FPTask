(ns blog.dal.repositories.users-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.user :as user-dto]
			  [blog.dal.dto.user-full :as user-full-dto]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.users-protocol :refer [users-repository-protocol]]))

(defn convert [item] 
	(if (nil? item)
	nil
	(user-dto/->user (:id item) (:login item) (:password item) (:seed item))))

(defn convert-to-full [item] 
	(if (nil? item)
	nil
	(user-full-dto/->user-full (:id item) (:login item) (:password item) (:seed item) (:name item) (:surname item) (:birth_date item) (:sex item) (:country item) (:city item) (:address item) (:email item))))

(deftype users-repository []
	users-repository-protocol
	(get-user-by-login [this login] (convert (db/get-user-by-login {:login login})))
	(get-seed-by-id [this id] (db/get-seed-by-id {:id id}))
	(get-seed-by-login [this login] (db/get-seed-by-login {:login login}))
	(delete-user-by-login [this login] (db/delete-user-by-login {:login login}))
	(get-full-users-info [this] (into [] (map convert-to-full (db/get-full-users-info))))
	(get-full-user-info [this user-id] (convert-to-full (db/get-full-user-info {:user-id user-id})))
	(get-user-rating [this user-id] (db/get-user-rating {:user-id user-id}))
)

(extend users-repository
 	common-repository-protocol
  	(common-repository "user" convert))