(ns blog.routes.comments
	(:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST PUT]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defroutes comments-routes	
	(GET "/comments/:post-id" [post-id] ())
	(POST "/comments/:post-id" [post-id] ())
	(GET "/comments/:post-id/:comment-id" [post-id comment-id] ())
	(PUT "/comments/:post-id/:comment-id" [post-id comment-id] ())
	(POST "/comments/rating/:comment-id" [comment-id] ())
	(PUT "/comments/rating/:comment-id" [comment-id] ()))