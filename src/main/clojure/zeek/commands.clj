(ns zeek.commands
  (require [zeek.ops :as ops]
           [zeek.client :refer [client]]))

(defn zkget [pwd [path]]
  (let [full-path (cond
                   (nil? path) pwd
                   (.startsWith path "/") path
                   (.startsWith path "..") (do (println "TODO") pwd)
                   (= path ".") pwd
                   (.startsWith path ".") (do (println "TODO") pwd)
                   true path)]
    (println (ops/get-data @client full-path))))

(defn cd [pwd [path]]
  (cond
   (nil? path) "/"
   (.startsWith path "/") path
   (.startsWith path "..") (do (println "TODO") pwd)
   (= path ".") pwd
   (.startsWith path ".") (do (println "TODO") pwd)
   true path))
