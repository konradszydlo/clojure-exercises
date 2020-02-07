(ns scrambls.core
  (:require [rum.core :as rum]
            [scrambls.ajax :as ajax]))

(enable-console-print!)

(defonce app-state (atom {:scramble nil}))

(def scramble (rum/cursor-in app-state [:scramble]))

(rum/defcs form <
           (rum/local "" ::str1)
           (rum/local "" ::str2)
           [state]
           (let [str1 (::str1 state)
                 str2 (::str2 state)
                 disabled? (or (empty? @str1) (empty? @str2))]
                [:.form
                 [:.filed
                  [:label.label "All characters"]
                  [:.control
                   [:input.input {:placeholder "Type first string"
                                  :on-change   #(reset! str1 (.. % -target -value))}]]]
                 [:.filed
                  [:label.label "Target string"]
                  [:.control
                   [:input.input {:placeholder "Type second string"
                                  :on-change   #(reset! str2 (.. % -target -value))}]]]
                 [:.field
                  [:.control
                   [:button.button.is-primary
                    {:disabled disabled?
                     :on-click #(ajax/perform-search @str1 @str2 app-state)} "Submit"]]]]))

(rum/defc notification [type message]
          [:.notification {:class type}
           message])

(rum/defc scramble-info < rum/reactive []
          (let [scramble (rum/react
                           scramble)]
               (if (:scramble-request-done scramble)
                 [:div
                  (if (:scramble-error scramble)
                    (notification "is-danger" "Please provide both strings.")
                    (if (:is-valid-scramble scramble)
                      (notification "is-success" "The words are scramble.")
                      (notification "is-warning" "The words are not scramble.")))])))

(rum/defc page []
          [:.section.columns
           [:.column.is-one-third
            [:h1 "Scramblies"]
            (form)
            (scramble-info)]])

(defn mount-components []
      (rum/mount (page) (.getElementById js/document "app")))

(mount-components)