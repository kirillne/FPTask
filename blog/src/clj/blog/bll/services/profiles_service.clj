(ns blog.bll.services.profiles-service
	(:use     [blog.dal.protocols.profiles-protocol])
	(:require [blog.bll.protocols.common-protocol :refer [common-service-protocol]]
			  [blog.bll.protocols.profiles-protocol :refer [profiles-service-protocol]]
			  [blog.bll.services.common-service :refer [common-service]]
              [blog.dal.repositories.profiles-repository])
  	(:import  [blog.dal.repositories.profiles_repository profiles-repository]))

(def repository (profiles-repository.))

(deftype profiles-service [] 

	profiles-service-protocol

	(get-profiles-by-surname [this surname] (get-profiles-by-surname repository surname))
	(get-profiles-by-email [this email] (get-profiles-by-email repository email))
	(get-profiles-by-country [this country] (get-profiles-by-country repository country))
	(get-profiles-by-city [this city] (get-profiles-by-city repository city))
)

(extend profiles-service
	common-service-protocol
	(common-service repository))