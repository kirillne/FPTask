(ns blog.dal.repo-protocols.users-protocol)

(defprotocol users-repo-protocol
	(get-by-login [this login])
	(get-seed [this id login])
	(delete-by-login [this login]))