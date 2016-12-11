(ns blog.dal.protocols.posts-protocol)

(defprotocol posts-repository-protocol
	(get-posts-by-user-id [this user-id])
	(get-posts-by-creation-date [this creation-date])
	(get-posts-with-comments-count-restricted [this user-id current-user-id])
	(add-post-rating [this user-id post-id value])
	(update-post-rating [this user-id post-id value])
	(get-existing [this user-id post-id])
	(get-post-sum-rating [this post-id])
	(get-posts-with-comments-count-and-ratings-restricted [this user-id current-user-id])
)