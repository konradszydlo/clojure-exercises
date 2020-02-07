(ns ^:figwheel-hooks jobs.core
  (:require
    [jobs.utils :as utils]
   [goog.dom :as gdom]
   [rum.core :as rum]))

(println "This text is printed from src/jobs/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (* a b))


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:jobs []}))

(def jobs (rum/cursor-in app-state [:jobs]))

(defn get-app-element []
  (gdom/getElement "app"))

(rum/defcs search-component <
  (rum/local "" ::search-term)
  [state]
  (let [search-term (::search-term state)]
    [:.search-panel
     [:input {:placeholder "Search for a job"
              :on-change   #(reset! search-term (.. % -target -value))
              }]
     [:button {:on-click #(utils/perform-search @search-term jobs)} "Search"]]))

(rum/defc job-tag [tag-name]
  [:span.tag tag-name])

(rum/defc job-component [job]
  [:div.column.is-one-third
   [:.job
    [:.logo
     [:img {:src (:logo (:company job))}]
     [:span.icon
      [:i.fas.fa-heart]]]
    [:.job-info
     [:.job-teaser
      [:.job-title (:title job)]
      [:div (:name (:company job))]
      [:.job-place (utils/format-job-location (:location job))]
      (if (:remote job)
        [:span
         [:span.icon
          [:i.fas.fa-home]]
         "Remote"])
      [:.salary (utils/format-salary (:remuneration job))]]
     [:.tags
      (for [tag (:tags job)]
        (rum/with-key (job-tag tag) tag))]
     [:.tagline (:tagline job)]
     [:.action-buttons
      [:button.button.is-small.is-danger.is-outlined {} "More info"]
      [:button.button.is-small.apply {} "1-click apply"]]
     ]]])

(rum/defc jobs-component < rum/reactive []
  [:div.columns.is-multiline
   (for [job (rum/react jobs)]
     (rum/with-key (job-component job) (:objectID job))
     )])

(rum/defc page []
  [:div.page
   (search-component)
   (jobs-component)
   ])

(defn mount [el]
  (rum/mount (page) el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
