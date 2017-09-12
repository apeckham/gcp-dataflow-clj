(ns gcp-dataflow-clj.core-test
  (:require [clojure.test :refer :all]
            [gcp-dataflow-clj.core :refer :all])
  (:import org.apache.beam.runners.dataflow.DataflowRunner))

(deftest my-options
  (let [opts (args->options ["--runner=DataflowRunner" "--foos=barbaz"])]
    (is (= DataflowRunner (.getRunner opts)))
    (is (= "barbaz" (.getFoos opts)))))
