(ns blog.dal.repo.users-repo
	(:require [blog.dal.repo-protocols.common-protocol :as common-protocol]
			  [blog.dal.db :as db]
			  [clojure.java.jdbc :as jdbc]))

(deftype users-repo []
	common-protocol/common-repo-protocol

	(get-items [this] 
		(jdbc/query db/db-spec 
             ["SELECT * FROM Users"]))

	(get-item [this id]
		(jdbc/query db/db-spec
             ["SELECT * FROM Users WHERE Id = ?" id]))

	(insert-item [this newItem] (+ 1 2))

	(update-item [this id updatedItem] (+ 1 2))

	(delete-item [this id]
		(jdbc/delete! db/db-spec :Users ["Id = ?" id])))