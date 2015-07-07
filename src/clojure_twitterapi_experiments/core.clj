(ns clojure-twitterapi-experiments.core
  (:use
   [twitter.oauth]
   [twitter.callbacks]
   [twitter.callbacks.handlers]
   [twitter.api.restful])
  (:import
   (twitter.callbacks.protocols SyncSingleCallback))
  (:gen-class))

(def my-creds (make-oauth-creds
               "consumer-key"
               "consumer-secret"
               "user-access-token"
               "user-access-token-secret"))

(defn print-search-results
  "This prints the handles of search results."
  [query]
  (let [statuses (get-in (search-tweets :oauth-creds my-creds :params {:q query :count 100}) [:body :statuses])]
    (loop [status-count (- (count statuses) 1)]
        (when (> status-count -1)
          (println (get-in (get statuses status-count) [:user :screen_name]))
          (recur (- status-count 1))))
    )
)

(defn -main
  "print handles of twitter folks who match the query"
  [& args]
  (print-search-results (first args))
  (System/exit 0)
 )
