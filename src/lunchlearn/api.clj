(ns lunchlearn.api
  (:require [clj-http.client :as http.client]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; API URI'S
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def top-stories-uri "https://hacker-news.firebaseio.com/v0/topstories.json")
(def story-detail-uri "https://hacker-news.firebaseio.com/v0/item/%d.json")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; API QUERY FNS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn top-stories
  [uri]
  (lazy-seq
   (:body
    (http.client/get uri
                     {:accept :json
                      :as :json}))))


(defn top-story-detail
  [uri id]
  (:body
   (http.client/get (format uri id)
                    {:accept :json
                     :as :json})))


(comment

  (top-stories top-stories-uri)

  (def id (second (top-stories top-stories-uri)))

  (top-story-detail story-detail-uri id)

  (select-keys (top-story-detail story-detail-uri id) [:title :url :score])


  (time
   (let [x 10]
     (->> (top-stories top-stories-uri)
          (take x)
          (pmap (partial top-story-detail story-detail-uri)) ;; higher order fns
          (map :score)
          (reduce +)
          (* (/ 1 x)) ;; (1/10 * [total_story_points])
          (double))))


  )
