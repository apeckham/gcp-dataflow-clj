(ns gcp-dataflow-clj.core-test
  (:require [clojure.test :refer :all]
            [gcp-dataflow-clj.core :refer :all])
  (:import org.apache.beam.runners.dataflow.DataflowRunner))

(deftest my-options
  (is (= DataflowRunner (.getRunner (args->options ["--runner=DataflowRunner"])))))
