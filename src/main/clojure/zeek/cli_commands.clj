(ns zeek.cli-commands
  (require [zeek.zk-ops :as ops]
           [clojure.string :as str :refer [split join]]))

(defn- if-exists-then-cd
  [{:keys [client pwd path]}]
  (if (ops/check-exists client path)
    path
    (do (println "No such file or directory")
        pwd)))

(defn- dotdot [pwd path]
  (let [new-pwd (->> (split pwd #"/")
                     butlast
                     rest
                     (join "/")
                     (str "/"))
        rest-path (->> (split path #"/")
                       rest
                       (join "/"))]
    [new-pwd rest-path]))

(defn- dot [pwd path]
  (let [rest-path (->> (split path #"/")
                       rest
                       (join "/"))]
    [pwd rest-path]))

(defn- join-pwd-path [pwd path]
  (str pwd (when-not (= pwd "/") "/") path))

(defn get-full-path [pwd path]
  (cond
   (nil? path) pwd
   (empty? path) pwd
   (.startsWith path "/") path
   (.startsWith path "..") (apply get-full-path (dotdot pwd path))
   (.startsWith path ".") (apply get-full-path (dot pwd path))
   true (join-pwd-path pwd path)))

(defn zkget "Return data at a znode"
  [{:keys [client pwd]} [path]]
  (let [full-path (get-full-path pwd path)]
    (ops/get-data client full-path)))

(defn cd "Return new pwd"
  [{:keys [client pwd] :as state} [path]]
  (let [full-path (get-full-path pwd path)]
    (if-exists-then-cd (assoc state :path full-path))))

(defn ls "Return a list of children at a znode"
  [{:keys [client pwd]} [path]]
  (let [full-path (get-full-path pwd path)]
    (sort (into [] (ops/get-children client full-path)))))

(defn rm "Remove a znode"
  [{:keys [client pwd]} [path]]
  (let [full-path (get-full-path pwd path)]
    (if (= full-path "/") (do (println "Operation not permitted") nil)
        (ops/delete client full-path))))
