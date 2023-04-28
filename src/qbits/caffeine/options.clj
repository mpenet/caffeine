(ns qbits.caffeine.options
  (:require [clojure.spec.alpha :as s])
  (:import
   (com.github.benmanes.caffeine.cache Caffeine)
   (java.util.concurrent TimeUnit Executor)))

(defmulti set-cache-option! (fn [k _c _v] k))

(s/def :qbits.caffeine.options/expire-after-access pos-int?)
(defmethod set-cache-option! :expire-after-access
  [_ ^Caffeine c duration]
  (.expireAfterAccess c (long duration) TimeUnit/MILLISECONDS))

(s/def :qbits.caffeine.options/expire-after-write pos-int?)
(defmethod set-cache-option! :expire-after-write
  [_ ^Caffeine c duration]
  (.expireAfterWrite c (long duration) TimeUnit/MILLISECONDS))

(s/def :qbits.caffeine.options/refresh-after-write pos-int?)
(defmethod set-cache-option! :refresh-after-write
  [_ ^Caffeine c duration]
  (.refreshAfterWrite c (long duration) TimeUnit/MILLISECONDS))

(s/def :qbits.caffeine.options/initial-capacity nat-int?)
(defmethod set-cache-option! :initial-capacity
  [_ ^Caffeine c x]
  (.initialCapacity c x))

(s/def :qbits.caffeine.options/maximum-size nat-int?)
(defmethod set-cache-option! :maximum-size
  [_ ^Caffeine c s]
  (.maximumSize c s))

(s/def :qbits.caffeine.options/maximum-weight nat-int?)
(defmethod set-cache-option! :maximum-weight
  [_ ^Caffeine c w]
  (.maximumWeight c w))

(s/def :qbits.caffeine.options/executor #(instance? Executor %))
(defmethod set-cache-option! :executor
  [_ ^Caffeine c exec]
  (.executor c exec))

(s/def :qbits.caffeine.options/weak-keys? boolean?)
(defmethod set-cache-option! :weak-keys?
  [_ ^Caffeine c weak-keys?]
  (cond-> c
    weak-keys?
    (.weakKeys)))

(s/def :qbits.caffeine.options/weak-values? boolean?)
(defmethod set-cache-option! :weak-values?
  [_ ^Caffeine c weak-values?]
  (cond-> c
    weak-values?
    (.weakValues)))

(s/def :qbits.caffeine.options/soft-values? boolean?)
(defmethod set-cache-option! :soft-values?
  [_ ^Caffeine c soft-values?]
  (cond-> c
    soft-values?
    (.softValues)))

(s/def :qbits.caffeine.options/record-stats? boolean?)
(defmethod set-cache-option! :record-stats?
  [_ ^Caffeine c record-stats?]
  (cond-> c
    record-stats?
    (.recordStats)))

(defmethod set-cache-option! :default
  [_ ^Caffeine c _]
  c)

(s/def :qbits.caffeine/options
  (s/keys :opt-un [:qbits.caffeine.options/expire-after-access
                   :qbits.caffeine.options/expire-after-write
                   :qbits.caffeine.options/refresh-after-write
                   :qbits.caffeine.options/initial-capacity
                   :qbits.caffeine.options/maximum-size
                   :qbits.caffeine.options/maximum-weight
                   :qbits.caffeine.options/executor
                   :qbits.caffeine.options/weak-keys?
                   :qbits.caffeine.options/weak-values?
                   :qbits.caffeine.options/soft-values?
                   :qbits.caffeine.options/record-stats?]))

(defn set-cache-options!
  ^Caffeine
  [^Caffeine cache options]
  (s/assert :qbits.caffeine/options options)
  (reduce (fn [cache [k option]]
            (set-cache-option! k cache option))
          cache
          options))
