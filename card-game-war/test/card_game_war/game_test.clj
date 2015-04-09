(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= :player2
           (play-round [:diamond 8] [:club 10]))))
  (testing "queens are higher rank than jacks"
    (is (= :player2
           (play-round [:diamond :jack] [:club :queen]))))
  (testing "kings are higher rank than queens"
    (is (= :player1
           (play-round [:spade :king] [:club :queen]))))
  (testing "aces are higher rank than kings"
    (is (= :player2
           (play-round [:heart :king] [:spade :ace]))))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= :player1
           (play-round [:club 3] [:spade 3]))))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= :player2
           (play-round [:club :ace] [:diamond :ace]))))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= :player1
           (play-round [:heart :jack] [:diamond :jack])))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"))

