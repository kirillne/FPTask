(ns blog.utils.messages-utils
	(:require [clojure.string :as s]))

(defn fix-validation-message [message]
	(let [string (str message)]
	(s/capitalize (subs string  2 (- (count string) 2) ))))

(defn update-validation-message [messages upkey]
	{upkey (fix-validation-message (upkey messages))})

(defn fix-validation-messages [messages]
	(into {} (map #(update-validation-message messages %1) (keys messages))))