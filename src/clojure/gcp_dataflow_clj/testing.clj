(ns gcp-dataflow-clj.testing
  (:require [gcp-dataflow-clj.testing :as sut]
            [clojure.test :as t]))

(defn ff [context]
  (.output context (str "A" (.element context))))
