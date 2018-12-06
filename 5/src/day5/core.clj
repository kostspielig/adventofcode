(ns day5.core
  (require [clojure.string :as string])
  (:gen-class))


;; 65-90 (A-Z) 97-122 (a-z) = (+32)
(defn same-char-to-upper-or-lower [c1 c2]
  (if (or (nil? c1) (nil? c2))
    false
    (= (Math/abs (- c1 c2)) 32)))

(defn task1 [ascii]
  (first (reduce (fn [[acc prev] i]
                   (if (same-char-to-upper-or-lower prev i)
                     (if (= prev (last acc))
                       (let [next (pop acc)]
                         [next (last next)])
                       [acc prev])
                     [(conj acc i) i]))
                 [[] nil]
                 ascii)))

(defn same-char [c1 c2]
  (if (or (nil? c1) (nil? c2))
    false
    (let [diff (Math/abs (- c1 c2))]
      (or (= diff 32)
        (= diff 0)))))

(defn task2 [ascii]
  (apply
    min
    (map (fn [n]
           (count
             (task1
               (reduce
                 (fn [acc i]
                   (if (same-char i n)
                     acc
                     (conj acc i)))
                 []
                 ascii))))
         (range 65 91))))

(defn -main
  "Advent of code day 5."
  [& args]
  (let [input (str (read-string (slurp "input.txt")))]
    (println (count (task1 (map int input))))
    (println (task2 (map int input)))))
