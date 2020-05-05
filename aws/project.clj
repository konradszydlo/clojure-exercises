(defproject aws "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.cognitect.aws/api "0.8.456"]
                 [com.cognitect.aws/endpoints "1.1.11.753"]
                 [com.cognitect.aws/s3 "784.2.593.0"]
                 [com.grammarly/omniconf "0.4.1"]
                 [mount "0.1.16"]
                 [nrepl "0.7.0"]]
  :main ^:skip-aot aws.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
