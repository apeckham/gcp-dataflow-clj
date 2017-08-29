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

(def extract-words
  (ClojureDoFn.
   (fn [context]
     (doseq [word (->> #"[^\\p{L}]+"
                       (str/split (.element context))
                       (filter (complement str/blank?)))]
       (.output context word)))))

(def format-results
  (proxy [SimpleFunction] []
    (apply [input]
      (str (.getKey input) ": " (.getValue input)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [options (.as (.withValidation (PipelineOptionsFactory/fromArgs (into-array String args))) WordCountOptions)
        p (Pipeline/create)]
    (prn options)
    (-> p
        (.apply (-> (TextIO/read)
                    (.from "gs://apache-beam-samples/shakespeare/*")))
        (.apply "ExtractWords" (ParDo/of extract-words))
        (.apply (Count/perElement))
        (.apply "FormatResults" (MapElements/via format-results))
        (.setCoder (StringUtf8Coder/of))
        (.apply (-> (TextIO/write)
                    (.to "wordcounts"))))
    (-> p .run .waitUntilFinish)))
