(ns blog.dal.dto.comment-record)

(defrecord comment-record [id user-id text creation-date rating post-id])