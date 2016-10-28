(ns blog.dal.repos-protocols.posts-protocol)

(defprotocol posts-repo-protocol
	(get-by-user-id [this user-id])
	(get-by-creation-date [this creation-date])
)