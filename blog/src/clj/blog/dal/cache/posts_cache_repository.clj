(ns blog.dal.cache.posts-cache-repository
	(:use 
		[blog.dal.protocols.common-protocol]
		[blog.dal.protocols.posts-protocol]
		[blog.dal.dto.post :as post-dto]
		[blog.dal.dto.post-count :as post-count-dto])
	(:require 
		[blog.dal.repositories.posts-repository]
		[blog.dal.repositories.profiles-repository])
	(:import  
		[blog.dal.repositories.posts_repository posts-repository]
		[blog.dal.repositories.profiles_repository profiles-repository]))

(def post-repository (posts-repository.))

(def cache (atom {}))

(def db (agent post-repository))

(defn get-post [id] 
	(let [
		cached ((keyword id) @cache)]
		(if (nil? cached )
			(let [
				from-db (get-item post-repository {:id id})]
				(swap! cache conj {(keyword id) from-db})
				from-db)
			cached)))

(defn add-post [post]
	(let [
		id (get (first (create-item post-repository (assoc post :id nil))) 1 )]
		(swap! cache conj (assoc post :id id))
		id))

(defn update-post [post]
	(update-item post-repository post)
	(swap! cache assoc (keyword (:id post)) post)
)

(defn delete-post [params]
	(send db delete-item params)
	(swap! cache dissoc (keyword (:id params)))
)

(defn get-all []
	(reset! cache (get-all-items posts-repository))
)

(deftype posts-cache-repository [] 
	common-repository-protocol
	(get-all-items [this] (get-all))
	(get-item [this params] (get-post (:id params)))
	(create-item [this params] (add-post params))
	(update-item [this params] (update-post params))
	(delete-item [this params] (delete-post params))

	posts-repository-protocol
	(get-posts-by-user-id [this user-id] (get-posts-by-user-id posts-repository user-id))
	(get-posts-by-creation-date [this creation-date] (get-posts-by-user-id posts-repository creation-date))
	(get-posts-with-comments-count [this user-id] (get-posts-by-user-id posts-repository user-id)))


