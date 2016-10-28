(ns blog.bll.services.profiles-service
	(:require [blog.bll.services-protocols.common-protocol :as common-protocol]
			  [blog.bll.services-protocols.profiles-protocol :as profiles-protocol]
			  [blog.dal.repos.profiles-repo :as profiles-repo]))

(deftype profiles-service [profiles-repo] 
	common-protocol/common-service-protocol

	(get-items [this] 
		(.get-items profiles-repo))

	(get-item [this id]
		(first (.get-item profiles-repo id)))

	(insert-item [this newItem]
		(.insert-item profiles-repo newItem))

	(update-item [this updatedItem]
		(.update-item profiles-repo updatedItem))

	(delete-item [this id]
		(.delete-item profiles-repo id))

	profiles-protocol/profiles-service-protocol

	(get-by-surname [this surname] 
		(.get-by-surname profiles-repo surname))

	(get-by-email [this email] 
		(.get-by-email profiles-repo email))

	(get-by-country [this country] 
		(.get-by-country profiles-repo country))
	
	(get-by-city [this city] 
		(.get-by-city profiles-repo city)))