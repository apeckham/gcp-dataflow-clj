(ns gcp-dataflow-clj.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:import beam.ClojureDoFn
           org.apache.beam.sdk.coders.StringUtf8Coder
           org.apache.beam.sdk.io.TextIO
           org.apache.beam.sdk.options.PipelineOptionsFactory
           WordCountOptions
           org.apache.beam.sdk.Pipeline
           [org.apache.beam.sdk.transforms Count MapElements ParDo SimpleFunction]))

(defn par-do [f]
  (ParDo/of (ClojureDoFn. f)))

(defn extract-words [context]
  (doseq [word (->> #"[^\\p{L}]+"
                    (str/split (.element context))
                    (filter (complement str/blank?)))]
    (.output context word)))

(def format-results
  (proxy [SimpleFunction] []
    (apply [input]
      (str (.getKey input) ": " (.getValue input)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [options-factory (PipelineOptionsFactory/fromArgs (into-array String args))
        options (-> options-factory .withValidation (.as WordCountOptions))
        p (Pipeline/create)]
    (prn options)
    (-> p
        (.apply (-> (TextIO/read)
                    (.from "gs://apache-beam-samples/shakespeare/*")))
        (.apply "ExtractWords" (par-do extract-words))
        (.apply (Count/perElement))
        (.apply "FormatResults" (MapElements/via format-results))
        (.setCoder (StringUtf8Coder/of))
        (.apply (-> (TextIO/write)
                    (.to "wordcounts"))))
    (-> p .run .waitUntilFinish)))
