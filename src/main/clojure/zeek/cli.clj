(ns zeek.cli
  (require [zeek.commands :as cmd]
           [zeek.ops :as ops]))

(defn split-input
  [input]
  (try
    (clojure.string/split input #"\s+")
    (catch RuntimeException e
      (println "could not parse input"))))

(defn eval-input [{:keys [pwd] :as state} input]
  (let [[command & args] (split-input input)]
    (case command
      "cd" (assoc state :pwd (cmd/cd pwd args))
      "get" (do (cmd/zkget pwd args) state)
      "exit" (System/exit 0)
      true (println "unknown command"))))

(defn prompt [{:keys [pwd]}]
  (println (str pwd " $ ")))
