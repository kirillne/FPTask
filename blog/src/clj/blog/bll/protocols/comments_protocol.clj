(ns blog.bll.protocols.comments-protocol)

(defprotocol comments-service-protocol
	(get-comments-by-user-id [this user-id])
	(get-comments-by-post-id [this post-id])
	(delete-comment-by-post-id [this post-id]))