(ns blog.core
	(:use compojure.core)
	(:require [ring.util.response :as response]
			  [ring.middleware.json :as middleware]
			  [compojure.handler :as handler]
			  [compojure.route :as route]
			  [blog.views.view :as view]
			  [blog.dal.db :as db]
			  [blog.bll.services.users-service :as users-service]
			  [blog.bll.services.profiles-service :as profiles-service]
			  [blog.bll.services.posts-service :as posts-service]
			  [blog.bll.services.comments-service :as comments-service]
			  [blog.dal.repos.users-repo :as users-repo]
			  [blog.dal.repos.profiles-repo :as profiles-repo]
			  [blog.dal.repos.posts-repo :as posts-repo]
			  [blog.dal.repos.comments-repo :as comments-repo]
			  
			  [blog.dal.dto.user :as user]
			  [blog.dal.dto.profile :as profile]
			  [blog.dal.dto.post :as post]
			  [blog.dal.dto.comment-rec :as comment-rec]))


(def users-repository (users-repo/->users-repo db/db-spec))
(def users-service (users-service/->users-service users-repository))

(def profiles-repository (profiles-repo/->profiles-repo db/db-spec))
(def profiles-service (profiles-service/->profiles-service profiles-repository))

(def posts-repository (posts-repo/->posts-repo db/db-spec))
(def posts-service (posts-service/->posts-service posts-repository))

(def comments-repository (comments-repo/->comments-repo db/db-spec))
(def comments-service (comments-service/->comments-service comments-repository))	


(defn create-user ([login password seed] (user/->user nil login password seed))
				  ([id login password seed] (user/->user id login password seed)))

(defn create-profile [userid name surname birthdate sex country city address email] (profile/->profile userid name surname birthdate sex country city address email nil))

(defn create-post ([name creation-date user-id text] (post/->post nil name creation-date user-id text nil))
				  ([id name creation-date user-id text] (post/->post id name creation-date user-id text nil)))
						
(defn create-comment ([user-id text creation-date post-id] (comment-rec/->comment-rec nil user-id text creation-date nil post-id))
					([id user-id text creation-date post-id] (comment-rec/->comment-rec id user-id text creation-date nil post-id)))

				  
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

	(GET "/profiles" [] (view/all-profiles-page (.get-items profiles-service) false false nil))

	(GET "/profiles/:id/:deleted/:added" [id deleted added] (view/all-profiles-page (.get-items profiles-service) deleted added id))

	(GET "/profile/add" [] (view/add-profile-page))

	(GET "/profile/:id" [id] (view/profile-page (.get-item profiles-service id) false))

	(GET "/profile/:id/:updated" [id updated] (view/profile-page (.get-item profiles-service id) updated))

	(POST "/profile/add" request (do (.insert-item profiles-service (create-profile 
												(get-in request [:params :userid]) 
												(get-in request [:params :name]) 
												(get-in request [:params :surname])
												(get-in request [:params :birthdate])
												(get-in request [:params :sex])
												(get-in request [:params :country])
												(get-in request [:params :city])
												(get-in request [:params :address])
												(get-in request [:params :email]))) 
								     (response/redirect (str "/profiles/" (get-in request [:params :name]) "/false/true"))))

	(POST "/profile/update" request (do (.update-item profiles-service (create-profile 
												(get-in request [:params :userid]) 
												(get-in request [:params :name]) 
												(get-in request [:params :surname])
												(get-in request [:params :birthdate])
												(get-in request [:params :sex])
												(get-in request [:params :country])
												(get-in request [:params :city])
												(get-in request [:params :address])
												(get-in request [:params :email]))) 
									 		(response/redirect (str "/profile/" (get-in request [:params :userid]) "/true"))))
	
	(POST "/profile/delete" request (do (.delete-item profiles-service (get-in request [:params :userid])) 
								     (response/redirect (str "/profiles/" (get-in request [:params :userid]) "/true/false"))))

	(GET "/posts" [] (view/all-posts-page (.get-items posts-service) false false nil))

	(GET "/posts/:id/:deleted/:added" [id deleted added] (view/all-posts-page (.get-items posts-service) deleted added id))

	(GET "/post/add" [] (view/add-post-page))

	(GET "/post/:id" [id] (view/post-page (.get-item posts-service id) false))

	(GET "/post/:id/:updated" [id updated] (view/post-page (.get-item posts-service id) updated))

	(POST "/post/add" request (do (.insert-item posts-service (create-post 
												(get-in request [:params :name]) 
												(get-in request [:params :creation-date])
												(get-in request [:params :user-id])
												(get-in request [:params :text]))) 
								     (response/redirect (str "/posts/" (get-in request [:params :name]) "/false/true"))))

	(POST "/post/update" request (do (.update-item posts-service (create-post 
												(get-in request [:params :id]) 
												(get-in request [:params :name]) 
												(get-in request [:params :creation-date])
												(get-in request [:params :user-id])
												(get-in request [:params :text]))) 
									 		(response/redirect (str "/post/" (get-in request [:params :id]) "/true"))))
	
	(POST "/post/delete" request (do (.delete-item posts-service (get-in request [:params :id])) 
								     (response/redirect (str "/posts/" (get-in request [:params :id]) "/true/false"))))

	(GET "/api/posts" [] (.get-items posts-service))
	(GET "/api/post/:id" [id] (.get-item posts-repository id))
	(GET "/api/delete-post/:id" [id] (.delete-item posts-repository id))
	(GET "/api/create-post/:name/:creation-date/:user-id/:text" [name creation-date user-id text] (.insert-item posts-repository (create-post name creation-date user-id text)))
	(GET "/api/update-post/:id/:name/:creation-date/:user-id/:text" [id name creation-date user-id text] (.update-item posts-repository (create-post id name creation-date user-id text)))
	(GET "/api/posts/user/:user-id" [user-id] (.get-by-user-id posts-repository user-id))
	(GET "/api/posts/date/:creation-date" [creation-date] (.get-by-creation-date posts-repository creation-date))

	
	(GET "/comments" [] (view/all-comments-page (.get-items comments-service) false false nil))
	(GET "/comments/:id/:deleted/:added" [id deleted added] (view/all-comments-page (.get-items comments-service) deleted added id))
	(GET "/comments/add" [] (view/add-comment-page))
	(GET "/comments/:id" [id] (view/comment-page (.get-item comments-service id) false))
	(GET "/comments/:id/:updated" [id updated] (view/comment-page (.get-item comments-service id) updated))
	
	(POST "/comment/add" request (do (.insert-item comments-service (create-comment 
												(get-in request [:params :userid]) 
												(get-in request [:params :text]) 
												(get-in request [:params :creationdate])
												(get-in request [:params :postid]))) 
								  (response/redirect (str "/comments/" (get-in request [:params :postid]) "/false/true"))))
	(POST "/comment/update" request (do (.update-item comments-service (create-comment 
												(get-in request [:params :id])
												(get-in request [:params :userid]) 
												(get-in request [:params :text]) 
												(get-in request [:params :creationdate])
												(get-in request [:params :postid]))) 
									 (response/redirect (str "/comment/" (get-in request [:params :id]) "/true"))))
	(POST "/comment/delete" request (do (.delete-item comments-service (get-in request [:params :id])) 
								     (response/redirect (str "/comments/" (get-in request [:params :id]) "/true/false"))))
	
	(route/resources "/"))

(def app  
	(-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))