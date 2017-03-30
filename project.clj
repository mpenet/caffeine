(defproject cc.qbits/caffeine "0.1.0-SNAPSHOT"
  :description "Wrapper of ben-manes/caffeine"
  :url "https://github.com/mpenet/caffeine"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha15"]
                 [cc.qbits/commons "0.4.6"]
                 [com.github.ben-manes.caffeine/caffeine "2.4.0"]]
  :codox {:src-dir-uri "https://github.com/mpenet/caffeine/blob/master/"
          :src-linenum-anchor-prefix "L"
          :defaults {:doc/format :markdown}
          :output-dir "doc/codox"}

  :source-paths ["src/clj"]
  ;; :java-source-paths ["src/java"]
  ;; :javac-options ["-source" "1.6" "-target" "1.6" "-g"]
  :global-vars {*warn-on-reflection* true})
