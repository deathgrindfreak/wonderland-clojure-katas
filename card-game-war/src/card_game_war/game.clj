(ns card-game-war.game)

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

(defn play-game [player1-cards player2-cards]
  (cond (empty? player1-cards) :player2
        (empty? player2-cards) :player1
        :else (let [[top1 rest1] player1-cards
                    [top2 rest2] player2-cards
                    play (play-round top1 top2)]
                (if (= play :player1)
                  (play-game (conj player1-cards
                                   top2)
                             rest2)
                  (play-game rest1
                             (conj player2-cards
                                   top1))))))
