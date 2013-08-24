(ns zeek.core
  "Configuration file must eval to a map of the form:
  {:env1 {:zookeeper ^String a-zk-connect-string}
   :env2 {:zookeeper ^String another-zk-connect-string}
   :default :env1}"
  (:require [zeek.config :refer [load-config]]
            [zeek.client :refer [client new-client]]
            [zeek.cli :refer [eval-input prompt]]
            [clojure.string :refer [split]])
  (import java.net.InetAddress)
  (:gen-class))

(defn run
  ;; ([] (find config in io/resource))
  ;; ([config] #(run config (:default config)))
  ([config-path env & args]
     (let [config (load-config config-path)
           env-config (get config (keyword env))
           host (-> (.getHostName (InetAddress/getLocalHost))
                    (split #"\.")
                    first)]
       (reset! client (new-client env-config))
       (.start @client)
       (try
         (loop [state {:client @client :pwd "/" :host host :env env}]
           (prompt state)
           (flush)
           (->> (read-line) (eval-input state) (recur)))
         (finally (.close @client))))))

(def -main run)
