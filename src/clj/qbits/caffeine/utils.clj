(ns qbits.caffeine.utils
  (:import
   (com.github.benmanes.caffeine.cache CacheLoader)
   (java.util.function Function)))

(defn ^Function func [f]
  (reify Function
    (apply [this args]
      (f args))))

(defn ^CacheLoader cache-loader [f]
  (reify CacheLoader
    (load [this k]
      (f k))))
