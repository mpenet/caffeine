(ns qbits.caffeine.utils
  (:import
   (com.github.benmanes.caffeine.cache AsyncCacheLoader CacheLoader)
   (java.util.function BiFunction Function)))

(defn func
  ^Function [f]
  (reify Function
    (apply [_this args]
      (f args))))

(defn bifunc
  ^BiFunction
  [f]
  (reify BiFunction
    (apply [_this x y]
      (f x y))))

(defn cache-loader
  ^CacheLoader
  [f]
  (reify CacheLoader
    (load [_this k]
      (f k))))

(defn async-cache-loader
  ^AsyncCacheLoader
  [f]
  (reify AsyncCacheLoader
    (asyncLoad [_this k executor]
      (f k executor))))
