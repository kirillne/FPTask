(ns blog.dal.db-protocols)

(defprotocol db-common-protocol
	(get-item [this] [this id])
	(insert [this newItem])
	(update [this id updatedItem])
	(delete [this id]))