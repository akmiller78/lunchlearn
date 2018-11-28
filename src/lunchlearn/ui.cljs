(ns lunchlearn.ui
  (:require [reagent.core :as reagent]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; app-state / state fns
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Common design patter in ClojureScript (and React)
;; is centralized state management
(def app-state (reagent/atom {:title "Todo List"
                              :items {}}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; events
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Multi-methods provide convenient and powerful
;; mechanism for doing polymorphism.
;; Multi-method defines a dispatching fn along with
;; 1 more methods
;; Using here to manage all "events" in my application

(defmulti dispatch
  (fn [state k & ks]
    k))


(defmethod dispatch :add-item
  [state _ item]
  (swap! state assoc-in [:items (str (:id item))] item)
  (.setTimeout js/window #(dispatch state :remove-highlight (:id item)) 3000))


(defmethod dispatch :remove-highlight
  [state _ id]
  (when (contains? (:items @state) (str id))
    (swap! state assoc-in [:items (str id) :highlight] false)))


(defmethod dispatch :clear-items
  [state _]
  (swap! state assoc :items {}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helper functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn create-item [text]
  {:id (random-uuid)
   :text text
   :status :active
   :highlight true})


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; View Components
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn todo-item
  [{:keys [id text status highlight]} on-change]
  (let [opts (cond
               (= status :done) {:class "done"}
               highlight {:class "highlight"})]
    [:div
     [:label opts
      [:input {:type "checkbox"
               :defaultChecked (= status :done)
               :on-change #(let [checked (-> % .-target .-checked)]
                             (on-change id checked))}]
      text]]))


(defn todo-list
  [state]
  (let [items (:items @state)
        ks (keys items)
        on-change (fn [id checked]
                    (swap! state
                           assoc-in
                           [:items (str id) :status]
                           (if checked :done :active)))]
    [:div
     (for [k ks]
       ^{:key k} [todo-item (get items k) on-change])]))


;; controlled input where it's re-rendered as the state changes and its
;; value managed by internal state
(defn add-item
  [state]
  (let [val (reagent/atom "")]
    (fn []
      [:div
       [:input {:type "text"
                :value @val
                :on-change #(reset! val (-> % .-target .-value))}]
       [:button {:on-click #(when (not (clojure.string/blank? @val))
                              (let [item (create-item @val)]
                                (dispatch state :add-item item)
                                (reset! val "")))}
        "Add Item"]])))


;; Utilizing Reagent
;; Inner fn is the render function for React
(defn main-view
  "Main view for our todo app."
  []
  (fn []
    (let [title (:title @app-state)]
      [:div
       [:h1 title]
       [:div
        [:button
         {:on-click #(dispatch app-state :clear-items)}
         "Clear"]]
       [todo-list app-state]
       [add-item app-state]])))
