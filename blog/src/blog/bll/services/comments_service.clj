(ns blog.bll.services.comments-service
	(:require [blog.bll.services-protocols.common-protocol :as common-protocol]
	         [blog.bll.services-protocols.comments-protocol :as comments-protocol]
			 [blog.dal.repos.comments-repo :as comments-repo]))
			 
(deftype comments-service [comments-repo]
	common-protocol/common-service-protocol
	
	(get-items [this]
		(.get-items comments-repo))
	
	(get-item [this id]
		(first (.get-item comments-repo id)))
	
	(insert-item [this newItem]
		(.insert-item comments-repo newItem))

	(update-item [this updatedItem]
		(.update-item comments-repo updatedItem))
	
	(delete-item [this id]
		(.delete-item comments-repo id))
	
	
	comments-protocol/comments-service-protocol
	
	(get-by-user-id [this user-id]
		(.get-by-user-id comments-repo user-id))
	
	(get-by-post-id [this post-id]
		(.get-by-post-id comments-repo post-id))
	
	(delete-by-post-id [this post-id]
		(.delete-by-post-id comments-repo post-id)))