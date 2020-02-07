(ns scrambls.utils)

(defn scramble? [str1 str2]
  (= (count str2) (count (remove nil? (map #(clojure.string/index-of str1 %) (vec str2))))))


(defn scramble-with-transducer? [str1 str2]
  (= (count str2) (count (into []
                               (comp (map #(clojure.string/index-of str1 %))
                                     (remove nil?))
                               (vec str2)))))