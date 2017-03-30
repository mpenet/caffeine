(ns qbits.caffeine.cache
  (:refer-clojure :exclude [get])
  (:require
   [qbits.caffeine.options :as options]
   [qbits.caffeine.utils :as u])
  (:import
   (com.github.benmanes.caffeine.cache
    Caffeine
    Cache)))

(defn ^Cache make
  [options f]
  (-> (Caffeine/newBuilder)
      (options/set-cache-options! options)
      (.build)))

(defn get [^Cache c k f]
  (.get c k (u/func f)))

(defn cleanup!
  [^Cache c]
  (.cleanUp c))

(defn estimated-size [^Cache c]
  (.estimatedSize c))

(defn get-all-present [^Cache c ks]
  (.getAllPresent c ks))

(defn get-if-present [^Cache c k]
  (.getIfPresent c k))

(defn invalidate!
  ([^Cache c]
   (.invalidateAll c))
  ([^Cache c k]
   (.invalidate c k)))

(defn policy [^Cache c]
  (.policy c))

(defn put! [^Cache c k v]
  (.put c k v))

(defn put-all! [^Cache c m]
  (.putAll c m))

(defn stats [^Cache c]
  (.stats c))
