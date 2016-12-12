(ns blog.controllers.dsl-controller
	(:use 
		[blog.dal.protocols.common-protocol]
		[blog.dal.protocols.posts-protocol]
		[blog.dal.protocols.profiles-protocol]
		[blog.dal.protocols.comments-protocol]
		[blog.dal.protocols.users-protocol])
	(:require 
		[blog.dal.repositories.posts-repository]
		[blog.dal.repositories.profiles-repository]
		[blog.dal.repositories.comments-repository]
		[blog.dal.repositories.users-repository]
		[blog.layout :as layout]
		[bouncer.core :as b]
        [bouncer.validators :as v]
        [ring.util.response :refer [redirect]]
        [clojure.string :as s])
	(:import  
		[blog.dal.repositories.posts_repository posts-repository]
		[blog.dal.repositories.profiles_repository profiles-repository]
		[blog.dal.repositories.comments_repository comments-repository]
		[blog.dal.repositories.users_repository users-repository]))


(def post-repository (posts-repository.))
(def profile-repository (profiles-repository.))
(def comment-repository (comments-repository.))
(def user-repository (users-repository.))

(defn get-page [request]
	 (layout/render request "dsl.html" {:message "", :result ""}))

(defn get-command [string]
	(if (= string "get-all") 
		(str "dsl-" string "-items")
		(str "dsl-" string "-item"))
	)


(defn get-arg-map [string]
	(println string)
	(let [parts (s/split string #"=") 
		name (s/lower-case(get parts 0))
		value (get parts 1)]
		{(keyword name) value})
	)

(defn get-params [parts]
	(let [args 
		(for [x (range 2 (count parts))] 
			(get-arg-map (get parts x))
			)]
		(into {} args))
	)

(defn invoke [request]
	(let 
		[query (get-in request [:form-params "query"])
		command-parts (s/split query #"\s+")
		entity (s/lower-case(get command-parts 0))
		command (s/lower-case(get-command (get command-parts 1)))
		params (get-params command-parts)
		command-string (str "(blog.controllers.dsl-controller/" command " blog.controllers.dsl-controller/" entity "-repository" " " params  ")")]
		(try
     		{:result (str (eval (read-string command-string)) ) }
     	(catch  clojure.lang.Compiler$CompilerException e 
     		{:message "No such entity or command" } 
			)
     	(catch Exception e 
     		{:message (.getMessage e) } 
		)))
	)
		
(defn invoke-command [request]
	 (layout/render request "dsl.html" (invoke request)))

(defn dsl-get-item [this params] (into {} (get-item this params)))
(defn dsl-create-item [this params] (create-item this (assoc params :id nil)))
(defn dsl-update-item [this params] (update-item this params))
(defn dsl-delete-item [this params] (delete-item this params))
(defn dsl-get-all-items [this params] (s/join "\n\n" (map #(into {} %1	) (get-all-items this))))
