(ns ^:figwheel-hooks lunchlearn.main
  (:require [reagent.core :as reagent]
            [lunchlearn.ui :as ui]))


(enable-console-print!)


;; usage of javascript interop
;; render main view using reagent (our react wrapper library)
(defn ^:after-load mount
  []
  (reagent/render [ui/main-view]
                  (.getElementById js/document "app")))


(defonce run
  (mount))
