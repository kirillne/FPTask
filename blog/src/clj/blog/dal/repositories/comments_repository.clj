(ns blog.dal.repositories.comments-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.comment-rec :as dto]
			  [blog.dal.dto.comment-rec-rating :as dto-rating]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.comments-protocol :refer [comments-repository-protocol]]))


(defn convert-bool [item] 
	(if (nil? item) (int 0) (if (= item true) (int 1) (int -1))))
	
(defn convert [item] 
	(if (nil? item)
	nil
	(dto/->comment-rec (:id item) (:user_id  item) (:text item) (:creation_date item) (:post_id item))))

(defn convert-rating [item] 
	(if (nil? item)
	nil
	(dto-rating/->comment-rec-rating (:id item) (:user_id  item) (:text item) (:creation_date item) (:post_id item) (:rating item) (convert-bool (:value item)))))
	
(deftype comments-repository []
	comments-repository-protocol
	(get-comments-by-user-id [this user-id] (into [] (map convert (db/get-comments-by-user-id {:user-id user-id}))))
	(get-comments-by-post-id [this post-id] (into [] (map convert (db/get-comments-by-post-id {:post-id post-id}))))
	(delete-comment-by-post-id [this post-id] (db/delete-comment-by-post-id {:post-id post-id}))
	(get-comment-existing [this user-id comment-id] (db/get-comment-existing {:user-id user-id :comment-id comment-id}))
	(add-comment-rating [this user-id comment-id value] (db/create-comments-ratings {:user-id user-id :comment-id comment-id :value value}))
	(update-comment-rating [this user-id comment-id value] (db/update-comments-ratings {:user-id user-id :comment-id comment-id :value value}))
	(get-comment-sum-rating [this comment-id] (db/get-comment-sum-rating {:comment-id comment-id}))
	(get-comments-by-post-id-with-ratings [this post-id] (into [] (map convert-rating (db/get-comments-by-post-id-with-ratings {:post-id post-id}))))
	)

(extend comments-repository 
	common-repository-protocol
  	(common-repository "comment" convert))