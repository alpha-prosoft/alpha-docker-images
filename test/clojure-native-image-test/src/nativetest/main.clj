(ns nativetest.main
  (:gen-class)
  (:require [clojure.data :as data]
            [malli.core :as m]
            [malli.error :as me]
            [nativetest.jwt :as jwt]
            [edd.db :as edd-db]
            [iso-7064.core :as iso]
            [next.jdbc.connection :as jdbc-conn]
            [next.jdbc :as jdbc]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log])
  (:import (org.slf4j LoggerFactory)
           (com.zaxxer.hikari HikariDataSource HikariConfig)
           (org.postgresql.util PSQLException)
           (java.sql SQLTransientConnectionException)))

(def schema-data {:id "1" :age "10"})

(def schema-test
  (m/schema
    [:map
     [:id string?]
     [:age [:fn (fn [v] (int? (Integer/parseInt v)))]]]))

(defn pre-check
  [data]
  (println "Try to parse" (Integer/parseInt "10"))
  (m/validate schema-test data))

(defn diff []
  (println
    (data/diff {:a #uuid "a485727e-ec3a-4eb3-8df3-ed2af33fff13"
                :d :e
                :f :g}
               {:d :e})))

(defn schema-fn
  [data]
  {:pre [(pre-check data)]}
  (println "Schema check ok"))



(defn check-iso-7064 []
  (println (iso/calc-check-character iso/mod-97-10 "1")))

(defn file-load-from-classpath
  []
  (with-open [in (io/input-stream (io/resource "conf/res/sample1.txt"))]
    (if (not (= (slurp in)
                "1"))
      (throw (ex-info "conf/res/sample1.txt failed" {:in in}))
      (println "conf/res/sample1.txt success")))

  (with-open [in (io/input-stream (io/resource "conf/sample2.txt"))]
    (if (not (= (slurp in)
                "2"))
      (throw (ex-info "conf/sample2.txt failed" {:in in}))
      (println "conf/sample2.txt success"))))

#_(gen-class
    :name com.zaxxer.hikari.metrics.MetricsTrackerFactory
    :state state
    :init init
    :prefix "-"
    :main false
    ;; declare only new methods, not superclass methods
    :methods [[setLocation [String] void]
              [getLocation [] String]])

#_(gen-class
    :name com.codahale.metrics.health.HealthCheckRegistry
    :state state
    :init init
    :prefix "-"
    :main false
    ;; declare only new methods, not superclass methods
    :methods [[setLocation [String] void]
              [getLocation [] String]])

(defn -main [& args]
  (diff)
  (println "Checking schema")
  (jwt/test-jwt)
  (println "Calling validation" (m/validate schema-test schema-data))
  (println "Explain" (m/explain schema-test schema-data))
  (println "(int? (Integer/parseInt v))" (int? (Integer/parseInt "10")))
  (println "Explain guman" (me/humanize
                             (m/explain schema-test schema-data)))
  (let [log (LoggerFactory/getLogger "test")]
    (.info log "Testing log"))
  #_(try
    (let [ds (edd-db/init {})]
      (with-open [^HikariDataSource ds (jdbc-conn/->pool HikariDataSource ds)]
        ;; this code initializes the pool and performs a validation check:
        (.close (jdbc/get-connection ds))))
    (catch SQLTransientConnectionException e
      (log/info "Error" e)))
  (check-iso-7064)
  (schema-fn schema-data)
  (file-load-from-classpath))
