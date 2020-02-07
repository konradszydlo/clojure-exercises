(ns scrambls.ajax
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn perform-search [str1 str2 state]
      (go (let [response (<! (http/post "http://localhost:9500/api/scramble"
                                        {:with-credentials? false
                                         :json-params {"str1" str1 "str2" str2}}))]
               (case (:status response)
                     200 (swap! state update-in [:scramble]
                                (fn [] {:is-valid-scramble (:status (:body response)) :scramble-request-done true}))
                     (swap! state update-in [:scramble]
                            (fn [] {:scramble-error true :scramble-request-done true}))))))