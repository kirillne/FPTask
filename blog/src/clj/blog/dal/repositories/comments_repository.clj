(ns blog.dal.repositories.comments-repository
	(:require [blog.db.core :as db]
			  [blog.dal.dto.comment-rec :as dto]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.comments-protocol :refer [comments-repository-protocol]]))


(defn convert [item] 
	(if (nil? item)
	nil
	(dto/->comment-rec (:id item) (:user_id  item) (:text item) (:creation_date item) (:post_id item))))

(deftype comments-repository []
	comments-repository-protocol
	(get-comments-by-user-id [this user-id] (into [] (map convert (db/get-comments-by-user-id {:user-id user-id}))))
	(get-comments-by-post-id [this post-id] (into [] (map convert (db/get-comments-by-post-id {:post-id post-id}))))
	(delete-comment-by-post-id [this post-id] (db/delete-comment-by-post-id {:post-id post-id})))

(extend comments-repository 
	common-repository-protocol
  	(common-repository "comment" convert))