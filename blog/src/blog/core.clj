(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.view :as view]
						[blog.db :as db]))

(defroutes app_routes
	(GET "/" [] (view/index-page))
	(GET "/api" [] (response {:name "Alex"}))
	(GET "/api/users" [] (response {:value (db/get-users)}))
	(route/resources "/"))

(def app (wrap-json-response app_routes))
