(ns aws.core
  (:require [aws.config :as cfg]
            [aws.s3]
            [mount.core :as mount]
            [nrepl.server :refer [start-server]]))

(defn -main
  [& args]
  (-> (mount/only #{#'aws.config/config})
      (mount/with-args {:cli args})
      mount/start)
  (start-server :port (cfg/cget :nrepl-port))
  (mount/start))