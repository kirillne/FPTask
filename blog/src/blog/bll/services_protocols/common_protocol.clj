(ns blog.bll.services-protocols.common-protocol)

(defprotocol common-service-protocol
	(get-items [this])
	(get-item [this id])
	(insert-item [this newItem])
	(update-item [this updatedItem])
	(delete-item [this id]))