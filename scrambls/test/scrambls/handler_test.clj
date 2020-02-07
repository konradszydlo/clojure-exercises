(ns scrambls.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [scrambls.handler :refer :all]
            [scrambls.utils :refer :all]))

(deftest test-scramble
  (testing "Test scramble?"
    (testing "Valid scramble"
      (are [actual] (true? actual)
                    (scramble? "rekqodlw" "world")
                    (scramble? "cedewaraaossoqqyt" "codewars")))
    (testing "Invalid scramble"
      (is (false? (scramble? "katas" "steak")))))

  (testing "Test scramble-with-transducer?"
    (testing "Valid scramble"
      (are [actual] (true? actual)
                    (scramble-with-transducer? "rekqodlw" "world")
                    (scramble-with-transducer? "cedewaraaossoqqyt" "codewars")))
    (testing "Invalid scramble"
      (is (false? (scramble-with-transducer? "katas" "steak"))))))

;;; 0.073705 msecs
(time (scramble? "rekqodlw" "world"))

;;; 0.096656 msecs
(time (scramble? "cedewaraaossoqqyt" "codewars"))

;;; 0.074944 msecs
(time (scramble? "katas" "steak"))

;;; 0.095563
(time (scramble-with-transducer? "rekqodlw" "world"))

;;; 0.075963
(time (scramble-with-transducer? "cedewaraaossoqqyt" "codewars"))

;;; 0.11431
(time (scramble-with-transducer? "katas" "steak"))