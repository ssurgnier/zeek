(ns zeek.core
  "Configuration file must eval to a map of the form:
  {:env1 ^String a-zk-connect-string}
   :env2 ^String another-zk-connect-string}"
  (:require [zeek.config :refer [load-config]]
            [zeek.client :refer [client new-client]]
            [zeek.cli :refer [eval-input prompt]]
            [clojure.string :refer [split]])
  (:import java.net.InetAddress)
  (:gen-class))

(defn run
  ;; ([] (find config in io/resource))
  ;; ([config] #(run config (:default config)))
  ([config-path env & args]
     (let [config (load-config config-path)
           zookeeper (get config (keyword env))
           host (-> (.getHostName (InetAddress/getLocalHost))
                    (split #"\.")
                    first)]
       (reset! client (new-client zookeeper))
       (.start @client)
       (try
         (loop [state {:client @client :pwd "/" :host host :env env}]
           (prompt state)
           (flush)
           (->> (read-line) (eval-input state) (recur)))
         (finally (.close @client))))))

(def -main run)
