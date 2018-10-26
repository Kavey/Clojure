(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot...yet."
  [& args]
    (println "Hello, World!"))

;start TEST

(def test-addhundred [{:input 100 :expected 200} {:input 200 :expected 300}])
#_(def tests (test-fn [{:input1 9 :input2 24 :expected 15}
                       {:input1 -3 :input2 10 :expected 13}]))

;end TEST

;Use the str, vector, list, hash-map, and hash-set functions.
(defn basic-functions
  []
  (println "My name is Kevin!")
  (vector
    (list 1 2 3)
    (hash-map :a 1 :b 2 :c 3)
    (hash-set :a 1 :a 2 :a 3)))

;Write a function that takes a number and adds 100 to it.
(defn addhundred
  [number]
  (+ number 100))

;Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:
;
;(def dec9 (dec-maker 9))
;(dec9 10)
; => 1

(defn dec-maker
  [number]
  (fn [dec] (- dec number)))
; (- 25 number)

;Write a function, mapset, that works like map except the return value is a set:
;
;(mapset inc [1 1 2 2])
;=> #{2 3}

#_(defn mapset [a b]
    (into #{} (map a b)))

;Create a function that’s similar to symmetrize-body-parts except that it has to work with
;weird space aliens with radial symmetry. Instead of two eyes, arms, legs, and so on, they have five.
;
;Create a function that generalizes symmetrize-body-parts and the function you created in Exercise 5.
;The new function should take a collection of body parts and the number of matching body parts to add.
;If you’re completely new to Lisp languages and functional programming, it probably won’t be obvious
;how to do this. If you get stuck, just move on to the next chapter and revisit the problem later.
;
;(def asym-hobbit-body-parts [{:name "head" :size 3}
;                             {:name "left-eye" :size 1}
;                             {:name "left-ear" :size 1}
;                             {:name "mouth" :size 1}
;                             {:name "nose" :size 1}
;                             {:name "neck" :size 2}
;                             {:name "left-shoulder" :size 3}
;                             {:name "left-upper-arm" :size 3}
;                             {:name "chest" :size 10}
;                             {:name "back" :size 10}
;                             {:name "left-forearm" :size 3}
;                             {:name "abdomen" :size 6}
;                             {:name "left-kidney" :size 1}
;                             {:name "left-hand" :size 2}
;                             {:name "left-knee" :size 2}
;                             {:name "left-thigh" :size 4}
;                             {:name "left-lower-leg" :size 3}
;                             {:name "left-achilles" :size 1}
;                             {:name "left-foot" :size 2}])
;
;
;(defn matching-part
;  [part]
;  {:name (clojure.string/replace (:name part) #"^left-" "right-")
;   :size (:size part)})
;
; (defn symmetrize-body-parts
;  "Expects a seq of maps that have a :name and :size"
;  [asym-body-parts]
;   (loop [remaining-asym-parts asym-body-parts
;         final-body-parts []]
;     (if (empty? remaining-asym-parts)
;      final-body-parts
;       (let [[part & remaining] remaining-asym-parts]
;         (recur remaining
;               (into final-body-parts
;                     (set [part (matching-part part)])))))))
;
;(defn my-reduce
;  ([f initial coll]
;   (loop [result initial
;          remaining coll]
;     (if (empty? remaining)
;       result
;       (recur (f result (first remaining)) (rest remaining)))))
;  ([f [head & tail]]
;   (my-reduce f head tail)))
;
;(defn better-symmetrize-body-parts
;  "Expects a seq of maps that have a :name and :size"
;  [asym-body-parts]
;  (reduce (fn [final-body-parts part]
;            (into final-body-parts (set [part (matching-part part)])))
;          []
;          asym-body-parts))


;TESTS___________________________________________________

(println (str "\nStarting testing basic function..."))
(println (basic-functions))

(println (str "\nStarting testing addhundred function..."))
(doseq [test test-addhundred]
  (let [output (addhundred (:input test))]
    (if (not= output (:expected test))
      (println (str "FAIL for " (:input test) " : Expected " (:expected test) ", got " output " :-("))
      (println (str "SUCCESS for " (:input test) " :-)")))))

(println (str "\nStarting testing dec-maker function..."))
(let [dec-maker (fn [number] (fn [dec] (- dec number)))
      dec9      (dec-maker 9)
      test-fn   (fn [tests]
                  (doseq [test tests]
                    (let [output ((dec-maker (:input1 test)) (:input2 test))]
                      (if (not= output (:expected test))
                        (println (str "FAIL for " (:input1 test) " and " (:input2 test) ": Expected " (:expected test) ", got " output))
                        (println (str "SUCCESS for " (:input1 test) " and " (:input2 test)))))))])

;(println (str "\nStarting testing mapset function..."))