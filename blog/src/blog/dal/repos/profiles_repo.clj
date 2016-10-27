(ns blog.dal.repos.profiles-repo
	(:require [blog.dal.repos-protocols.common-protocol :as common-protocol]
			  [blog.dal.repos-protocols.profiles-protocol :as profiles-protocol]
			  [blog.dal.dto.profile :as profile-dto]
			  [clojure.java.jdbc :as jdbc]))

(deftype profiles-repo [db-spec]

	;;common-repo-protocol implementaiton
	common-protocol/common-repo-protocol

	(get-items [this] 
		(jdbc/query db-spec 
             ["SELECT * FROM Profiles"]
             (profile-dto/->profile 
             	:userid
             	:name 
             	:surname 
             	:birthdate 
             	:sex 
             	:country 
             	:city 
             	:address 
             	:email 
             	:rating)))

	(get-item [this id]
		(jdbc/query db-spec
             ["SELECT * FROM Profiles WHERE UserId = ?" id]
             (profile-dto/->profile 
             	:userid 
             	:name 
             	:surname 
             	:birthdate 
             	:sex 
             	:country 
             	:city 
             	:address 
             	:email 
             	:rating)))

	(insert-item [this newItem]
		(jdbc/insert! db-spec :Profiles 
			{
				:userid (:user-id newItem) 
				:name (:name newItem) 
				:surname (:surname newItem) 
				:birthdate (:birth-date newItem) 
				:sex (:sex newItem) 
				:country (:country newItem) 
				:city (:city newItem) 
				:address (:address newItem) 
				:email (:email newItem)}))

	(update-item [this updatedItem] 
		(jdbc/update! db-spec :Profiles 
			{
				:userid (:user-id updatedItem) 
				:name (:name updatedItem) 
				:surname (:surname updatedItem) 
				:birthdate (:birth-date updatedItem) 
				:sex (:sex updatedItem) 
				:country (:country updatedItem) 
				:city (:city updatedItem) 
				:address (:address updatedItem) 
				:email (:email updatedItem)}
			["UserId = ?" (:user-id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db-spec :Profiles ["UserId = ?" id]))

	;;profiles-repo-protocol implementation
	profiles-protocol/profiles-repo-protocol

	(get-by-surname [this surname]
		(jdbc/query db-spec
             ["SELECT * FROM Profiles WHERE Surname = ?" surname]
             (profile-dto/->profile 
             	:userid 
             	:name 
             	:surname 
             	:birthdate 
             	:sex 
             	:country 
             	:city 
             	:address 
             	:email 
             	:rating)))

	(get-by-email [this email]
		(jdbc/query db-spec
             ["SELECT * FROM Profiles WHERE Email = ?" email]
             (profile-dto/->profile 
             	:userid 
             	:name 
             	:surname 
             	:birthdate 
             	:sex 
             	:country 
             	:city 
             	:address 
             	:email 
             	:rating)))

	(get-by-country [this country]
		(jdbc/query db-spec
             ["SELECT * FROM Profiles WHERE Country = ?" country]
             (profile-dto/->profile 
             	:userid 
             	:name 
             	:surname 
             	:birthdate 
             	:sex 
             	:country 
             	:city 
             	:address 
             	:email 
             	:rating)))

	(get-by-city [this city]
		(jdbc/query db-spec
             ["SELECT * FROM Profiles WHERE City = ?" city]
             (profile-dto/->profile 
             	:userid 
             	:name 
             	:surname 
             	:birthdate 
             	:sex 
             	:country 
             	:city 
             	:address 
             	:email 
             	:rating))))