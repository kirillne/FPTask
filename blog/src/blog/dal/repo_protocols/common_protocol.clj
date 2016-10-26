(ns blog.dal.repo-protocols.common-protocol)

(defprotocol common-repo-protocol
	(get-items [this])
	(get-item [this id])
	(insert-item [this newItem])
	(update-item [this updatedItem])
	(delete-item [this id]))