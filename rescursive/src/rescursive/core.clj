(ns rescursive.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn testing
  [msg expected actual]
  (if (= expected actual)
    (println "Test" msg "is successful!")
    (println "Test" msg "have failed: Expected" expected "got" actual "instead!")
    ;(do (if (= expected actual)
    ;      true
    ;      false))
    ))

;; écrire fn factorielle recursive

(defn factorielle
  [n]
  (if (< n 2)
    1
    (* n (factorielle (- n 1)))
    ))

;; ((n * factorielle (n - 1))

(defn fibonacci
  [n]
  (if (< n 2)
    1
    (+ (fibonacci (- n 1)) (fibonacci (- n 2)))
    ))

(def f-memo (memoize fibonacci))

;; Pour N si c'est plus grand que 1, alors ça vaut la valeur pour N-1 plus+ la valeur pour N-2

;;TESTS ______________________________________________________

(testing "of our function"
         1
         (factorielle 0))

(testing "of our function"
         1
         (factorielle 1))

(testing "of our function"
         120
         (factorielle 5))

(println "\n")

(testing "of our function"
         1
         (fibonacci 0))

(testing "of our function"
         1
         (fibonacci 1))

(testing "of our function"
         2
         (fibonacci 2))

(testing "of our function"
         3
         (fibonacci 3))

(testing "of our function"
         5
         (fibonacci 4))

(testing "of our function"
         8
         (fibonacci 5))

(testing "of our function"
         55
         (fibonacci 9))

(testing "of our function"
         987
         (fibonacci 15))

