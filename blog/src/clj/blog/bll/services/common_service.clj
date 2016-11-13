(ns blog.bll.services.common-service
	(:use [blog.dal.protocols.common-protocol]))

(defn common-service [repository] {
  :get-all-items (fn [this] (get-all-items repository))
  :get-item (fn [this params] (get-item repository params))
  :create-item (fn [this params] (create-item repository params))
  :update-item (fn [this params] (update-item repository params))
  :delete-item (fn [this params] (delete-item repository params))})