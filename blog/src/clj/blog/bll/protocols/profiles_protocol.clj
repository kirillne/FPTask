(ns blog.bll.protocols.profiles-protocol)

(defprotocol profiles-service-protocol
	(get-profiles-by-surname [this surname])
	(get-profiles-by-email [this email])
	(get-profiles-by-country [this country])
	(get-profiles-by-city [this city])
)