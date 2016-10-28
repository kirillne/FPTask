(ns blog.bll.services.posts-service
	(:require [blog.bll.services-protocols.common-protocol :as common-protocol]
			  [blog.bll.services-protocols.posts-protocol :as posts-protocol]
			  [blog.dal.repos.posts-repo :as posts-repo]))

(deftype posts-service [posts-repo] 
	common-protocol/common-service-protocol

	(get-items [this] 
		(.get-items posts-repo))

	(get-item [this id]
		(first (.get-item posts-repo id)))

	(insert-item [this newItem]
		(.insert-item posts-repo newItem))

	(update-item [this updatedItem]
		(.update-item posts-repo updatedItem))

	(delete-item [this id]
		(.delete-item posts-repo id))

	posts-protocol/posts-service-protocol

	(get-by-user-id [this user-id]
		(.get-by-user-id posts-repo user-id))

	(get-by-creation-date [this creation-date]
		(.get-by-creation-date creation-date creation-date)))