(ns blog.routes.home
  (:use     [blog.bll.protocols.common-protocol]
            [blog.bll.protocols.users-protocol])
  (:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [blog.bll.services.users-service])
  (:import  [blog.bll.services.users_service users-service]))

(def users-service-instance (users-service.))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/a" [] (get-all-items users-service-instance))
  (GET "/a/:login" [login] (get-user-by-login users-service-instance login)))

