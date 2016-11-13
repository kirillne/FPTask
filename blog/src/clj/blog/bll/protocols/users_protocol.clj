(ns blog.bll.protocols.users-protocol)

(defprotocol users-service-protocol
	(get-user-by-login [this login])
	(get-seed-by-id [this id])
	(get-seed-by-login [this login])
	(delete-user-by-login [this login]))