(ns qbits.caffeine
  (:require [qbits.commons.ns :as uns]))

(doseq [module '(cache loading-cache)]
  (uns/alias-ns (symbol (str "qbits.caffeine." module))))
