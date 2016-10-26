(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.repo.users-repo :as repo]
			  [blog.dal.dto.user :as user]))


(def users-repository (repo/->users-repo))
(defn create-user [login password seed] (user/->user nil login password seed))
(defn create-user [id login password seed] (user/->user id login password seed))

(defroutes app-routes
	(GET "/" [] (view/index-page))
	(GET "/api/users" [] (.get-items users-repository))
	(GET "/api/user/:id" [id] (.get-item users-repository id))
	(GET "/api/delete-user/:id" [id] (.delete-item users-repository id))
	(GET "/api/create-user/:login/:password/:seed" [login password seed] (.insert-item users-repository (create-user login password seed)))
	(GET "/api/update-user/:id/:login/:password/:seed" [id login password seed] (.update-item users-repository (create-user id login password seed)))
	(route/resources "/"))

(def app (wrap-json-response app-routes))