(ns blog.dal.repo-protocols.common-protocol)

(defprotocol common-protocol
	(get-items [this])
	(get-item [this id])
	(insert [this newItem])
	(update [this id updatedItem])
	(delete [this id]))