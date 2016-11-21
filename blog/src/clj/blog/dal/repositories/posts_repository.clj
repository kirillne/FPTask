(ns blog.dal.repositories.posts-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.post :as post-dto]
			  [blog.dal.dto.post-count :as post-count-dto]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.posts-protocol :refer [posts-repository-protocol]]))

(defn convert [item] 
	(if (nil? item)
	nil
	(post-dto/->post (:id item) (:name item) (:creation_date item) (:user_id item) (:text item))))

(defn convert-with-count [item] 
	(if (nil? item)
	nil
	(post-count-dto/->post-count (:id item) (:name item) (:creation_date item) (:user_id item) (:text item) (:count item))))

(deftype posts-repository []
	posts-repository-protocol
	(get-posts-by-user-id [this user-id] (into [] (map convert (db/get-posts-by-user-id {:user-id user-id}))))
	(get-posts-by-creation-date [this creation-date] (into [] (map convert (db/get-posts-by-creation-date {:creation-date creation-date}))))
	(get-posts-with-comments-count [this user-id] (into [] (map convert-with-count (db/get-posts-with-comments-count {:user-id user-id})))))

(extend posts-repository 
	common-repository-protocol
  	(common-repository "post" convert))