(ns qbits.caffeine.utils
  (:import (java.util.function Function)))

(defn ^Function func [f]
  (reify Function
    (apply [this args]
      (f args))))
