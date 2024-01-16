(ns build
  (:require [clojure.tools.build.api :as b]
            [clojure.pprint :as pprint]
            [clojure.java.io :as io]))

(println "Bulding!")

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [{:keys [group-id
                   artifact-id
                   ^String version
                   out]
            :as params}]
  (println "Bulding jar:")
  (pprint/pprint params)
  (println (format "Building uberjar: %s/%s:%s into %s"
                   group-id artifact-id version out))
  (let [basis (b/create-basis {:project "deps.edn"
                               :user :standard})
        lib (symbol (format "%s/%s" group-id artifact-id))
        jar-file (format "%s/%s-%s.jar"
                         out
                         (name artifact-id)
                         version)
        class-dir (format "%s/classes" out)
        src-dirs (-> (slurp "deps.edn")
                     read-string
                     :paths)]
    (println (format "Compiling: %s" src-dirs))
    (b/write-pom {:lib lib
                  :version version
                  :target "./"
                  :basis basis
                  :src-dirs src-dirs})
    (b/copy-dir {:src-dirs src-dirs
                 :target-dir class-dir})
    (b/jar {:class-dir class-dir
            :jar-file jar-file})))

(defn uber [{:keys [artifact-id
                    ^String version
                    main
                    out
                    lms]
             :or {lms false}
             :as params}]
  (clean nil)
  (println "Building uberjar")
  (jar params)
  (println "Uberjar params")
  (pprint/pprint params)
  (let [basis (b/create-basis {:project "deps.edn"
                               :user :standard})

        uber-file (format "%s/%s-%s-standalone.jar"
                          out
                          (name artifact-id)
                          version)
        class-dir (format "%s/classes" out)
        src-dirs (-> (slurp "deps.edn")
                     read-string
                     :paths)]

    (println (format "Compiling: %s" src-dirs))
    (b/copy-dir {:src-dirs src-dirs
                 :target-dir class-dir})
    (b/compile-clj {:basis basis
                    :src-dirs src-dirs
                    :class-dir class-dir})

    (when lms
      (let [logging-ns "logging"
            logging-base  "src"
            logging-file (format "%s/%s.clj" logging-base logging-ns)]
        (println "Add LMS specific logging to be AOT compiled")
        (io/make-parents logging-file)
        (spit logging-file
              "(ns logging (:require [lambda.logging :refer :all]))")
        (b/compile-clj {:basis basis
                        :src-dirs [logging-base]
                        :class-dir class-dir})
        (b/delete {:path logging-file})))
    (b/uber {:class-dir class-dir
             :uber-file uber-file
             :basis basis
             :main (symbol main)})))
