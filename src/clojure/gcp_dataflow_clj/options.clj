(ns gcp-dataflow-clj.options)

(gen-interface
 :name WordCountOptions
 :extends [org.apache.beam.sdk.options.PipelineOptions]
 :methods [[^{org.apache.beam.sdk.options.Description "Foo test parameter"}
            getFoo [] String]
           [setFoo [String] void]])
