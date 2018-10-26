(ns character.core
  (:gen-class))

(defn testing
  [msg expected actual]
  (if (= expected actual)
    (println "Test" msg "is successful!")
    (println "Test" msg "have failed: Expected" expected "got" actual "instead!")))

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)
(c-str character)
(c-dex character)

(fn [c] (:strength (:attributes c)))

(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))

(spell-slots character)

(def spell-slots-comp (comp int inc #(/ % 2) c-int))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  ;(Thread/sleep 1000)
  x)
(sleepy-identity "Mr. Fantastico")
(sleepy-identity "Mr. Fantastico")

(def memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Mr. Fantastico")
(memo-sleepy-identity "Mr. Fantastico")

;___________________________________________________
;EXOS

;1. You used (comp :intelligence :attributes) to create a function that returns a character’s intelligence.
;   Create a new function, attr, that you can call like (attr :intelligence) and that does the same thing.
;2. Implement the comp function.
;3. Implement the assoc-in function. Hint: use the assoc function and define its parameters as [m [k & ks] v].
;4. Look up and use the update-in function.
;5. Implement update-in.

;1.
(defn attr
  [attr]
  (comp attr :attributes))

;2.
(defn comp2
  ([f g]
   (fn
     ([x] (f (g x)))
     ))
  )

;3.
(defn assoc-in2
  [m [k & ks] v]
    (assoc m k (assoc-in (get m k) ks v))
  )

;4,5.
(defn update-in2
  [m [k & ks] f & args]
  (if ks
    (assoc m k (apply update-in2 (get m k) ks f args))
    (assoc m k (apply f (get m k) args)))
  )

;___________________________________________________
;TESTS

(println "Testing attr function...")
(testing "of our function"
         10
         ((attr :intelligence) character))

(println "\nTesting comp2 function...")
(testing "of our function"
         10
         ((comp2 :intelligence :attributes) character)
         ;(:intelligence (:attributes character))
         )

(println "\nTesting assoc-in2 function...")
(testing "of our function"
         {:name "Smooches McCutes", :attributes {:intelligence 10, :strength 8, :dexterity 5}}
         ;(def character
         ;  {:name "Smooches McCutes"
         ;   :attributes {:intelligence 10
         ;                :strength 4
         ;                :dexterity 5}})
         (assoc-in2 character [:attributes :strength] 8))

(println "\nTesting update-in2 function...")
(testing "of our function"
         {:name "Smooches McCutes", :attributes {:intelligence 10, :strength 4, :dexterity 10}}
         (update-in2 character [:attributes :dexterity] * 2))

(println "\nTesting update-in2 function to update more values...")
(testing "of our function"
         {:name "Smooches McCutes", :attributes {:intelligence 20, :strength 8, :dexterity 10}}
         (update-in2 character [:attributes] assoc :intelligence 20 :strength 8 :dexterity 10))