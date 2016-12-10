(ns blog.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [blog.layout :refer [error-page]]
            [blog.routes.home :refer [home-routes]]
            [blog.routes.users :refer [users-routes]]
            [blog.routes.posts :refer [posts-routes]]
            [blog.routes.dsl :refer [dsl-routes]]
            [blog.routes.comments :refer [comments-routes]]
            [compojure.route :as route]
            [blog.env :refer [defaults]]
            [mount.core :as mount]
            [blog.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes 
        (wrap-routes middleware/wrap-formats))
    (-> #'users-routes
        (wrap-routes middleware/wrap-formats)
        (wrap-routes middleware/wrap-restricted))
    (-> #'posts-routes
        (wrap-routes middleware/wrap-formats)
        (wrap-routes middleware/wrap-restricted))
    (-> #'comments-routes
        (wrap-routes middleware/wrap-formats)
        (wrap-routes middleware/wrap-restricted))
    (-> #'dsl-routes
        (wrap-routes middleware/wrap-formats)
        (wrap-routes middleware/wrap-restricted))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
