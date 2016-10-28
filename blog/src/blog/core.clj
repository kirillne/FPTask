(ns blog.core
	(:use compojure.core)
	(:require [ring.util.response :as response]
			  [ring.middleware.json :as middleware]
			  [compojure.handler :as handler]
			  [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.db :as db]
			  [blog.bll.services.users-service :as users-service]
			  [blog.dal.repos.users-repo :as users-repo]
			  [blog.dal.repos.profiles-repo :as profiles-repo]
			  [blog.dal.repos.posts-repo :as posts-repo]
			  [blog.dal.repo.comments-repo :as comments-repo]
			  
			  [blog.dal.dto.user :as user]
			  [blog.dal.dto.profile :as profile]
			  [blog.dal.dto.post :as post]
			  [blog.dal.dto.comment :as comment]))


(def users-repository (users-repo/->users-repo db/db-spec))
(def users-service (users-service/->users-service users-repository))

(def posts-repository (posts-repo/->posts-repo db/db-spec))
(def comments-repository (comments-repo/->comments-repo))
	


(defn create-user ([login password seed] (user/->user nil login password seed))
				  ([id login password seed] (user/->user id login password seed)))

(defn create-post ([name creation-date user-id text] (post/->post nil name creation-date user-id text nil))
				  ([id name creation-date user-id text] (post/->post id name creation-date user-id text nil)))
				  
(defn create-comment ([user-id text creation-date post-id] (comment/->comment nil user-id text creation-date nil post-id))
					([id user-id text creation-date post-id] (comment/->comment id user-id text creation-date nil post-id))	

				  
(defroutes app-routes
	(GET "/" [] (view/index-page))

	(GET "/users" [] (view/all-users-page (.get-items users-service) false false nil))

	(GET "/users/:id/:deleted/:added" [id deleted added] (view/all-users-page (.get-items users-service) deleted added id))

	(GET "/user/add" [] (view/add-user-page))

	(GET "/user/:id" [id] (view/user-page (.get-item users-service id) false))

	(GET "/user/:id/:updated" [id updated] (view/user-page (.get-item users-service id) updated))

	(POST "/user/add" request (do (.insert-item users-service (create-user 
												(get-in request [:params :login]) 
												(get-in request [:params :password]) 
												(get-in request [:params :seed]))) 
								  (response/redirect (str "/users/" (get-in request [:params :login]) "/false/true"))))


	(POST "/user/update" request (do (.update-item users-service (create-user 
												(get-in request [:params :id])
												(get-in request [:params :login]) 
												(get-in request [:params :password]) 
												(get-in request [:params :seed]))) 
									 (response/redirect (str "/user/" (get-in request [:params :id]) "/true"))))
	
	(POST "/user/delete" request (do (.delete-item users-service (get-in request [:params :id])) 
								     (response/redirect (str "/users/" (get-in request [:params :id]) "/true/false"))))

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

(def app  
	(-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))