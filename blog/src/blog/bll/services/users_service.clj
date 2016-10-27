(ns blog.bll.services.users-service
	(:require [blog.bll.services-protocols.common-protocol :as common-protocol]
			  [blog.bll.services-protocols.users-protocol :as users-protocol]
			  [blog.dal.repos.users-repo :as users-repo]))

(deftype users-service [users-repo] 
	common-protocol/common-service-protocol

	(get-items [this] 
		(.get-items users-repo))

	(get-item [this id]
		(.get-item users-repo id))

	(insert-item [this newItem]
		(.insert-item users-repo newItem))

	(update-item [this updatedItem]
		(.update-item users-repo updatedItem))

	(delete-item [this id]
		(.delete-item users-repo id))

	users-protocol/users-service-protocol

	(get-by-login [this login]
		(.get-by-login users-repo login))

	(get-seed-by-id [this id]
		(.get-seed-by-id users-repo id))

	(get-seed-by-login [this login]
		(.get-seed-by-login users-repo login))

	(delete-by-login [this login] 
		(.delete-by-login users-repo login)))