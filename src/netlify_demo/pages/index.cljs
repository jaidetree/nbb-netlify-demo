(ns netlify-demo.pages.index)

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
    "Home"]])
