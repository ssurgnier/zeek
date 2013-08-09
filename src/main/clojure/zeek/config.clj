(ns zeek.config)

(defn load-config
  [path]
  (-> path slurp load-string))
