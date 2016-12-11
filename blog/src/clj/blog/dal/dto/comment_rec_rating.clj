(ns blog.dal.dto.comment-rec-rating)

(defrecord comment-rec-rating [id user-id text creation-date post-id rating can-rate])