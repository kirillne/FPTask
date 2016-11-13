(ns blog.bll.protocols.posts-protocol)

(defprotocol posts-service-protocol
	(get-posts-by-user-id [this user-id])
	(get-posts-by-creation-date [this creation-date])
)