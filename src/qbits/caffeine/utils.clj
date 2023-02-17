(ns qbits.caffeine.utils
  (:import
   (com.github.benmanes.caffeine.cache CacheLoader)
   (java.util.function Function)))

(defn func
  ^Function [f]
  (reify Function
    (apply [_this args]
      (f args))))

(defn cache-loader
  ^CacheLoader
  [f]
  (reify CacheLoader
    (load [_this k]
      (f k))))
