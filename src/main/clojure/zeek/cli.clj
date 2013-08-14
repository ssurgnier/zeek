(ns zeek.cli
  (require [zeek.cli-commands :as cmd]))

(defn split-input
  [input]
  (try
    (clojure.string/split input #"\s+")
    (catch RuntimeException e
      (println "could not parse input"))))

(def output println)

(defn eval-input [state input]
  (let [[command & args] (split-input input)]
    (case command
      "cd" (assoc state :pwd (cmd/cd state args))
      "ls" (do (output (cmd/ls state args)) state)
      "rm" (do (output (cmd/rm state args)) state)
      "get" (do (output (cmd/zkget state args)) state)
      "exit" (System/exit 0)
      (do (println "unknown command") state))))

(defn prompt
  "TOOD <username>@<zookeeper-host>:pwd $"
  [{:keys [pwd]}]
  (print (str pwd " $ ")))
