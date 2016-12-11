(ns blog.controllers.home-controller
	(:use [blog.dal.protocols.common-protocol]
		  [blog.dal.protocols.users-protocol]
		  [blog.dal.protocols.posts-protocol]
		  [blog.dal.protocols.profiles-protocol]
		  [bouncer.validators :only [defvalidator]])
	(:require [blog.dal.repositories.users-repository]
			  [blog.dal.repositories.profiles-repository]
			  [blog.dal.cache.posts-cache-repository]
			  [blog.utils.messages-utils :as utils]
			  [bouncer.core :as b]
              [bouncer.validators :as v]
			  [clojure.string :refer [blank?]]
			  [ring.util.response :refer [response redirect content-type]]
			  [blog.layout :as layout]
			  [buddy.hashers :as hashers]
			  [buddy.auth.backends :as backends])
	(:import  [blog.dal.repositories.users_repository users-repository]
			  [blog.dal.repositories.profiles_repository profiles-repository]
			  [blog.dal.cache.posts_cache_repository posts-cache-repository]))

(defn about-page [request]
  (layout/render request "about.html"))
  
(defn signin-page [messages request]
  (layout/render request "account/signin.html" {:messages messages}))
  
(defn registration-page [messages request]
  (layout/render request "account/registration.html" {:messages messages}))
			
(def user-repository (users-repository.))
(def profile-repository (profiles-repository.))
(def post-repository (posts-cache-repository.))

(defvalidator equals  {:default-message-format "passwords are not equal"} [pass pass2] (= (compare pass pass2) 0))

(defvalidator unique-login  {:default-message-format "user with such login already exist"} [login] (nil? (get-user-by-login user-repository login)))

(defn validate-new-user [user] (b/validate user 
								:login [v/required unique-login]
								:password v/required
								:password2 [[v/required :message "Password confirmation must be present"] [equals (:password user)]]
								:email [[v/email :pre (comp not blank? :email)]]
								:birth-date [[v/datetime :pre (comp not blank? :birth-date)]]))

(defn validate-singin-user [user] (b/validate user
									:login v/required
									:password v/required))
								
(defn create-user [login password] (let [salt (str (rand-int 123456789))
										 hash-password (hashers/encrypt password {:alg :pbkdf2+sha256 :salt salt})]
								 	(create-item user-repository {:id nil :login login :password hash-password :seed salt})
									(:id (get-user-by-login user-repository login))))

(defn add-nil-members [user] 
	(let [
		user-name (if-not (contains? user :name)
					(assoc user :name nil) 
					user)
		user-surname (if-not (contains? user :surname)
					(assoc user-name :surname nil) 
					user-name)
		user-birth-date (if (or (not (contains? user :birth-date)) (blank? (:birth-date user)))
				    (assoc user-surname :birth-date nil) 
					user-surname)
		user-sex (if-not (contains? user :sex)
				    (assoc user-birth-date :sex nil) 
					user-birth-date)
		user-country (if-not (contains? user :country)
				    (assoc user-sex :country nil) 
					user-sex)
		user-city (if-not (contains? user :city)
				    (assoc user-country :city nil) 
					user-country)
		user-address (if-not (contains? user :address)
				    (assoc user-city :address nil) 
					user-city)
		user-email (if-not (contains? user :email)
				    (assoc user-address :email nil) 
					user-address)
		]
	user-email
))

(defn create-profile [params] (create-item profile-repository (add-nil-members params)))

(defn register [user] (let [validation-result (first (validate-new-user user))]
					  (if (nil? validation-result)  
						  (do
						  	(create-profile (assoc user :id (create-user (:login user) (:password user))))
						  	(registration-page (utils/fix-validation-messages {:success "((registration completed successfully))"}) nil)
						  )
					  (registration-page (utils/fix-validation-messages validation-result) nil))))



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
			(signin-page (utils/fix-validation-messages validation-result) nil))))

(defn signout [request]
  (-> (redirect "/home")
      (assoc :session {})))


(defn home-page [request]
  (layout/render
    request "home.html" {:posts (get-last-posts-with-comments-count-and-ratings post-repository)}))