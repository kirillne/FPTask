(ns blog.dal.dto.post-rating)

(defrecord post-rating [id name creation-date user-id text count rating can-rate])