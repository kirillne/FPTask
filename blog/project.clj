(defproject blog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.7"]
            [lein-localrepo "0.5.3"]]
  :ring {:handler blog.core/app
  		   :auto-reload? true
  		   :auto-refresh? false}
  :native-path "C:/Program Files/Java/jre1.8.0_111/bin"
  :dependencies [[org.clojure/clojure "1.8.0"]
  				       [ring/ring "1.5.0"]
  				       [ring/ring-json "0.4.0"]
  				       [compojure "1.5.1"]
  				       [hiccup "1.0.5"]
				         [org.clojure/java.jdbc "0.6.2-alpha3"]
                 [sqljdbc4/sqljdbc4 "4.0"]])
