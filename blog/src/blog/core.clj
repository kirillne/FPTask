(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.view :as view]))

(defroutes app_routes
	(GET "/" [] (view/index-page))
	(GET "/api" [] (response {:name "Alex"}))
	(route/resources "/"))

(def app (wrap-json-response app_routes))
