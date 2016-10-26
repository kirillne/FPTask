(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.repo.users-repo :as users-repo]
			  [blog.dal.repo.profiles-repo :as profiles-repo]
			  [blog.dal.dto.user :as user]
			  [blog.dal.dto.profile :as profile]))


(def users-repository (users-repo/->users-repo))
(defn create-user [login password seed] (user/->user nil login password seed))
(defn create-user [id login password seed] (user/->user id login password seed))

(def profiles-repository (profiles-repo/->profiles-repo))
(defn create-profile [user-id name surname birth-date sex country city address email] (profile/->profile user-id name surname birth-date sex country city address email nil))

(defroutes app-routes
	(GET "/" [] (view/index-page))

	(GET "/api/users" [] (.get-items users-repository))
	(GET "/api/user/:id" [id] (.get-item users-repository id))
	(GET "/api/delete-user/:id" [id] (.delete-item users-repository id))
	(GET "/api/create-user/:login/:password/:seed" [login password seed] (.insert-item users-repository (create-user login password seed)))
	(GET "/api/update-user/:id/:login/:password/:seed" [id login password seed] (.update-item users-repository (create-user id login password seed)))
	(GET "/api/user/login/:login" [login] (.get-by-login users-repository login))
	(GET "/api/user/seed/:id" [id] (.get-seed-by-id users-repository id))
	(GET "/api/user/seed/login/:login" [login] (.get-seed-by-login users-repository login))
	(GET "/api/delete-user/login/:login" [login] (.delete-by-login users-repository login))

	(GET "/api/profiles" [] (.get-items profiles-repository))
	(GET "/api/profile/:id" [id] (.get-item profiles-repository id))
	(GET "/api/delete-profile/:id" [id] (.delete-item profiles-repository id))
	(GET "/api/create-profile/:user-id/:name/:surname/:birth-date/:sex/:country/:city/:address/:email" [user-id name surname birth-date sex country city address email] (.insert-item profiles-repository (create-profile user-id name surname birth-date sex country city address email)))
	(GET "/api/update-profile/:user-id/:name/:surname/:birth-date/:sex/:country/:city/:address/:email" [user-id name surname birth-date sex country city address email] (.update-item profiles-repository (create-profile user-id name surname birth-date sex country city address email)))
	(GET "/api/profile/surname/:surname" [surname] (.get-by-surname profiles-repository surname))
	(GET "/api/profile/email/:email" [email] (.get-by-email profiles-repository email))
	(GET "/api/profile/country/:country" [country] (.get-by-country profiles-repository country))
	(GET "/api/profile/city/:city" [city] (.get-by-city profiles-repository city))
	(route/resources "/"))

(def app (wrap-json-response app-routes))