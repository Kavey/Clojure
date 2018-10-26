(ns readingevaluation.core
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

;; 1. Use the list function, quoting, and read-string to create a list that, when evaluated,
;; prints your first name and your favorite sci-fi movie.

(def listing
  (eval (list (read-string "print") "Kevin:" "Star Wars")))

;; 2. Create an infix function that takes a list like (1 + 3 * 4 - 5) and transforms it into the lists
;; that Clojure needs in order to correctly evaluate the expression using operator precedence rules.

(defn identical
  [lst lst2]
  (identical? lst lst2))

(def valid-operators
  #{'+ '- '* '/})
; set les opérateurs valides

(defn every-equal
  [lst]
  (apply = lst))
; pour égalité des opérateurs

(defn even-equal
  [lst]
  (every-equal (take-nth 2 (rest lst))))
; vérifie si tout les opérateurs sont les mêmes

(defn valid-operator
  [lst]
  ;; TODO: check si l'opérateur est correct (+-*/)
  (contains? valid-operators (second lst)))
; récuperer le premier opérateur et vérifier si il est dans le set opérateur

(defn move-even
  [lst]
  (conj (take-nth 2 lst) (second lst)))
; déplace les opérateur au début de l'expression

(defmacro same-infix
  [eval]
  (if (and (even-equal eval) (valid-operator eval))
    (move-even eval)
    (println "Operators are not valid or are not our same!")))

(defmacro infix
  [eval]
  )

; éclater la macro en petites fonctions

;; TESTS
;; ___________________________________________________________________________________________________

(println "\n\nTesting indentical function...")
(testing "of our function"
         true
         (identical (* 2 2) 4))

(testing "of our function"
         false
         (identical (* 4 4) 4))

(println "\nTesting every-equal function...")
(testing "of our function"
         true
         (every-equal '(1 1 1)))

(testing "of our function"
         false
         (every-equal '(1 2 1)))

(testing "of our function"
         true
         (every-equal '(z z z)))

(testing "of our function"
         false
         (every-equal '(x y z)))

(println "\nTesting even-equal function...")
(testing "of our function"
         true
         (even-equal '(1 + 2 + 3 + 4 + 5)))

(testing "of our function"
         true
         (even-equal '(1 - 2 - 3 - 4 - 5)))

(testing "of our function"
         true
         (even-equal '(1 * 2 * 3 * 4 * 5)))

(testing "of our function"
         true
         (even-equal '(1 / 2 / 3 / 4 / 5)))

(testing "of our function"
         false
         (even-equal '(1 + 2 - 3 + 4 - 5)))

(println "\nTesting valid-operator function...")
(testing "of our function"
         true
         (valid-operator '(1 + 2 + 3 + 4 + 5)))

(testing "of our function"
         true
         (valid-operator '(1 + 2 - 3 * 4 / 5)))

(testing "of our function"
         true
         (valid-operator '(1 + 2 - 3 * 4 / 5)))

(testing "of our function"
         false
         (valid-operator '(1 7 2 7 3 7 4 7 5)))

(testing "of our function"
         false
         (valid-operator '(1 y 2 y 3 y 4 y 5)))

(testing "of our function"
         false
         (valid-operator '(1 w 2 x 3 y 4 z 5)))

(println "\nTesting same-infix macro...")
(testing "of our function"
         (+ 1 2 3 4 5)
         (same-infix (1 + 2 + 3 + 4 + 5)))

(testing "of our function"
         (- 1 2 3 4 5)
         (same-infix (1 - 2 - 3 - 4 - 5)))

(testing "of our function"
         (* 1 2 3 4 5)
         (same-infix (1 * 2 * 3 * 4 * 5)))

(testing "of our function"
         (/ 1 2 3 4 5)
         (same-infix (1 / 2 / 3 / 4 / 5)))

(testing "of our function"
         nil
         (same-infix (1 + 2 - 3 * 4 / 5)))

(testing "of our function"
         nil
         (same-infix (1 1 2 2 3 3 4 4 5)))

(testing "of our function"
         nil
         (same-infix '(1 x 2 x 3 x 4 x 5)))

(testing "of our function"
         nil
         (same-infix '(1 a 2 b 3 c 4 d 5)))