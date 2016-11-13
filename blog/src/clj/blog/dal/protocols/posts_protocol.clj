(ns blog.dal.protocols.posts-protocol)

(defprotocol posts-repository-protocol
	(get-posts-by-user-id [this user-id])
	(get-posts-by-creation-date [this creation-date])
)