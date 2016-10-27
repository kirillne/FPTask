(ns blog.views.view
	(:use hiccup.page
		  hiccup.element
     :require [blog.views.layout :as layout]))

(defn index-page [] 
	(layout/render
    "home.html" {:docs "document"}))

(defn all-users-page [users]
	(layout/render
		"users/all_users.html" {:users users}))

(defn user-page [user]
	(layout/render
		"users/user.html" {:user user}))