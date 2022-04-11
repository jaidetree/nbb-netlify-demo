(ns netlify-demo.pages.test-dir.about)

(defn meta
  []
  {:title "" ;; page title
   :head []  ;; extra head tags
   })

(defn loader
  []
  {})

(defn view
  []
  [:div
   [:h1
    "About"]])
