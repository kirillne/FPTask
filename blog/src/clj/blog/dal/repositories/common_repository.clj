(ns blog.dal.repositories.common-repository
	(:require [blog.db.core :as db]))

(defn call [name & params]
  (apply (ns-resolve 'blog.db.core (symbol name)) params))

(defn common-repository [table-name] {
  :get-all-items (fn [this] (call (str "get-all-" table-name "s") []))
  :get-item (fn [this params] (call (str "get-" table-name) params))
  :create-item (fn [this params] (call (str "create-" table-name) params))
  :update-item (fn [this params] (call (str "update-" table-name) params))
  :delete-item (fn [this params] (call (str "delete-" table-name) params))})
