(ns blog.dal.repositories.posts-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.post :as post-dto]
			  [blog.dal.dto.post-count :as post-count-dto]
			  [blog.dal.dto.post-rating :as post-rating-dto]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.posts-protocol :refer [posts-repository-protocol]]))

(defn convert-bool [item] 
	(if (nil? item) (int 0) (if (= item true) (int 1) (int -1))))

(defn convert [item] 
	(if (nil? item)
	nil
	(post-dto/->post (:id item) (:name item) (:creation_date item) (:user_id item) (:text item))))

(defn convert-with-count [item] 
	(if (nil? item)
	nil
	(post-count-dto/->post-count (:id item) (:name item) (:creation_date item) (:user_id item) (:text item) (:count item) (convert-bool (:value item)) )))

(defn convert-with-rating [item] 
	(if (nil? item)
	nil
	(post-rating-dto/->post-rating (:id item) (:name item) (:creation_date item) (:user_id item) (:text item) (:count item) (:rating item) (convert-bool (:value item)) )))
	
(deftype posts-repository []
	posts-repository-protocol
	(get-posts-by-user-id [this user-id] (into [] (map convert (db/get-posts-by-user-id {:user-id user-id}))))
	(get-posts-by-creation-date [this creation-date] (into [] (map convert (db/get-posts-by-creation-date {:creation-date creation-date}))))
	(get-posts-with-comments-count-restricted [this user-id current-user-id] (into [] (map convert-with-count (db/get-posts-with-comments-count {:user-id user-id :current-user-id current-user-id}))))
	(add-post-rating [this user-id post-id value] (db/create-posts-ratings {:user-id user-id :post-id post-id :value value}))
	(update-post-rating [this user-id post-id value] (db/update-post-rating {:user-id user-id :post-id post-id :value value}))
	(get-existing [this user-id post-id] (db/get-existing {:user-id user-id :post-id post-id}))
	(get-post-sum-rating [this post-id] (db/get-post-sum-rating {:post-id post-id}))
	(get-posts-with-comments-count-and-ratings-restricted [this user-id current-user-id] (into [] (map convert-with-rating (db/get-posts-with-comments-count-and-ratings {:user-id user-id :current-user-id current-user-id}))))
	)

(extend posts-repository 
	common-repository-protocol
  	(common-repository "post" convert))