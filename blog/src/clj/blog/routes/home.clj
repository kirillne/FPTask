(ns blog.routes.home
  (:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [blog.controllers.home-controller :as home]))


  
(defn registration [])

(defroutes home-routes
  (GET "/home" [] (home/home-page))
  (GET "/about" [] (home/about-page))
  (GET "/registration" [] (home/registration-page nil))
  (POST "/registration" [& params] (home/register params)) ;(home/register login password password2 name surname birth-date sex country city address email))
  (GET "/signin" [] (home/signin-page nil))
  (POST "/signin" [login password :as request] (home/signin login password request))
  (GET "/check-session" [:as request] (println (request :session))))

