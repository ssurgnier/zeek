(defproject zeek "0.1.0-SNAPSHOT"
  :description "Simple ZooKeeper CLI"
  :url "http://github.com/ssurgnier/zeek"
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
  :main zeek.core
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.apache.curator/curator-client "2.2.0-incubating"]
                 [org.apache.curator/curator-framework "2.2.0-incubating"]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"})
