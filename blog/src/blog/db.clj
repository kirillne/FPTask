(ns blog.db
	(:require [clojure.java.jdbc :as j]))

  (def db-spec{:classname "com.microsoft.jdbc.sqlserver.SQLServerDriver"
               :subprotocol "sqlserver"
               :subname "//localhost:1433;database=BlogDb;integratedSecurity=true"} )

  (defn get-users []
    (j/query db-spec
             ["select * from Users"]
             ))
