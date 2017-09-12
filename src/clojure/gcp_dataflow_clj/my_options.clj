(ns gcp-dataflow-clj.my-options)

(gen-interface
 :name gcp-dataflow-clj.my-options.MyOptions
 :extends [org.apache.beam.sdk.options.PipelineOptions]
 :methods [[^{org.apache.beam.sdk.options.Description "Foo test parameter"}
            getFoos [] String]
           [setFoos [String] void]])
