(defproject gcp-dataflow-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.beam/beam-sdks-java-core "2.1.0"]
                 [org.apache.beam/beam-sdks-java-io-google-cloud-platform "2.0.0" :exclusions [io.grpc/grpc-core io.netty/netty-codec-http2]]
                 [org.apache.beam/beam-runners-google-cloud-dataflow-java "2.1.0" :exclusions [io.grpc/grpc-core io.netty/netty-codec-http2]]
                 [org.apache.beam/beam-runners-direct-java "2.1.0"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :main ^:skip-aot gcp-dataflow-clj.core
  :aot :all
  :target-path "target/%s"
  :plugins [[com.jakemccrary/lein-test-refresh "0.21.1"]]
  :profiles {:uberjar {:aot :all}})
