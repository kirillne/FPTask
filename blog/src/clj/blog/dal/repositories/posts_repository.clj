(ns blog.dal.repositories.posts-repository
	(:require [blog.db.core :as db]
			  [blog.dal.repositories.common-repository :refer [common-repository]]
			  [blog.dal.protocols.common-protocol :refer [common-repository-protocol]]
			  [blog.dal.protocols.posts-protocol :refer [posts-repository-protocol]]))

(deftype posts-repository []
	posts-repository-protocol
	(get-posts-by-user-id [this user-id] (db/get-posts-by-user-id {:user-id user-id}))
	(get-posts-by-creation-date [this creation-date] (db/get-posts-by-creation-date {:creation-date creation-date})))

(extend posts-repository 
	common-repository-protocol
  	(common-repository "post"))