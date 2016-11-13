(ns blog.bll.protocols.common-protocol)

(defprotocol common-service-protocol
	(get-all-items [this])
	(get-item [this params])
	(create-item [this params])
	(update-item [this params])
	(delete-item [this params]))