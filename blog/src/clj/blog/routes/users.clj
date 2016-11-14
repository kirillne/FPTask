(ns blog.routes.users
	(:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET PUT]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defroutes users-routes
	(GET "/users/:id" [id] ())
	(GET "/profiles/:id" [id] ())
	(PUT "/profiles/:id" [id] ()))