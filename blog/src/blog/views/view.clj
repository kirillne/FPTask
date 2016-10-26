(ns blog.views.view
	(:use hiccup.page
		  hiccup.element
     :require [blog.views.layout :as layout]))

(defn index-page [] 
	(layout/render
    "home.html" {:docs "document"}))