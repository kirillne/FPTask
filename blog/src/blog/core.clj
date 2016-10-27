(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.db :as db]
			  [blog.bll.services.users-service :as users-service]
			  [blog.dal.repos.users-repo :as users-repo]
			  [blog.dal.repos.profiles-repo :as profiles-repo]
			  [blog.dal.dto.user :as user]
			  [blog.dal.dto.profile :as profile]))


(def users-repo (users-repo/->users-repo db/db-spec))
(def users-service (users-service/->users-service users-repo))

(defn create-user [login password seed] (user/->user nil login password seed))
(defn create-user [id login password seed] (user/->user id login password seed))

(defroutes app-routes
	(GET "/" [] (view/index-page))
	(GET "/users" [] (view/all-users-page (.get-items users-service)))
	(GET "/users/:id" [id] (view/user-page (.get-item users-service id)))

	(route/resources "/"))

(def app (wrap-json-response app-routes))