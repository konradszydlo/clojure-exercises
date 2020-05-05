(ns aws.config
  (:require [mount.core :as mount :refer [defstate]]
            [omniconf.core :as cfg]))

(defn init-config []
  (cfg/define
    {:conf-file {:type :file
                 :verifier cfg/verify-file-exists
                 :description "Config file"}
     :aws-creds {:type :edn
                 :secret true
                 :default {:access-key-id ""
                           :secret-access-key ""}}
     :nrepl-port {:type :number
                  :default 7999}
     :s3-bucket {:type :edn
                 :default {:name ""
                           :region ""}}})
  (let [args (mount/args)
        quit-on-error (some? (:cli args))]
    (cfg/populate-from-cmd (:cli args))
    (when-let [conf-file (cfg/get :conf-file)]
      (cfg/populate-from-file conf-file))
    (cfg/populate-from-env)
    (cfg/verify :quit-on-error quit-on-error)))

(defstate config
          :start (init-config))

(def cget cfg/get)