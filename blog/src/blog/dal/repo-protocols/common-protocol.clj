(ns blog.dal.repo-protocols)

(defprotocol common-protocol
	(get-item [this] [this id])
	(insert [this newItem])
	(update [this id updatedItem])
	(delete [this id]))