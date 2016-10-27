(ns blog.dal.repos.users-repo
	(:require [blog.dal.repos-protocols.common-protocol :as common-protocol]
			  [blog.dal.repos-protocols.users-protocol :as users-protocol]
			  [blog.dal.dto.user :as user-dto]
			  [clojure.java.jdbc :as jdbc]))

(deftype users-repo [db-spec]

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol

	(get-items [this] 
		(jdbc/query db-spec 
             ["SELECT * FROM Users"]))

	(get-item [this id]
		(jdbc/query db-spec
             ["SELECT * FROM Users WHERE Id = ?" id]))

	(insert-item [this newItem]
		(jdbc/insert! db-spec :Users 
			{:login (:login newItem) :password (:password newItem) :seed (:seed newItem)}))

	(update-item [this updatedItem] 
		(jdbc/update! db-spec :Users 
			{:login (:login updatedItem) :password (:password updatedItem) :seed (:seed updatedItem)}
			["Id = ?" (:id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db-spec :Users ["Id = ?" id]))

	;;users-repo-protocol implementation 
	users-protocol/users-repo-protocol

	(get-by-login [this login]
		(jdbc/query db-spec 
             ["SELECT * FROM Users WHERE Login = ?" login]))

	(get-seed-by-id [this id]
		(jdbc/query db-spec 
             ["SELECT * FROM Users WHERE Id = ?" id] 
             {:row-fn :seed}))

	(get-seed-by-login [this login]
		(jdbc/query db-spec 
             ["SELECT * FROM Users WHERE Login = ?" login] 
             {:row-fn :seed}))

	(delete-by-login [this login]
		(jdbc/delete! db-spec :Users ["Login = ?" login])))