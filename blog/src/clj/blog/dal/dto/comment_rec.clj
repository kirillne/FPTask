(ns blog.dal.dto.comment-rec)

(defrecord comment-rec [id user-id text creation-date post-id])