(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.repo.users-repo :as repo]))


(def users-repository (repo/->users-repo))

(defroutes app-routes
	(GET "/" [] (view/index-page))
	(GET "/api/users" [] (.get-items users-repository))
	(GET "/api/user" [] (.get-item users-repository 1))
	(GET "/api/delete-user" [] (.delete-item users-repository 1))
	(route/resources "/"))

(def app (wrap-json-response app-routes))