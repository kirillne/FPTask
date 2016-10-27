(ns blog.dal.repo.posts-repo
	(:require [blog.dal.repo-protocols.common-protocol :as common-protocol]
			  [blog.dal.repo-protocols.posts-protocol :as posts-protocol]
			  [blog.dal.dto.post :as posts-dto]
			  [blog.dal.db :as db]
			  [clojure.java.jdbc :as jdbc]))

(deftype posts-repo []

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol

	(get-items [this] 
		(jdbc/query db/db-spec 
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts"]
             (posts-dto/->post 
             	:id
             	:name 
             	:creation-date 
             	:user-id 
             	:text 
             	:rating)))

	(get-item [this id]
		(jdbc/query db/db-spec
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts WHERE Id = ?" id]
             (posts-dto/->post 
                  :id
                  :name 
                  :creation-date 
                  :user-id 
                  :text 
                  :rating)))

	(insert-item [this newItem]
		(jdbc/insert! db/db-spec :Posts 
			{
				:name (:name newItem) 
				:creationdate (:creation-date newItem) 
				:userid  (:user-id  newItem) 
				:text (:text newItem) }))

	(update-item [this updatedItem] 
		(jdbc/update! db/db-spec :Posts 
			{
				:name (:name updatedItem) 
                        :creationdate (:creation-date updatedItem) 
                        :userid  (:user-id  updatedItem) 
                        :text (:text updatedItem) }
			["Id = ?" (:id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db/db-spec :Profiles ["Id = ?" id]))

	;;post-repo-protocol implementation
	posts-protocol/posts-repo-protocol

	(get-by-user-id [this user-id]
		(jdbc/query db/db-spec
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts WHERE UserId = ?" user-id]
             (posts-dto/->post 
                  :id
                  :name 
                  :creation-date 
                  :user-id 
                  :text 
                  :rating)))

      (get-by-creation-date [this creation-date]
            (jdbc/query db/db-spec
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts WHERE CreationDate = ?" creation-date]
             (posts-dto/->post 
                  :id
                  :name 
                  :creation-date 
                  :user-id 
                  :text 
                  :rating)))
)