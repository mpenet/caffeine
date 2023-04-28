(ns qbits.caffeine
  (:refer-clojure :exclude [get])
  (:require [qbits.caffeine.options :as options]
            [qbits.caffeine.utils :as u])
  (:import (com.github.benmanes.caffeine.cache
            AsyncCache
            AsyncLoadingCache
            Caffeine
            Cache
            LoadingCache)
           (java.util.concurrent CompletableFuture)))

(set! *warn-on-reflection* true)

(defn cache
  ^Cache [options]
  (-> (Caffeine/newBuilder)
      (options/set-cache-options! options)
      (.build)))

(defn loading-cache
  ^LoadingCache
  [options f]
  (-> (Caffeine/newBuilder)
      (options/set-cache-options! options)
      (.build (u/cache-loader f))))

(defn async-cache
  ^AsyncCache
  [options]
  (-> (Caffeine/newBuilder)
      (options/set-cache-options! options)
      (.buildAsync)))

(defn async-loading-cache
  ^AsyncLoadingCache
  [options f]
  (-> (Caffeine/newBuilder)
      (options/set-cache-options! options)
      (.buildAsync (u/async-cache-loader f))))

(defprotocol Cacheable
  (-put! [c k x]
    "Sets new value `x` on cache `c` for key `k`")
  (-get [c k] [c k f]
    "Retrieves value from cache under `k`, if `f` is supplied it will be used to compute a missing value"))

(extend-protocol Cacheable
  Cache
  (-put! [c k x]
    (.put c k x))

  (-get [c k]
    (.getIfPresent c k))

  LoadingCache
  (-put! [c k x]
    (.put c k x))

  (-get
    ([c k] (.get c k))
    ([c k f]
     (.get c k (u/func f))))

  AsyncCache
  (-put! [c k cf]
    (.put c k cf))

  (-get
    ([c k]
     (or (.getIfPresent c k)
         (CompletableFuture/completedFuture nil)))
    ([c k f]
     (.get c k (u/bifunc f))))

  AsyncLoadingCache
  (-put! [c k cf]
    (.put c k cf))

  (-get
    ([c k]
     (.get c k))
    ([c k f]
     (.get c k (u/bifunc f)))))

(def get -get)
(def put! -put!)

;; (let [c (async-cache {})]
;;   (-put! c "a" (qbits.auspex/success-future "asdf"))
;;   (-get c "a" (fn [& _] (qbits.auspex/success-future 2)))
;;   @(-get c "a")
;;   @(-get c ""))

;; (let [c (async-loading-cache {} (fn [k x] (qbits.auspex/success-future 3)))]
;;   ;; (-put! c "a" (qbits.auspex/success-future 1))
;;   ;; @(-get c "a" (fn [& args] (qbits.auspex/success-future 3)))
;;   ;; @(-get c "a")
;;   ;; @(-get c "b")
;;   )

;; (let [c (cache {})]
;;   (-put! c "a" 2)
;;   (-get c "a"))

;; (let [c (loading-cache {}
;;                        (fn [& args]
;;                          (prn args)
;;                          3))]
;;   (-put! c "a" 2)
;;   (-get c "basdf" (fn [k] 4)))
