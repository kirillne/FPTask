(ns blog.dal.repos.posts-repo
	(:require [blog.dal.repos-protocols.common-protocol :as common-protocol]
			  [blog.dal.repos-protocols.posts-protocol :as posts-protocol]
			  [blog.dal.dto.post :as posts-dto]
			  [clojure.java.jdbc :as jdbc]))

(deftype posts-repo [db-spec]

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol

	(get-items [this] 
		(jdbc/query db-spec 
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts"]
             {:row-fn #(posts-dto/->post 
             	(:id %1)
             	(:name %1) 
             	(:creationdate %1)
             	(:userid %1)
             	(:text %1)
             	(:rating %1))}))

	(get-item [this id]
		(jdbc/query db-spec
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts WHERE Id = ?" id]
             {:row-fn #(posts-dto/->post 
                  (:id %1)
                  (:name %1) 
                  (:creationdate %1)
                  (:userid %1)
                  (:text %1)
                  (:rating %1))}))

	(insert-item [this newItem]
		(jdbc/insert! db-spec :Posts 
			{
				:name (:name newItem) 
				:creationdate (:creation-date newItem) 
				:userid  (:user-id  newItem) 
				:text (:text newItem) }))

	(update-item [this updatedItem] 
		(jdbc/update! db-spec :Posts 
			{
				:name (:name updatedItem) 
                        :creationdate (:creation-date updatedItem) 
                        :userid  (:user-id  updatedItem) 
                        :text (:text updatedItem) }
			["Id = ?" (:id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db-spec :Posts ["Id = ?" id]))

	;;post-repo-protocol implementation
	posts-protocol/posts-repo-protocol

	(get-by-user-id [this user-id]
		(jdbc/query db-spec
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts WHERE UserId = ?" user-id]
             {:row-fn #(posts-dto/->post 
                  (:id %1)
                  (:name %1) 
                  (:creationdate %1)
                  (:userid %1)
                  (:text %1)
                  (:rating %1))}))

      (get-by-creation-date [this creation-date]
            (jdbc/query db-spec
             ["SELECT Id, Name, CreationDate, UserId, Text, Rating FROM Posts WHERE CreationDate = ?" creation-date]
             {:row-fn #(posts-dto/->post 
                  (:id %1)
                  (:name %1) 
                  (:creationdate %1)
                  (:userid %1)
                  (:text %1)
                  (:rating %1))}))
)