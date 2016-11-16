(ns blog.controllers.home-controller
	(:use [blog.dal.protocols.common-protocol]
		  [blog.dal.protocols.users-protocol]
		  [blog.dal.protocols.profiles-protocol]
		  [bouncer.validators :only [defvalidator]])
	(:require [blog.dal.repositories.users-repository]
			  [blog.dal.repositories.profiles-repository]
			  [bouncer.core :as b]
              [bouncer.validators :as v]
			  [clojure.string :refer [blank?]]
			  [ring.util.response :refer [response redirect content-type]]
			  [blog.layout :as layout]
			  [buddy.hashers :as hashers]
			  [buddy.auth.backends :as backends])
	(:import  [blog.dal.repositories.users_repository users-repository]
			[blog.dal.repositories.profiles_repository profiles-repository]))
			
(defn home-page [request]
  (layout/render
    request "home.html" {:docs "Hello"}))

(defn about-page [request]
  (layout/render request "about.html"))
  
(defn signin-page [messages request]
  (layout/render request "account/signin.html" {:messages messages}))
  
(defn registration-page [messages request]
  (layout/render request "account/registration.html" {:messages messages}))
			
(def user-repository (users-repository.))
(def profile-repository (profiles-repository.))

(defvalidator equals  {:default-message-format "passwords are not equal"} [pass pass2] (= (compare pass pass2) 0))

(defn validate-new-user [user] (b/validate user 
								:login v/required
								:password v/required
								:password2 [v/required [equals (:password user)]]
								:email [[v/email :pre (comp not blank? :email)]]
								:birth-date [[v/datetime :pre (comp not blank? :birth-date)]]))

(defn validate-singin-user [user] (b/validate user
									:login v/required
									:password v/required))
								
(defn create-user [login password] (let [salt (str (rand-int 123456789))
										 hash-password (hashers/encrypt password {:alg :pbkdf2+sha256 :salt salt})]
								 	(create-item user-repository {:id nil :login login :password hash-password :seed salt})
									(:id (get-user-by-login user-repository login))))
								 
(defn create-profile [params] (create-item profile-repository params))

(defn register [user] (let [validation-result (first (validate-new-user user))]
					  (if (nil? validation-result)  
						  (create-profile (assoc user :id (create-user (:login user) (:password user))))
						  (registration-page validation-result nil))
					  (registration-page "success" nil)))



(defn update-session [request id]
	(let [next-url (get-in request [:query-params :next] "/home")
		  session (:session request)
          updated-session (assoc session :identity id)]
        (-> (redirect next-url)
            (assoc :session updated-session)))
	) 

(defn signin [request] 
	(let [
		login (get-in request [:form-params "login"])
		password (get-in request [:form-params "password"])
		validation-result (first (validate-singin-user {:login login :password password}))]
		(if (nil? validation-result)
			(let [salt (:seed (get-seed-by-login user-repository login))
				  user (get-user-by-login user-repository login)
			 	  hash-pass-db (:password user)
			 	  hash-pass (hashers/encrypt password {:alg :pbkdf2+sha256 :salt salt})]
				(if (and hash-pass hash-pass-db (= hash-pass hash-pass-db))
					(update-session request (:id user))
					(signin-page "error in login or password" nil)))
			(signin-page validation-result nil))))

(defn signout
  [request]
  (-> (redirect "/home")
      (assoc :session {})))