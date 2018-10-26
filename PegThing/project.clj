(defproject pegthing "0.1.0-SNAPSHOT"
  :description "Clojure for the Brave - Peg Thing"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot pegthing.core
  :profiles {:uberjar {:aot :all}})
