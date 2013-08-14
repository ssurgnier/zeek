(ns zeek.cli-commands
  (require [zeek.zk-ops :as ops]))

(defn zkget
  "TODO need to concat default cond with pwd"
  [{:keys [client pwd]} [path]]
  (let [full-path (cond
                   (nil? path) pwd
                   (.startsWith path "/") path
                   (.startsWith path "..") (do (println "TODO") pwd)
                   (= path ".") pwd
                   (.startsWith path ".") (do (println "TODO") pwd)
                   true path)]
    (ops/get-data client full-path)))

(defn- if-exists-then-cd
  [{:keys [client pwd path]}]
  (if (ops/check-exists client path)
    path
    (do (println "No such file or directory")
        pwd)))

(defn cd [{:keys [client pwd] :as state} [path]]
  (cond
   (nil? path) "/"
   (.startsWith path "/") (if-exists-then-cd (assoc state :path path))
   (.startsWith path "..") (do (println "TODO") pwd)
   (= path ".") pwd
   (.startsWith path ".") (do (println "TODO") pwd)
   true (->> path
             (str (when-not (= pwd "/") pwd) "/")
             (assoc state :path)
             if-exists-then-cd)))

(defn ls
  "TODO need to concat default cond with pwd"
  [{:keys [client pwd]} [path]]
  (let [full-path
        (cond
         (nil? path) pwd
         (.startsWith path "/") path
         (.startsWith path "..") (do (println "TODO") pwd)
         (= path ".") pwd
         (.startsWith path ".") (do (println "TODO") pwd)
         true path)]
    (sort (into [] (ops/get-children client full-path)))))

(defn rm
  "TODO need to concat default cond with pwd"
  [{:keys [client pwd]} [path]]
  (let [full-path
        (cond
         (nil? path) (do (println "TODO") nil)
         (.startsWith path "/") (do (println "Operation not permitted") nil)
         (.startsWith path "..") (do (println "TODO") nil)
         (= path ".") (do (println "TODO") nil)
         (.startsWith path ".") (do (println "TODO") nil)
         true path)]
    (ops/delete client full-path)))
