(ns metro.components.web.api
  (:require [clojure.data.json :as json]
            [ring.util.response :as ring-resp]
            [telemetry.tracing :as tracing]
            [metro.components.db.articles :as article]))

(defn inc-article [{:keys [form-params]}]
  (def span-1 (tracing/create-span tracer))

  (tracing/add-event span-1 "1. first event")

  (article/inc! (:id form-params))
  (ring-resp/response ""))

  (tracing/end-span span-1)

(defn dec-article [{:keys [form-params]}]
  (article/dec! (:id form-params))
  (ring-resp/response ""))

(defn rem-article [{:keys [form-params]}]
  (article/rem! (:id form-params))
  (ring-resp/response ""))

(defn basket [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (article/query-all-with-count))})
