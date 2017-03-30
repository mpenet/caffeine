(ns qbits.caffeine.loading-cache
  (:refer-clojure :exclude [get])
  (:require
   [qbits.caffeine.options :as options]
   [qbits.caffeine.utils :as u])
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

(defn get
  ([^LoadingCache c k f]
   (.get c k (u/func f)))
  ([^LoadingCache c k]
   (.get c k)))

(defn get-all [^LoadingCache c k]
  (.getAll c k))

(defn refresh! [^LoadingCache c k]
  (.refresh c k))

(defn cleanup!
  [^LoadingCache c]
  (.cleanUp c))

(defn estimated-size [^LoadingCache c]
  (.estimatedSize c))

(defn get-all-present [^LoadingCache c ks]
  (.getAllPresent c ks))

(defn get-if-present [^LoadingCache c k]
  (.getIfPresent c k))

(defn invalidate!
  ([^LoadingCache c]
   (.invalidateAll c))
  ([^LoadingCache c k]
   (.invalidate c k)))

(defn policy [^LoadingCache c]
  (.policy c))

(defn put! [^LoadingCache c k v]
  (.put c k v))

(defn put-all! [^LoadingCache c m]
  (.putAll c m))

(defn stats [^LoadingCache c]
  (.stats c))
