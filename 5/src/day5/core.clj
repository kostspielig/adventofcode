(ns day5.core
  (require [clojure.string :as string])
  (:gen-class))


;; 65-90 (A-Z) 97-122 (a-z) = (+32)
(defn same-char [c1 c2]
  (if (or (nil? c1) (nil? c2))
    false
    (= (Math/abs (- c1 c2)) 32)))

(defn task1 [ascii]
  (first (reduce (fn [[acc prev] i]
                   (if (same-char prev i)
                     (if (= prev (last acc))
                       (let [next (pop acc)]
                         [next (last next)])
                       [acc prev])
                     [(conj acc i) i]))
                 [[] nil]
                 ascii)))


(defn -main
  "Advent of code day 5."
  [& args]
  (let [input (str (read-string (slurp "input.txt")))]
    (println (count (task1 (map int input))))))
