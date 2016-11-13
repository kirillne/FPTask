(ns blog.routes.posts
	(:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST PUT]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defroutes posts-routes
	(GET "/post" [] ())
	(POST "/post" [] ())
	(GET "/posts/:post-id" [post-id] ())
	(POST "/posts/rating/:post-id" [post-id] ())
	(PUT "/posts/rating/:post-id" [post-id] ()))