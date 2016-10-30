(ns blog.dal.repos.comments-repo
	(:require [blog.dal.repos-protocols.common-protocol :as common-protocol]
			  [blog.dal.repos-protocols.comments-protocol :as comments-protocol]
			  [blog.dal.dto.comment-rec :as comments-dto]
			  [clojure.java.jdbc :as jdbc]))

(deftype comments-repo [db-spec]

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol
	
	(get-items [this]
		(jdbc/query db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments"]
			(comments-dto/->comment-rec
				:id
				:userid
				:text 
				:creationdate
				:rating
				:postid)))
	
	
	
	(get-item [this id]
		(jdbc/query db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments WHERE Id = ?" id]
			(comments-dto/->comment-rec
				:id
				:userid
				:text 
				:creationdate
				:rating
				:postid)))
	
	
	(insert-item [this newItem]
		(jdbc/insert! db-spec :Comments
		{
			:userid (:user-id newItem)
			:text (:text newItem)
			:creationdate (:creation-date newItem)
			:postid (:post-id newItem)
		}
		)
	)

	(update-item [this updatedItem]
		(jdbc/update! db-spec :Comments
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
		(jdbc/delete! db-spec :Comments
		["Id = ?" id])
	)
	
	
	;;comments-repo-protocol implementaiton
	comments-protocol/comments-repo-protocol
	
	
	(get-by-user-id [this user-id]
		(jdbc/query db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments WHERE UserId = ?" user-id]
			(comments-dto/->comment-rec
				:id
				:userid
				:text 
				:creationdate
				:rating
				:postid)))
	
	
	(get-by-post-id [this post-id]
		(jdbc/query db-spec
			["SELECT Id , UserId, Text, CreationDate, Rating, PostId FROM Comments WHERE PostId = ?" post-id]
			(comments-dto/->comment-rec
				:id
				:userid
				:text 
				:creationdate
				:rating
				:postid)))
	
	
	(delete-by-post-id [this post-id]
		(jdbc/delete! db-spec :Comments
		["PostId = ?" post-id])
	)
	
)			  