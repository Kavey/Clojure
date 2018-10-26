(ns fwpd.core)
(def csv-suspects "/home/kevinsariego/Clojure/fwpd/resources/suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn testing
  [msg expected actual]
  (if (= expected actual)
    (println "Test" msg "is successful!")
    (println "Test" msg "have failed: Expected" expected "got" actual "instead!")
    ;(do (if (= expected actual)
    ;      true
    ;      false))
    ))

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name          identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  "Filter suspects from CSV with glitter-index >= 3"
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;____________________________________________________________________________________________

(defn first-suspects
  [csv]
  (mapify
    (parse
      (slurp csv))))

;1. Turn the result of your glitter filter into a list of names.
(defn get-filter-result-into-name
  [records]
  (map #(:name %) records))

(defn filter-result-into-name
  [csv]
  (doall (get-filter-result-into-name
           (glitter-filter 3                                ;"Filter suspects from CSV with glitter-index >= 3"
                           (mapify                          ;"Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
                             (parse                         ;"Convert a CSV into rows of columns"
                               (slurp csv)))))))            ;"Opens a reader on suspects.csv and reads all its contents, returning a string.

;3. Write a function, validate, which will check that :name and :glitter-index are present
;when you append. The validate function should accept two arguments: a map of keywords to
;validating functions, similar to conversions, and the record to be validated.
(defn validate
  [key]
  (and (contains? key :name)
       (contains? key :glitter-index)))

;2. Write a function, append, which will append a new suspect to your list of suspects.
(defn append
  [lst new-suspect]
  (if (validate new-suspect)
    ;(conj [lst] new-suspect)
    (conj lst new-suspect)
    (println "Invalid suspect!")))

;4. Write a function that will take your list of maps and convert it back to a CSV string.
;You’ll need to use the clojure.string/join function.
(defn get-names
  [hashmap-n]
  (:name hashmap-n))

(defn get-glitter-indexes
  [hashmap-g]
  (:glitter-index hashmap-g))

(defn get-both
  [hashmap-b]
  (clojure.string/join "," [(get-names hashmap-b) (get-glitter-indexes hashmap-b)])
  ;[(get-names hashmap-b) (get-glitter-indexes hashmap-b)]
  )

(defn convert-vector-to-csv
  [vector]
  (clojure.string/join "\n" (map get-both
                                 vector)))
;(clojure.string/join "," (get-both vector))

;TESTS
;____________________________________________________________________________________________
;
(println "Making sure my test function is working!")
(testing "of our function"
         4
         (+ 2 2))

(println "\nMaking sure my test function is working!")
(testing "of our function"
         10
         (+ 2 2))

(println "\nStarting testing our raw list of suspect...")
(testing "of our function"
         '({:name "Edward Cullen", :glitter-index 10}
            {:name "Bella Swan", :glitter-index 0}
            {:name "Charlie Swan", :glitter-index 0}
            {:name "Jacob Black", :glitter-index 3}
            {:name "Carlisle Cullen", :glitter-index 6}
            {:name "John", :glitter-index 5})
         (first-suspects csv-suspects))


;1. Turn the result of your glitter filter into a list of names.
(println "\nStarting testing our glitter filter for a list of names...")
(testing "of our function"
         '("Edward Cullen" "Jacob Black" "Carlisle Cullen" "John")
         (filter-result-into-name csv-suspects))

;2. Write a function, append, which will append a new suspect to your list of suspects.
(println "\nStarting testing append function...")
(testing "of our function"
         [{:name "John", :glitter-index 5}
          {:name "Jack", :glitter-index 10}
          {:name "Hulk Casstou", :glitter-index 3}]
         (append [{:name "John" :glitter-index 5}
                  {:name "Jack" :glitter-index 10}]
                 {:name "Hulk Casstou" :glitter-index 3}))

;3. Write a function, validate, which will check that :name and :glitter-index are present
;when you append. The validate function should accept two arguments: a map of keywords to
;validating functions, similar to conversions, and the record to be validated.
(println "\nStarting testing our validate function...")
(testing "of our function"
         true
         (validate {:glitter-index 5 :name "Bachibouzouk"}))
(testing "of our function"
         false
         (validate {:name "Luck Skywalker"}))

;4. Write a function that will take your list of maps and convert it back to a CSV string.
;You’ll need to use the clojure.string/join function.
(println "\nStarting testing our map to CSV converter function...")
(testing "of our function"
         "John,5"
         (get-both {:name "John" :glitter-index 5}))
(testing "of our function"
         "Jack,10"
         (get-both {:name "Jack" :glitter-index 10}))

(println "\nStarting testing our vector to CSV converter function...")
(testing "of our function"
         "John,5\nJack,10"
         (convert-vector-to-csv [{:name "John" :glitter-index 5} {:name "Jack" :glitter-index 10}]))