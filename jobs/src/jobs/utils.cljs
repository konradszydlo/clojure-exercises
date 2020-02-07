(ns jobs.utils
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [cuerdas.core :as str-querdas]))

;;; TODO provide actual Algolia credentials
(def algolia-api-key "")
(def algolia-app-id "")
(def algolia-url "")

(defn perform-search [search-term search-atom]
  (go (let [response (<! (http/post algolia-url
                                   {:with-credentials? false
                                    :headers {"X-Algolia-API-Key" algolia-api-key
                                              "X-Algolia-Application-Id" algolia-app-id}
                                    :json-params {"query" search-term}}))]

        (if (= 200 (:status response))
          (reset! search-atom (:hits (:body response)))))))

(defn format-job-location [{:keys [city country country-code state] :as location} ]
  (println location)
  (if (false? (str-querdas/blank? state))
    (apply str (interpose ", " [city state country-code]))
    (if city
      (str city ", " country))))

(def currencies {"GBP" "£"
                 "USD" "$"})

(defn- format-money [money]
  (str (/ money 1000) "K"))

;;; $40K - 50K
(defn format-salary [{:keys [competitive currency min max ]}]
  (if competitive
    "Competitive"
    (str-querdas/format "%s%s - %s" (get currencies currency)
                        (format-money min)
                        (format-money max))))
