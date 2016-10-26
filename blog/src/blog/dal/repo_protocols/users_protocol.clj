(ns blog.dal.repo-protocols.users-protocol)

(defprotocol users-repo-protocol
	(get-by-login [this login])
	(get-seed-by-id [this id])
	(get-seed-by-login [this login])
	(delete-by-login [this login]))