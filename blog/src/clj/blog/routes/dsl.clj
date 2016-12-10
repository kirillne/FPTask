(ns blog.routes.dsl
  (:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [blog.controllers.dsl-controller :as dsl]))


(defroutes dsl-routes
  (GET "/dsl" [:as request] (dsl/get-page request))
  (POST "/dsl" [:as request] (dsl/invoke-command request)))

