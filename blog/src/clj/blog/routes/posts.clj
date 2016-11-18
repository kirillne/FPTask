(ns blog.routes.posts
	(:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST PUT]]
            [ring.util.http-response :as response]
            [blog.controllers.post-controller :as post]))

(defroutes posts-routes
	(GET "/post" [:as request] (post/add-post-page request nil nil))
	(POST "/post" [:as request] (post/add-post request))
	(GET "/posts/:post-id" [post-id :as request] (post/view-post request post-id))
	(GET "/post/edit/:post-id" [post-id :as request] (post/get-edit-post request post-id))
	(POST "/post/edit/:post-id" [post-id :as request] (post/edit-post request post-id))
	(POST "/posts/rating/:post-id" [post-id] ())
	(PUT "/posts/rating/:post-id" [post-id] ()))