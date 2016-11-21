(ns blog.routes.users
	(:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET PUT]]
            [ring.util.http-response :as response]
            [blog.controllers.user-controller :as user]))

(defroutes users-routes
	(GET "/users" [:as request] (user/view-users request))
	(GET "/users/:id" [:as request id] (user/view-user request id))
	(GET "/profiles/:id" [:as request id] ())
	(PUT "/profiles/:id" [:as request id] ()))