(ns blog.dal.protocols.users-protocol)

(defprotocol users-repository-protocol
	(get-user-by-login [this login])
	(get-seed-by-id [this id])
	(get-seed-by-login [this login])
	(delete-user-by-login [this login]))