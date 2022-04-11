(ns netlify-demo.base
  (:require
   [promesa.core :as p]
   [netlify-demo.utils :refer [readdir]]
   ["path" :as path]))

(defn loader
  []
  (p/let [files (readdir "./src/netlify_demo/pages")]
    {:files files}))

(defn meta
  [data]
  (p/resolved {:title "ClojureScript nbb netlify demo"}))

(defn view
  [{:keys [meta data]} & children]
  [:html
   [:head
    [:title (:title meta)]
    [:link
     {:rel "stylesheet"
      :href "/css/stylesheet.css"}]]
   [:body
    (into
     [:main.max-w-4xl.m-auto.py-16]
     children)]])
