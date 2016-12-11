(ns blog.dal.protocols.users-protocol)

(defprotocol users-repository-protocol
	(get-user-by-login [this login])
	(get-seed-by-id [this id])
	(get-seed-by-login [this login])
	(delete-user-by-login [this login])
	(get-full-users-info [this])
	(get-full-user-info [this user-id])
	(get-user-rating [this user-id])
)