(ns aws.s3
  (:require [cognitect.aws.client.api :as aws]
            [cognitect.aws.credentials :as credentials]
            [aws.config :as conf]
            [mount.core :as mount :refer [defstate]]))

(defstate s3-creds
  :start (credentials/basic-credentials-provider
           {:access-key-id     (:access-key-id (conf/cget :aws-creds))
            :secret-access-key (:secret-access-key (conf/cget :aws-creds))}))

;; Create our S3 client using S3 creds
(defstate s3-client
  :start (aws/client {:api                  :s3
                      :region               (:region (conf/cget :s3-bucket))
                      :credentials-provider s3-creds}))

(defn list-buckets
  [client]
  (aws/invoke client {:op :ListBuckets}))


(comment

;; What buckets do we have already?
  (list-buckets s3-client)

  (def bucket-name (:name (conf/cget :s3-bucket)))

  (aws/invoke s3-client {:op :CreateBucket :request {:Bucket bucket-name}})

  ;; Let's add something to it, something from the web!
  (aws/invoke s3-client {:op      :PutObject
                         :request {:Bucket bucket-name
                                   :Key    "crabby-patty.html"
                                   :Body   (byte-array (map int (slurp "https://wikipedia.org")))}})

  ;; Let's get it back!
  (def secret-recipe
    (aws/invoke s3-client {:op      :GetObject
                           :request {:Bucket bucket-name
                                     :Key    "crabby-patty.html"}}))

  secret-recipe
  (spit "crabby-patty.html" (slurp (:Body secret-recipe))))