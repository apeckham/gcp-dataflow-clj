(ns gcp-dataflow-clj.core-test
  (:require [clojure.test :refer :all]
            [gcp-dataflow-clj.testing :as testing]
            [gcp-dataflow-clj.core :refer :all])
  (:import beam.ClojureDoFn
           org.apache.beam.runners.dataflow.DataflowRunner
           org.apache.beam.sdk.transforms.DoFnTester))

(deftest my-options
  (let [opts (args->options ["--runner=DataflowRunner" "--foos=barbaz"])]
    (is (= DataflowRunner (.getRunner opts)))
    (is (= "barbaz" (.getFoos opts)))))

(deftest do-fn-tester
  (let [tester (DoFnTester/of (ClojureDoFn. testing/ff))]
    (is (= (repeat 20 "Azzz") (.processBundle tester (repeat 20 "zzz"))))))
