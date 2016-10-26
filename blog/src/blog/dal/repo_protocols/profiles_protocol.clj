(ns blog.dal.repo-protocols.profiles-protocol)

(defprotocol profiles-repo-protocol
	(get-by-surname [this surname])
	(get-by-email [this email])
	(get-by-country [this country])
	(get-by-city [this city])
)