(ns blog.routes.home
  (:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [blog.controllers.home-controller :as home]))


(defroutes home-routes
  (GET "/" [:as request] (home/home-page request))
  (GET "/home" [:as request] (home/home-page request))
  (GET "/about" [:as request] (home/about-page request))
  (GET "/registration" [] (home/registration-page nil nil))
  (POST "/registration" [& params] (home/register params)) ;(home/register login password password2 name surname birth-date sex country city address email))
  (GET "/signin" [] (home/signin-page nil nil))
  (POST "/signin" [:as request] (home/signin request))
  (POST "/signout" [:as request] (home/signout request))
  (GET "/check-session" [:as request] (println (request :session))))

