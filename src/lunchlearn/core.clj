(ns lunchlearn.core
  (:require [clojure.repl :refer :all]))

; defining a symbol (i.e. var)
(def a 42)


; lists
'(1 2 3 4)


; vectors
[1 2 3 4]


; sets
#{1 2 3 4}


; map
(def user {:user/first-name "Adam"
           :user/last-name "Miller"})

user

(get user :user/first-name)
(:user/first-name user)
(user :user/first-name)

; keyword (with namespace)
:user/first-name
