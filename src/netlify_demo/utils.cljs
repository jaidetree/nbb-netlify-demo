(ns netlify-demo.utils
  (:require
   [promesa.core :as p]
   ["fs/promises" :as fs]
   ["path" :as path]))

(defn readdir
  [dirpath]
  (p/->> (fs/readdir dirpath #js {:withFileTypes true})
         (map (fn [dirent]
                (let [filepath (.join path dirpath (.-name dirent))]
                  (cond (.isDirectory dirent)
                        (readdir filepath)

                        (.isFile dirent)
                        [filepath]

                        :else
                        []))))
         (p/all)
         (reduce #(into %1 %2) [])))
