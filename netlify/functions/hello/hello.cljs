(ns functions.hello
  (:require
   [promesa.core :as p]))

(defn handler
  [event ctx]
  (println "Update")
  (p/resolve (clj->js {:statusCode 200
                       :body "Hey I work"})))

;; Exports
#js {:handler handler}
