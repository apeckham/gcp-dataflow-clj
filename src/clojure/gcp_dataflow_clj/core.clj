(ns gcp-dataflow-clj.core
  (:gen-class)
  (:require [clojure.string :as str]
            gcp-dataflow-clj.my-options)
  (:import beam.ClojureDoFn
           MyOptions
           org.apache.beam.sdk.coders.StringUtf8Coder
           org.apache.beam.sdk.io.TextIO
           org.apache.beam.sdk.options.PipelineOptionsFactory
           org.apache.beam.sdk.Pipeline
           [org.apache.beam.sdk.transforms Count DoFnTester MapElements ParDo SimpleFunction]))

(defn par-do [f]
  (ParDo/of (ClojureDoFn. f)))

(defn simple-fn [f]
  (proxy [SimpleFunction] []
    (apply [input]
      (f input))))

(defn extract-words [context]
  (doseq [word (->> #"[^\\p{L}]+"
                    (str/split (.element context))
                    (filter (complement str/blank?)))]
    (.output context word)))

(defn format-results [input]
  (str (.getKey input) ": " (.getValue input)))

(defn args->options [args]
  (-> String
      (into-array args)
      PipelineOptionsFactory/fromArgs
      .withValidation
      (.as MyOptions)))

(defn -main
  [& args]
  (let [p (Pipeline/create (args->options args))]
    (-> p
        (.apply (-> (TextIO/read)
                    (.from "gs://apache-beam-samples/shakespeare/*")))
        (.apply "ExtractWords" (par-do extract-words))
        (.apply (Count/perElement))
        (.apply "FormatResults" (MapElements/via (simple-fn format-results)))
        (.setCoder (StringUtf8Coder/of))
        (.apply (-> (TextIO/write)
                    (.to "wordcounts"))))
    (-> p .run .waitUntilFinish)))
