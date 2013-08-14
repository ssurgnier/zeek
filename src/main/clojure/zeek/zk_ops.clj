(ns zeek.zk-ops
  "ZooKeeper operations")

(defn create [client path]
  (-> client .create (.forPath path)))

(defn delete [client path]
  (-> client .delete (.forPath path)))

(defn check-exists [client path]
  (-> client .checkExists (.forPath path)))

(defn get-data [client path]
  (-> client .getData (.forPath path)))

(defn set-data [client path data]
  (-> client .setData (.forPath path data)))

(defn get-children [client path]
  (-> client .getChildren (.forPath path)))
