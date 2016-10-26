(ns blog.dal.repo.users-repo
	(:require [blog.dal.repo-protocols.common-protocol :as common-protocol]
			  [blog.dal.repo-protocols.users-protocol :as users-protocol]
			  [blog.dal.dto.user :as user-dto]
			  [blog.dal.db :as db]
			  [clojure.java.jdbc :as jdbc]))

(deftype users-repo []

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol

	(get-items [this] 
		(jdbc/query db/db-spec 
             ["SELECT * FROM Users"]
             (user-dto/->user :id :login :password :seed)))

	(get-item [this id]
		(jdbc/query db/db-spec
             ["SELECT * FROM Users WHERE Id = ?" id]
             (user-dto/->user :id :login :password :seed)))

	(insert-item [this newItem]
		(jdbc/insert! db/db-spec :Users 
			{:login (:login newItem) :password (:password newItem) :seed (:seed newItem)}))

	(update-item [this updatedItem] 
		(jdbc/update! db/db-spec :Users 
			{:login (:login updatedItem) :password (:password updatedItem) :seed (:seed updatedItem)}
			["Id = ?" (:id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db/db-spec :Users ["Id = ?" id]))

	;;users-repo-protocol implementation 
	users-protocol/users-repo-protocol

	(get-by-login [this login]
		(jdbc/query db/db-spec 
             ["SELECT * FROM Users WHERE Login = ?" login]))

	(get-seed-by-id [this id]
		(jdbc/query db/db-spec 
             ["SELECT * FROM Users WHERE Id = ?" id] 
             {:row-fn :seed}))

	(get-seed-by-login [this login]
		(jdbc/query db/db-spec 
             ["SELECT * FROM Users WHERE Login = ?" login] 
             {:row-fn :seed}))

	(delete-by-login [this login]
		(jdbc/delete! db/db-spec :Users ["Login = ?" login])))