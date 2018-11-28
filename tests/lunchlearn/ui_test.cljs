(ns lunchlearn.ui-test
  (:require [cljs.test :refer-macros [deftest is]]
            [lunchlearn.ui]))


(deftest should-not-pass
  (is (= 20 20)))


(deftest create-item
  (let [item (lunchlearn.ui/create-item "This is a test item")]
    (is (= "This is a test item" (:text item)))
    (is (= true (:highlight item)))))
