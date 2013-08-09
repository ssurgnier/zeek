(ns zeek.ops
  "ZooKeeper operations")

(defn get-data [client path]
  (-> client .getData (.forPath path)))

(defn get-children [client path]
  (-> client .getChildren (.forPath path)))

(defn create [client path]
  (-> client .create (.forPath path)))

(defn delete [client path]
  (-> client .delete (.forPath path)))

(defn check-exists [client path]
  (-> client .checkExists (.forPath path)))
