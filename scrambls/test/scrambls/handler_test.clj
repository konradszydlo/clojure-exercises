(ns scrambls.handler-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [ring.mock.request :as mock]
            [scrambls.handler :refer :all]
            [scrambls.utils :refer :all]))

(deftest test-scramble
  (testing "Test scramble?"
    (testing "Valid scramble"
      (testing "Using are macro"
        (are [actual] (true? actual)
                      (scramble? "rekqodlw" "world")
                      (scramble? "cedewaraaossoqqyt" "codewars")
                      (scramble? "foo" "foo")
                      (scramble? "ofo" "foo")))
      (testing "Using test check"
        (testing "Scrambled string"
          (defspec scramble-test-check-scrambled-string 100
                   (prop/for-all [str1 gen/string-ascii]
                                 (true? (scramble? (scramble str1) str1)))))
        (testing "Concatenated string"
          (defspec scramble-test-check-concatenated-string 100
                   (prop/for-all [str1 gen/string-ascii
                                  str2 gen/string-ascii]
                                 (true? (scramble? (str str1 str2) str1)))))))
    (testing "Invalid scramble"
      (are [actual] (false? actual)
                    (scramble? "katas" "steak")
                    (scramble? "fo" "foo"))))

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