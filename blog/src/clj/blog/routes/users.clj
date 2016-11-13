(ns blog.routes.users
	(:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET PUT]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defroutes users-routes
	(GET "/user/:id" [id] ())
	(GET "/profile/:id" [id] ())
	(PUT "/profile/:id" [id] ()))