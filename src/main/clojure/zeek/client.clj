(ns zeek.client
  (:import [org.apache.curator.retry ExponentialBackoffRetry]
           [org.apache.curator.framework CuratorFrameworkFactory]))

(def client (atom {}))

(def retry-policy
  (ExponentialBackoffRetry. 1000 3))

(defn new-client
  [{:keys [zookeeper]}]
  (CuratorFrameworkFactory/newClient zookeeper retry-policy))
