(ns blog.dal.repositories.profiles-repository
	(:require [blog.db.core :as db]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.profiles-protocol :refer [profiles-repository-protocol]]))

(deftype profiles-repository []
	profiles-repository-protocol
	(get-profiles-by-surname [this surname] (db/get-profiles-by-surname {:surname surname}))
	(get-profiles-by-email [this email] (db/get-profiles-by-email {:email email}))
	(get-profiles-by-country [this country] (db/get-profiles-by-country {:country country}))
	(get-profiles-by-city [this city] (db/get-profiles-by-city {:city city})))

(extend profiles-repository 
	common-repository-protocol
  	(common-repository "profile"))