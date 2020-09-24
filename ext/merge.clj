(ns test
  (:require [clojure.pprint :refer :all]))

(defn deep-merge [v & vs]
  (letfn [(rec-merge [v1 v2]
                     (if (and (map? v1) (map? v2))
                       (merge-with deep-merge v1 v2)
                       v2))]
    (when (some identity vs)
      (reduce #(rec-merge %1 %2) v vs))))

(spit "deps-temp.edn"
 (with-out-str 
   (pprint (deep-merge (read-string (slurp "deps.edn"))
                       (read-string (slurp (str "/home/build/.clojure/" (System/getenv "DEPLOY_TARGET") ".edn")))
                       (read-string (slurp "/home/build/.clojure/deps.edn"))))))
