(ns blog.dal.repo.commetns-repo
	(:require [blog.dal.repo-protocols.common-protocol :as common-protocol]
			  [blog.dal.repo-protocols.commetns-protocol :as commetns-protocol]
			  [blog.dal.dto.comment :as commetns-dto]
			  [blog.dal.db :as db]
			  [clojure.java.jdbc :as jdbc]))

(deftype comments-repo []

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol
	
	(get-items [this]
		(jdbc/query db/db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments"]
			(commetns-dto/->comment
				:id
				:user-id
				:text
				:creation-date
				:rating
				:post-id
				)
		)
	)
	
	(get-item [this id]
		(jdbc/query db/db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments WHERE Id = ?" id]
			(commetns-dto/->comment
				:id
				:user-id
				:text
				:creation-date
				:rating
				:post-id)
		)
	)
	
	(insert-item [this newItem]
		(jdbc/insert! db/db-spec :Comments
		{
			:userid (:user-id newItem)
			:text (:text newItem)
			:creationdate (:creation-date newItem)
			:postid (:post-id newItem)
		}
		)
	)

	(update-item [this updatedItem]
		(jdbc/update! db/db-spec :Comments
		{
			:userid (:user-id updatedItem)
			:text (:text updatedItem)
			:creationdate (:creation-date updatedItem)
			:postid (:post-id updatedItem)
		}
		["Id = ?" (:id updatedItem)]
		)
	)
	
	(delete-item [this id]
		(jdbc/delete! db/db-spec :Comments
		["Id = ?" id])
	)
	
	
	;;comments-repo-protocol implementaiton
	comments-protocol/comments-repo-protocol
	
	
	(get-by-user-id [this user-id]
		(jdbc/query db/db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments WHERE UserId = ?" user-id]
			(commetns-dto/->comment
				:id
				:user-id
				:text
				:creation-date
				:rating
				:post-id
			)
		)
	)
	
	(get-by-post-id [this post-id]
		(jdbc/query db/db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments WHERE PostId = ?" post-id]
			(commetns-dto/->comment
				:id
				:user-id
				:text
				:creation-date
				:rating
				:post-id
			)
		)
	)
	
	(delete-by-post-id [this post-id]
		(jdbc/delete! db/db-spec :Comments
		["PostId = ?" post-id])
	)
	
)			  