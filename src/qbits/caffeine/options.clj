(ns qbits.caffeine.options
  (:import
   (com.github.benmanes.caffeine.cache Caffeine)
   (java.util.concurrent TimeUnit)))

(defmulti set-cache-option! (fn [k _c _v] k))

(defmethod set-cache-option! :expire-after-access
  [_ ^Caffeine c duration]
  (.expireAfterAccess c (long duration) TimeUnit/MILLISECONDS))

(defmethod set-cache-option! :expire-after-write
  [_ ^Caffeine c duration]
  (.expireAfterWrite c (long duration) TimeUnit/MILLISECONDS))

(defmethod set-cache-option! :refresh-after-write
  [_ ^Caffeine c duration]
  (.refreshAfterWrite c (long duration) TimeUnit/MILLISECONDS))

(defmethod set-cache-option! :initial-capacity
  [_ ^Caffeine c x]
  (.initialCapacity c x))

(defmethod set-cache-option! :maximum-size
  [_ ^Caffeine c s]
  (.maximumSize c s))

(defmethod set-cache-option! :maximum-weight
  [_ ^Caffeine c w]
  (.maximumWeight c w))

(defmethod set-cache-option! :executor
  [_ ^Caffeine c exec]
  (.executor c exec))

(defmethod set-cache-option! :weak-keys?
  [_ ^Caffeine c weak-keys?]
  (cond-> c
    weak-keys?
    (.weakKeys)))

(defmethod set-cache-option! :weak-values?
  [_ ^Caffeine c weak-values?]
  (cond-> c
    weak-values?
    (.weakValues)))

(defmethod set-cache-option! :soft-values?
  [_ ^Caffeine c soft-values?]
  (cond-> c
    soft-values?
    (.softValues)))

(defmethod set-cache-option! :record-stats?
  [_ ^Caffeine c record-stats?]
  (cond-> c
    record-stats?
    (.recordStats)))

(defn set-cache-options!
  ^Caffeine
  [^Caffeine cache options]
  (reduce (fn [cache [k option]]
            (set-cache-option! k cache option))
          cache
          options))
