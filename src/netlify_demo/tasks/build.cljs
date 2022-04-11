(ns netlify-demo.tasks.build
  (:require
   [nbb.core :as nbb]
   [clojure.string :as s]
   [promesa.core :as p]
   [reagent.dom.server :as rserver]
   [netlify-demo.base :as base]
   [netlify-demo.utils :refer [readdir]]
   ["fs/promises" :as fs]
   ["path" :as path]))

(def build-dir (.join path (js/process.cwd) "dist"))
(def pages-dir (.join path (js/process.cwd) "src/netlify_demo/pages"))

(defn write-page
  [filepath hiccup]
  (let [html (str "<!doctype html>" (rserver/render-to-static-markup hiccup))
        filepath (s/replace filepath #"\.cljs$" ".html")
        dirname (.dirname path filepath)]
    (println
     "Writing"
     (.join path "./dist" (.relative path build-dir filepath)))
    (p/do!
      (.mkdir fs dirname #js {:recursive true})
      (.writeFile fs filepath html #js {:encoding "utf-8"}))))

(defn load-pages
  [dir]
  (p/->> (readdir dir)
         (filter #(s/ends-with? % ".cljs"))))

(defn -main
  [& args]
  (p/let [pages (load-pages pages-dir)
          base-data (base/loader)
          base-meta (base/meta base-data)]
    (doseq [page pages]
      (p/let [module (nbb/load-file page)]
        (let [ns (namespace (symbol module))
              meta-fn (resolve (symbol ns 'meta))
              view-fn (resolve (symbol ns 'view))
              loader-fn (resolve (symbol ns 'loader))
              relative (.relative path pages-dir page)]
          (p/let [view-data (loader-fn base-data)
                  data (merge {} base-data view-data)
                  view-meta (meta-fn {:meta base-meta
                                      :data data})
                  meta (merge {} base-meta view-meta)
                  request {:meta meta :data data}]
            (write-page (.join path build-dir relative) (base/view request (view-fn request)))))))))
