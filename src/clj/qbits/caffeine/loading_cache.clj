(ns qbits.caffeine.loading-cache
  (:refer-clojure :exclude [get])
  (:require [qbits.caffeine.options :as options])
  (:import
   (com.github.benmanes.caffeine.cache
    Caffeine
    LoadingCache
    CacheLoader)))

(defn ^CacheLoader cache-loader [f]
  (reify CacheLoader
    (load [this k]
      (f k))))

(defn ^LoadingCache make
  [options f]
  (-> (Caffeine/newBuilder)
      (options/set-cache-options! options)
      (.build (cache-loader f))))

(defn get [^LoadingCache c k]
  (.get c k))

(defn get-all [^LoadingCache c k]
  (.getAll c k))

(defn refresh! [^LoadingCache c k]
  (.refresh c k))
