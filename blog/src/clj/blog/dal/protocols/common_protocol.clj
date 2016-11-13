(ns blog.dal.protocols.common-protocol)

(defprotocol common-repository-protocol
	(get-all-items [this])
	(get-item [this params])
	(create-item [this params])
	(update-item [this params])
	(delete-item [this params]))