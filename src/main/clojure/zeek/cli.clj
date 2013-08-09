(ns zeek.cli
  (require [zeek.commands :as cmd]
           [zeek.ops :as ops]))

(defn split-input
  [input]
  (try
    (clojure.string/split input #"\s+")
    (catch RuntimeException e
      (println "could not parse input"))))

(defn eval-input [state input]
  (let [[command & args] (split-input input)]
    (case command
      "cd" (assoc state :pwd (cmd/cd state args))
      "get" (do (cmd/zkget state args) state)
      "exit" (System/exit 0)
      (println "unknown command"))))

(defn prompt [{:keys [pwd]}]
  (println (str pwd " $ ")))
