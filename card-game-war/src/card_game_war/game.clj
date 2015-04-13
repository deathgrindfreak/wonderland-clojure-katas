(ns card-game-war.game
  (:gen-class)
  (:require [clojure.tools.trace :refer [deftrace]]))

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn play-round [player1-card player2-card]
  (let [[rank1 value1] player1-card
        [rank2 value2] player2-card
        high-cards {:jack 11 :queen 12 :king 13 :ace 14}
        ranks {:spade 0 :club 1 :diamond 2 :heart 3}]

    (defn comp-vals [val1 val2]
      (cond (> val1 val2) :player1
            (< val1 val2) :player2
            :else (if (> (rank1 ranks)
                         (rank2 ranks))
                    :player1
                    :player2)))

    (cond
      (and (keyword? value1) (keyword? value2))
      (let [hi1 (value1 high-cards)
            hi2 (value2 high-cards)]
        (cond (> hi1 hi2) :player1
              (< hi1 hi2) :player2
              :else (comp-vals hi1 hi2)))
      (and (keyword? value1)
           (not (keyword? value2))) :player1
      (and (not (keyword? value1))
           (keyword? value2)) :player2
      :else (comp-vals value1 value2))))

(deftrace play-game [player1-cards player2-cards]
  (loop [player1 (into (clojure.lang.PersistentQueue/EMPTY)
                       player1-cards)
         player2 (into (clojure.lang.PersistentQueue/EMPTY)
                       player2-cards)]
    (let [top1 (peek player1)
          top2 (peek player2)
          rest1 (pop player1)
          rest2 (pop player2)
          play (play-round top1 top2)
          [r1 r2] (shuffle [top1 top2])]
      (cond (nil? top1) :player2
            (nil? top2) :player1
            (= play :player1) (recur (conj (conj rest1 r1) r2) rest2)
            :else (recur rest1 (conj (conj rest2 r1) r2))))))

(defn shuffle-cards [deck]
  (defn shuf [cards p1 p2]
    (if (empty? cards)
      [p1 p2]
      (let [[f s & rest] cards]
        (recur rest (conj p1 f) (conj p2 s)))))
  (shuf deck [] []))

(defn play-random-game []
  (let [[p1-cards p2-cards] (shuffle-cards cards)]
    (play-game p1-cards p2-cards)))

(defn -main [& args]
  (play-random-game))
