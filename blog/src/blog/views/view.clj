(ns blog.views.view
	(:use hiccup.page
		  hiccup.element
     :require [blog.views.layout :as layout]))

(defn index-page [] 
	(layout/render
    "home.html" {:docs "document"}))

(defn all-users-page [users deleted added param]
	(layout/render
		"users/all_users.html" {:users users :deleted deleted :added added :param param}))

(defn user-page [user updated]
	(layout/render
		"users/user.html" {:user user :updated updated}))

(defn add-user-page []
	(layout/render
		"users/add_user.html"))

(defn all-profiles-page [profiles deleted added param]
	(layout/render
		"profiles/all_profiles.html" {:profiles profiles :deleted deleted :added added :param param}))

(defn profile-page [profile updated]
	(layout/render
		"profiles/profile.html" {:profile profile :updated updated}))

(defn add-profile-page []
	(layout/render
		"profiles/add_profile.html"))

(defn all-posts-page [posts deleted added param]
	(layout/render
		"posts/all_posts.html" {:posts posts :deleted deleted :added added :param param}))

(defn post-page [post updated]
	(layout/render
		"posts/post.html" {:post post :updated updated}))

(defn add-post-page []
	(layout/render
		"posts/add_post.html"))
