(ns zeek.cli-commands-test
  (:use [clojure.test])
  (:require [zeek.cli-commands :as cmd]
            [zeek.zk-ops :as ops]))

(deftest test-ls
  (testing "should sort the returned vector"
    (with-redefs [ops/get-children (fn [_ path] ["a" "c" "b"])]
      (is (= (list "a" "b" "c")
             (cmd/ls {:client nil :pwd "/"} ["."]))))))
