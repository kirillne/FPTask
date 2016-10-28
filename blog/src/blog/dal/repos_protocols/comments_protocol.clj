(ns blog.dal.repos-protocols.comments-protocol)

(defprotocol comments-repo-protocol
	(get-by-user-id [this user-id])
	(get-by-post-id [this post-id])
	(delete-by-post-id [this post-id])
)