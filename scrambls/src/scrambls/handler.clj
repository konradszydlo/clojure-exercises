(ns scrambls.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [defroutes POST context]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [scrambls.utils :refer [scramble?]]))


(defn process-scramble [str1 str2]
  (scramble? str1 str2))

(defn process-scramble-post [{:keys [body]}]
  (let [str1 (get body "str1")
        str2 (get body "str2")]
    (if (or (clojure.string/blank? str1) (clojure.string/blank? str2))
      {:status 422
       :body {:message "Please provide two strings"}}
      {:status 200
       :body   {:status (process-scramble str1 str2)}})))

(defroutes api-routes
           (POST "/scramble" req (process-scramble-post req)))

(defn api-handler []
  (-> api-routes
      wrap-json-response
      wrap-json-body))

(defroutes app-routes
  (context "/api" [] (api-handler))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))