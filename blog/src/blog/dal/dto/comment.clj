(ns blog.dal.dto.comment)

(defrecord comment [id user-id text creation-date rating post-id])