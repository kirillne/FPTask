(ns blog.bll.services-protocols.comments-protocol)

(defprotocol comments-service-protocol
	(get-by-user-id [this user-id])
	(get-by-post-id [this post-id])
	(delete-by-post-id [this post-id]))