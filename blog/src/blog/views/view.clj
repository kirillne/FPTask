(ns blog.views.view
	(:use hiccup.page
		  hiccup.element
     :require [blog.views.layout :as layout]))

(defn index-page [user posts] 
	(layout/render
    "home.html" {:user user :posts posts}))

(defn registration-page [] 
	(layout/render
    "account/registration.html"))

(defn signin-page [] 
	(layout/render
    "account/signin.html"))


(defn all-users-page [users deleted added param]
	(layout/render
		"users/all_users.html" {:users users :deleted deleted :added added :param param}))

(defn user-page [user updated]
	(layout/render
		"users/user.html" {:user user :updated updated}))

(defn add-user-page []
	(layout/render
		"users/add_user.html"))