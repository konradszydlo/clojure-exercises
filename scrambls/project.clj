(defproject scrambls "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.bhauman/figwheel-main "0.2.3"]
                 [com.bhauman/rebel-readline-cljs "0.1.4"]
                 [compojure "1.6.1"]
                 [cljs-http "0.1.46"]
                 [rum "0.11.4"]
                 [ring/ring-json "0.5.0"
                  :exclusions [ring/ring-core]]]
  :plugins [[lein-cljsbuild "1.1.3"]]
  :resource-paths ["resources" "target"]

  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
            "build-dev" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})