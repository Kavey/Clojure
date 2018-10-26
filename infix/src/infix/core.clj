(ns infix.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn testing
  [msg expected actual]
  ;; TODO:
  ;; 1. Should log "Test {msg} successful" if expected and actual are the same
  ;; 2. Should log "Test {msg} failed: Expected {expected}, got {actual} instead" else
  ;; 3. Returns true if they are equal, and false else
  (if (= expected actual)
    (println "Test" msg "is successful!")
    (println "Test" msg "have failed: Expected" expected "got" actual "instead!")
    ;(do (if (= expected actual)
    ;      true
    ;      false))
    ))

(def sample-tree {:operator '+
                  :child1   {:operator '*
                             :child1   1
                             :child2   2}
                  :child2   {:operator '*
                             :child1   3
                             :child2   4}})

(defn infix-to-tree
  [node]
  (if (list? node)
    {:operator (nth node 1)
     :child1   (infix-to-tree (nth node 0))
     :child2   (infix-to-tree (nth node 2))}
    node))

(defn not-map                                               ; not used
  [child]
  (not (map? child)))

(defn tree-to-prefix                                        ; not used
  [tree]
  (filter not-map
          (tree-seq map? vals tree)))
;
; (+ * 1 2 * 3 4)
; (+ (* 1 2) (* 3 4))

(defn infix-to-prefix [node]
  (if (list? node)
    (let [first-child (infix-to-prefix (nth node 0))
          operator    (nth node 1)
          last-child  (infix-to-prefix (nth node 2))]
      (list operator first-child last-child))
    node))

;; TESTS _______________________________________________________________________________

(println "Printing tree...")
(println sample-tree "\n")
(println "      +")
(println "    /   \\")
(println "   *     *")
(println "  / \\   / \\")
(println " 1   2 3   4")

(println "\nTesting infix-to-tree function...")
(testing "when input is a proper list"
         sample-tree
         (infix-to-tree '((1 * 2) + (3 * 4))))
(testing "when input is not a list"
         '1
         (infix-to-tree 1))

(println "\nTesting tree-to-prefix function...")
(testing "when input is a proper list"
         '(+ * 1 2 * 3 4)
         (tree-to-prefix sample-tree))
(testing "when input is not a list"
         '(1)
         (tree-to-prefix 1))

(println "\nTesting infix-to-prefix function...")
(testing "of the function"
         '(+ (* 1 2) (* 3 4))
         (infix-to-prefix '((1 * 2) + (3 * 4))))

(testing "of the function"
         '(+ (* 1 2) (* 3 4))
         (infix-to-prefix '((1 * 2) + (3 * 4))))