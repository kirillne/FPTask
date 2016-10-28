(ns blog.bll.services-protocols.posts-protocol)

(defprotocol posts-service-protocol
	(get-by-user-id [this user-id])
	(get-by-creation-date [this creation-date])
)