(ns ^:figwheel-hooks lunchlearn.ui-main-test
  (:require [cljs.test]
            [cljs-test-display.core]
            [lunchlearn.ui-test])
  (:require-macros [cljs.test]))

(defn ^:after-load test-run []
  (println "reload")
  (cljs.test/run-tests
   (cljs-test-display.core/init! "app_tests")
   'lunchlearn.ui-test))

(defonce run (test-run))
