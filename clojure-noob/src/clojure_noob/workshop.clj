(ns clojure-noob.workshop)

(defn toto []
  (println "toto"))

(toto)

(defn error [n]
  (let [x 0]
    (/ n x)))

(error 1)