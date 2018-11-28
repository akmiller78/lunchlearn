(ns lunchlearn.devcards
  (:require [devcards.core :as devcards]
            [reagent.core :as reagent]
            [lunchlearn.ui :as ui])
  (:require-macros
   [devcards.core :refer [defcard defcard-rg]]))


(defcard-rg sample-card
  [:div
   [:h3 "Test Card"]
   [:p "Just an example of the simple idea of devcard"]])


(defcard-rg todo-item
  (let [state (reagent/atom {:id 1
                             :text "this is a test item"
                             :status :active
                             :highlight false})
        on-change (fn [id checked]
                    (swap! state assoc :status (if checked :done :active)))]
    (fn []
      [ui/todo-item @state on-change])))


(defcard-rg todo-item-highlight
  [ui/todo-item {:id 1
                 :text "this is a test item"
                 :status :active
                 :highlight true}])


(def todo-item-list-state (reagent/atom {:items {"1" {:id 1 :text "test 1" :status :active :highlight false}
                                                 "2" {:id 2 :text "test 2" :status :active :highlight false}}}))

(defcard todo-item-list
  (devcards/reagent ui/todo-list)
  todo-item-list-state
  {:inspect-data true :history true})

(def todo-item-list-state-add-item (reagent/atom {:items {"1" {:id 1 :text "test 1" :status :active :highlight false}
                                                          "2" {:id 2 :text "test 2" :status :active :highlight false}}}))

(defcard add-item
  (devcards/reagent ui/add-item)
  todo-item-list-state)


(defonce devcard-ui (devcards/start-devcard-ui!))
