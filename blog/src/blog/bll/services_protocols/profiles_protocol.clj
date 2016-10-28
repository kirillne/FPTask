(ns blog.bll.services-protocols.profiles-protocol)

(defprotocol profiles-service-protocol
	(get-by-surname [this surname])
	(get-by-email [this email])
	(get-by-country [this country])
	(get-by-city [this city])
)