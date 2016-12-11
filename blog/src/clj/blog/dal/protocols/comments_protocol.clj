(ns blog.dal.protocols.comments-protocol)

(defprotocol comments-repository-protocol
	(get-comments-by-user-id [this user-id])
	(get-comments-by-post-id [this post-id])
	(delete-comment-by-post-id [this post-id])
	(get-comment-existing [this user-id comment-id])
	(add-comment-rating [this user-id comment-id value])
	(update-comment-rating [this user-id comment-id value])
	(get-comment-sum-rating [this comment-id])
	(get-comments-by-post-id-with-ratings [this post-id])
)