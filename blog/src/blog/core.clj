(ns blog.core
	(:use compojure.core
		  ring.middleware.json
		  ring.util.response)
	(:require [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.repo.users-repo :as users-repo]
			  [blog.dal.repo.profiles-repo :as profiles-repo]
			  [blog.dal.repo.posts-repo :as posts-repo]
			  [blog.dal.repo.comments-repo :as comments-repo]
			  [blog.dal.dto.user :as user]
			  [blog.dal.dto.profile :as profile]
			  [blog.dal.dto.post :as post]
			  [blog.dal.dto.comment :as comment]))


(def users-repository (users-repo/->users-repo))
(defn create-user [login password seed] (user/->user nil login password seed))
(defn create-user [id login password seed] (user/->user id login password seed))

(def profiles-repository (profiles-repo/->profiles-repo))
(defn create-profile [user-id name surname birth-date sex country city address email] (profile/->profile user-id name surname birth-date sex country city address email nil))

(def posts-repository (posts-repo/->posts-repo))
(defn create-post ([name creation-date user-id text] (post/->post nil name creation-date user-id text nil))
				  ([id name creation-date user-id text] (post/->post id name creation-date user-id text nil)))
				  
(def comments-repository (comments-repo/->comments-repo))
(defn create-comment ([user-id text creation-date post-id] (comment/->comment nil user-id text creation-date nil post-id))
					([id user-id text creation-date post-id] (comment/->comment id user-id text creation-date nil post-id))	
				  
(defroutes app-routes
	(GET "/" [] (view/index-page))

	(GET "/api/users" [] (.get-items users-repository))
	(GET "/api/user/:id" [id] (.get-item users-repository id))
	(GET "/api/delete-user/:id" [id] (.delete-item users-repository id))
	(GET "/api/create-user/:login/:password/:seed" [login password seed] (.insert-item users-repository (create-user login password seed)))
	(GET "/api/update-user/:id/:login/:password/:seed" [id login password seed] (.update-item users-repository (create-user id login password seed)))
	(GET "/api/user/login/:login" [login] (.get-by-login users-repository login))
	(GET "/api/user/seed/:id" [id] (.get-seed-by-id users-repository id))
	(GET "/api/user/seed/login/:login" [login] (.get-seed-by-login users-repository login))
	(GET "/api/delete-user/login/:login" [login] (.delete-by-login users-repository login))

	(GET "/api/profiles" [] (.get-items profiles-repository))
	(GET "/api/profile/:id" [id] (.get-item profiles-repository id))
	(GET "/api/delete-profile/:id" [id] (.delete-item profiles-repository id))
	(GET "/api/create-profile/:user-id/:name/:surname/:birth-date/:sex/:country/:city/:address/:email" [user-id name surname birth-date sex country city address email] (.insert-item profiles-repository (create-profile user-id name surname birth-date sex country city address email)))
	(GET "/api/update-profile/:user-id/:name/:surname/:birth-date/:sex/:country/:city/:address/:email" [user-id name surname birth-date sex country city address email] (.update-item profiles-repository (create-profile user-id name surname birth-date sex country city address email)))
	(GET "/api/profile/surname/:surname" [surname] (.get-by-surname profiles-repository surname))
	(GET "/api/profile/email/:email" [email] (.get-by-email profiles-repository email))
	(GET "/api/profile/country/:country" [country] (.get-by-country profiles-repository country))
	(GET "/api/profile/city/:city" [city] (.get-by-city profiles-repository city))

	(GET "/api/posts" [] (.get-items posts-repository))
	(GET "/api/post/:id" [id] (.get-item posts-repository id))
	(GET "/api/delete-post/:id" [id] (.delete-item posts-repository id))
	(GET "/api/create-post/:name/:creation-date/:user-id/:text" [name creation-date user-id text] (.insert-item posts-repository (create-post name creation-date user-id text)))
	(GET "/api/update-post/:id/:name/:creation-date/:user-id/:text" [id name creation-date user-id text] (.update-item posts-repository (create-post id name creation-date user-id text)))
	(GET "/api/posts/user/:user-id" [user-id] (.get-by-user-id posts-repository user-id))
	(GET "/api/posts/date/:creation-date" [creation-date] (.get-by-creation-date posts-repository creation-date))
	
	(GET "/api/comments" [] (.get-items comments-repository))
	(GET "/api/comment/:id" [id] (.get-item comments-repository id))
	(GET "/api/create-comment/:user-id/:text/:creation-date/:post-id" [user-id text creation-date post-id] (.insert-item comments-repository (create-comment user-id text creation-date post-id)))
	(GET "/api/update-comment/:id/:user-id/:text/:creation-date/:post-id"  [id user-id text creation-date post-id] (.update-item comments-repository (create-comment id user-id text creation-date post-id)))
	(GET "/api/delete-comment/:id" [id] (.delete-item comments-repository id)))
	(GET "/api/comments/:user-id" [user-id] (.get-by-user-id comments-repository user-id))
	(GET "/api/comments/:post-id" [post-id] (.get-by-post-id comments-repository post-id))
	(GET "/api/delete-comments/:post-id" [post-id] (.delete-by-post-id comments-repository post-id))
			
	(route/resources "/"))

(def app (wrap-json-response app-routes))