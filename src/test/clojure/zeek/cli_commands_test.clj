(ns zeek.cli-commands-test
  (:use [clojure.test])
  (:require [zeek.cli-commands :as cmd]
            [zeek.zk-ops :as ops]))

(deftest test-get-full-path
  (testing "should stay in pwd"
    (is (= "/" (cmd/get-full-path "/" "")))
    (is (= "/foo" (cmd/get-full-path  "/foo" "")))
    (is (= "/foo/bar" (cmd/get-full-path "/foo/bar" "")))
    (is (= "/" (cmd/get-full-path "/" nil)))
    (is (= "/foo" (cmd/get-full-path  "/foo" nil)))
    (is (= "/foo/bar" (cmd/get-full-path "/foo/bar" nil))))
  (testing "should move to path"
    (is (= "/foo" (cmd/get-full-path "/" "foo")))
    (is (= "/foo/bar" (cmd/get-full-path "/" "foo/bar")))
    (is (= "/foo/bar/baz" (cmd/get-full-path "/foo/bar" "baz"))))
  (testing "should move up a directory"
    (is (= "/" (cmd/get-full-path "/" "..")))
    (is (= "/" (cmd/get-full-path "/foo" "..")))
    (is (= "/" (cmd/get-full-path "/foo/" "..")))
    (is (= "/foo" (cmd/get-full-path "/foo/bar" ".."))))
  (testing "should move up two directories"
    (is (= "/" (cmd/get-full-path "/foo/bar" "../..")))
    (is (= "/foo" (cmd/get-full-path "/foo/bar/baz" "../.."))))
  (testing "should move up and then down a directory"
    (is (= "/bar" (cmd/get-full-path "/foo" "../bar")))
    (is (= "/foo/baz" (cmd/get-full-path "/foo/bar" "../baz"))))
  (testing "should move up two directories and then down one"
    (is (= "/baz" (cmd/get-full-path "/foo/bar" "../../baz")))
    (is (= "/foo/buh" (cmd/get-full-path "/foo/bar/baz" "../../buh")))))

(deftest test-ls
  (testing "should sort the returned vector"
    (with-redefs [ops/get-children (fn [_ path] ["a" "c" "b"])]
      (is (= (list "a" "b" "c")
             (cmd/ls {:client nil :pwd "/"} ["."]))))))

(deftest test-cd
  (testing "should return pwd if path does not exist"
    (with-redefs [ops/check-exists (fn [_ _] false)]
      (is (= "/" (cmd/cd {:client nil :pwd "/"} ["."])))))
  (testing "should return pwd if path does not exist"
    (with-redefs [ops/check-exists (fn [_ _] true)]
      (is (= "/foo" (cmd/cd {:client nil :pwd "/"} ["/foo"]))))))
