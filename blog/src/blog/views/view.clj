(ns blog.views.view
	(:use hiccup.page
		  hiccup.element))

(defn index-page [] 
	(html5
		[:html
			[:head]
			[:body "Alex"]]))