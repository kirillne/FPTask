(ns blog.routes.comments
	(:require 
			[blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST PUT]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
			[blog.controllers.comments-controller :as comments]))

(defroutes comments-routes	
	(GET "/comments/:post-id" [post-id :as request] (comments/add-comment-page request post-id))
	(POST "/comments/:post-id" [post-id] ())
	(POST "/comments/new/:post-id/:user-id" [post-id user-id :as request] (comments/add-comment request post-id user-id))
	
	(GET "/comments/:post-id/:comment-id" [post-id comment-id :as request] (comments/edit-comment-view request post-id comment-id ))
	(POST "/comments/edit/:post-id/:comment-id" [post-id comment-id :as request] (comments/edit-comment request post-id comment-id ))
	
	(POST "/comments/delete/:post-id/:comment-id" [post-id comment-id :as request] (comments/delete-comment request post-id comment-id))
	
	(POST "/comments/rating/:user-id/:comment-id" [user-id comment-id value post-id :as request] (comments/add-rating request user-id comment-id value post-id))
	(PUT "/comments/rating/:comment-id" [comment-id] ()))