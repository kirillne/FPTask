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
             ["SELECT UserId , Name, Surname, BirthDate, Sex, Country, City, Address, Email, Rating FROM Profiles"]
             {:row-fn #(profile-dto/->profile 
             	(:userid %1)
             	(:name %1) 
             	(:surname %1) 
             	(:birthdate %1) 
             	(:sex %1) 
             	(:country %1) 
             	(:city %1) 
             	(:address %1) 
             	(:email %1) 
             	(:rating %1))}))

	(get-item [this id]
		(jdbc/query db-spec
             ["SELECT UserId , Name, Surname, BirthDate, Sex, Country, City, Address, Email, Rating FROM Profiles WHERE UserId = ?" id]
             {:row-fn #(profile-dto/->profile 
                  (:userid %1)
                  (:name %1) 
                  (:surname %1) 
                  (:birthdate %1) 
                  (:sex %1) 
                  (:country %1) 
                  (:city %1) 
                  (:address %1) 
                  (:email %1) 
                  (:rating %1))}))

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
             ["SELECT UserId , Name, Surname, BirthDate, Sex, Country, City, Address, Email, Rating FROM Profiles WHERE Surname = ?" surname]
             {:row-fn #(profile-dto/->profile 
                  (:userid %1)
                  (:name %1) 
                  (:surname %1) 
                  (:birthdate %1) 
                  (:sex %1) 
                  (:country %1) 
                  (:city %1) 
                  (:address %1) 
                  (:email %1) 
                  (:rating %1))}))

	(get-by-email [this email]
		(jdbc/query db-spec
             ["SELECT UserId , Name, Surname, BirthDate, Sex, Country, City, Address, Email, Rating FROM Profiles WHERE Email = ?" email]
             {:row-fn #(profile-dto/->profile 
                  (:userid %1)
                  (:name %1) 
                  (:surname %1) 
                  (:birthdate %1) 
                  (:sex %1) 
                  (:country %1) 
                  (:city %1) 
                  (:address %1) 
                  (:email %1) 
                  (:rating %1))}))

	(get-by-country [this country]
		(jdbc/query db-spec
             ["SELECT UserId , Name, Surname, BirthDate, Sex, Country, City, Address, Email, Rating FROM Profiles WHERE Country = ?" country]
             {:row-fn #(profile-dto/->profile 
                  (:userid %1)
                  (:name %1) 
                  (:surname %1) 
                  (:birthdate %1) 
                  (:sex %1) 
                  (:country %1) 
                  (:city %1) 
                  (:address %1) 
                  (:email %1) 
                  (:rating %1))}))

	(get-by-city [this city]
		(jdbc/query db-spec
             ["SELECT UserId , Name, Surname, BirthDate, Sex, Country, City, Address, Email, Rating FROM Profiles WHERE City = ?" city]
             {:row-fn #(profile-dto/->profile 
                  (:userid %1)
                  (:name %1) 
                  (:surname %1) 
                  (:birthdate %1) 
                  (:sex %1) 
                  (:country %1) 
                  (:city %1) 
                  (:address %1) 
                  (:email %1) 
                  (:rating %1))})))