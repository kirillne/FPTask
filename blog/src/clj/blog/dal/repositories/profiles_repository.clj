(ns blog.dal.repositories.profiles-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.profile :as dto]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.profiles-protocol :refer [profiles-repository-protocol]]))

(defn convert [item] 
	(if (nil? item)
	nil
	(dto/->profile (:user_id item) (:name item) (:surname item) (:birth_date item) (:sex item) (:country item) (:city item) (:address item) (:email item))))

(deftype profiles-repository []
	profiles-repository-protocol
	(get-profiles-by-surname [this surname] (into [] (map convert (db/get-profiles-by-surname {:surname surname}))))
	(get-profiles-by-email [this email] (into [] (map convert (db/get-profiles-by-email {:email email}))))
	(get-profiles-by-country [this country] (into [] (map convert (db/get-profiles-by-country {:country country}))))
	(get-profiles-by-city [this city] (into [] (map convert (db/get-profiles-by-city {:city city})))))

(extend profiles-repository 
	common-repository-protocol
  	(common-repository "profile" convert))