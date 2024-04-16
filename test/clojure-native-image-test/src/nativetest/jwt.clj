(ns nativetest.jwt
  (:require [lambda.jwt :as jwt]
            [lambda.util :as util]
            [clojure.data :refer [diff]]
            [clojure.pprint :refer [pprint]]))

(def token "eyJraWQiOiI1SFE5NTJxNU1yZEc2TXRTXC96NzhJb085Z0V6ZTlhb2dBRExDYWM3TFB4MD0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiLWMyNnhzSlkxME1HNGRab2h4U3RDZyIsInN1YiI6ImVkMDE1MjI3LWE5MTUtNGE2NC04OTc5LTE0ZDQ0ZGMyMGQzZSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LWNlbnRyYWwtMV8wYzJ1M0d0bWdfUGluZ0ZlZGVyYXRlIiwibGltZS1saW1pdC1tYW5hZ2VycyIsImxpbWUtYWNjb3VudC1tYW5hZ2VycyIsImxpbWUtcmlzay1tYW5hZ2VycyIsImxpbWUtc2VtLWFwcHJvdmVycyIsImxpbWUtc2VtLWV4cGVydHMiXSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLmV1LWNlbnRyYWwtMS5hbWF6b25hd3MuY29tXC9ldS1jZW50cmFsLTFfMGMydTNHdG1nIiwiY29nbml0bzp1c2VybmFtZSI6IlBpbmdGZWRlcmF0ZV9yb2JlcnQucG9mdWtAcmJpbnRlcm5hdGlvbmFsLmNvbSIsImdpdmVuX25hbWUiOiJSb2JlcnQiLCJub25jZSI6Ik9FT09BOXNxQzlsRVBRX2U3SXVCeEV6YXNNV3VtNlR0OXRwbUR5QldqeGxIaURIZFdqNFNiQ3lUTFh1WERzMlZXajVqanY4LUZYTXdOdnZUbF9YeUxrQ19PZjlSdHZwWXdSUTg5cGtNV2NqZ0Rkc3NrMDhjakZOVnhReVlGM3JUTml5SVV3eWQwZHhVdndCWHFmQ09EZ3hrNlZOb1h1WVVjckJIT3A0WGJRTSIsImF1ZCI6IjFnb2YwdWg1aDdwY3FhdDI5cjY1aHQxa21mIiwiaWRlbnRpdGllcyI6W3sidXNlcklkIjoicm9iZXJ0LnBvZnVrQHJiaW50ZXJuYXRpb25hbC5jb20iLCJwcm92aWRlck5hbWUiOiJQaW5nRmVkZXJhdGUiLCJwcm92aWRlclR5cGUiOiJPSURDIiwiaXNzdWVyIjpudWxsLCJwcmltYXJ5IjoidHJ1ZSIsImRhdGVDcmVhdGVkIjoiMTU4NzMyMzY1MTg0MSJ9XSwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE1ODg0MDAzNjUsImV4cCI6MTU4ODQwMzk2NSwiaWF0IjoxNTg4NDAwMzY1LCJmYW1pbHlfbmFtZSI6IlBPRlVLIiwiZW1haWwiOiJyb2JlcnQucG9mdWtAcmJpbnRlcm5hdGlvbmFsLmNvbSJ9.cES4CNpck_YlWQci-Lct7SuT2QEIBB9Yst1kAlsp7kzbskWmvASMt5fE2PgGIY5rmiAgwxa5eUuydoQ3b_gqr_hm7B5HvPKVTqQamjiZkVEXf6wTwDTp_sKuv-U3ZfgskTdX42g67HCoP5ZkMlbFSpWykkTi4MgqfN6kFEmAvNlCisHz8IFHPs_31GIBwRsj08_QFpAhmacoXfZDmF1Ebhs91DnLH0_BqG-VpLdUph01RH0_tZDUx6-c0BWyrATUSvmzgiqMiWzr2uo15KAXDPmr8XzxOX9Pb7CfY-PgWsHr6KXqSordZnoKNByCQX-L4f4ckSQ_7hdUJ2rAdk4mBg")
(def jwks-key {:keys
               [{:alg "RS256",
                 :e   "AQAB",
                 :kid
                 "5HQ952q5MrdG6MtS/z78IoO9gEze9aogADLCac7LPx0=",
                 :kty "RSA",
                 :n
                 "rEg1tpleHjpL-gQkPVqYfF7XtewNhvItiw7T3LQ8hXIQZ9ZbrqXJL0XRZdKooNAFesYmxnT2A518Fckx0NsbV0aBe3KfO0qk64e8v3Zu73MKinmNrQ6SwQQa-6-Ak1I1vPgR79G8f7TTjQU3RfS9kwDPP7k23EEZ6HQIajpzob1ai4LcBY7AFZWskKQ6gA-mr8DXg5qqV8QvnTK3RfbOobyyJM30gsH1Ann7h-ofVbgd2JNs-zb0PfOTnO2iN0c2X0cXiDtOisxU6VmQ2K_xnTlQIBjPnw0Z6rC93i8R0peMoyxPNM42bTfo7Kq1zDkuh9nF0CbeTwrRWcRzVcb0Mw",
                 :use "sig"}
                {:alg "RS256",
                 :e   "AQAB",
                 :kid
                 "RJN3NN5P7hjuGIVys3YWWIJ7eR/7tv+Gak9dwvafgaA=",
                 :kty "RSA",
                 :n
                 "gGRepJ-OXQVGczoKcddr7aUk2DdF44MKgnq15Lts4pEG2jMywh7GOkkgRjivT5rBDX8WV0d8oz3-zxgeZ15NY8uA42njaTxXz_veaXt3BsBJUiN3oxWcXtkSKsFuMzQGoneLDajsE8a092NBFJ1ku3MSohL0YhoCuJ2_fPGTRvsQVnl-GTdsqoFlGlYJt33DvPQ-NmI04pxAEEQs_t_i_MDklG29WWC1mIV4aFMFmvxrN7BwWxd1-TyJk0849msvievwkmpp-0VVjhVuAVComCzuTm2sm26ozYOKTljX2qTt_kQaGsyXrVQqKM6Zh0YquWzJJ1P7zSiqGDfze85MNw",
                 :use "sig"}]})

(def region "eu-central-1")
(def user-pool-client-id "1gof0uh5h7pcqat29r65ht1kmf")
(def user-pool-id "eu-central-1_0c2u3Gtmg")

(defn ctx
  [jwks-all]
  {:jwks-all (:keys jwks-all)
   :env      {:region region}
   :auth     {:user-pool-id user-pool-id
              :client-id    user-pool-client-id}})

; FIXME: temporarily commented-out so we can proceed with https://clm.rcrs.me/jira/browse/LMSO-1400
; This same test works here:
; https://glms-gerrit.ewp-build.internal.rbigroup.cloud/plugins/gitiles/glms/lambda-apigw-lib/+/refs/heads/master/test/lambda/jwt_test.clj#78
(defn test-jwt
  []
      (println "test-jwt ignored temporarily because of LMSO-1400")
  ;(with-redefs [util/get-current-time-ms (fn [] 1587403965)]
  ;  (let [resp (:user (jwt/parse-token (ctx jwks-key) token))
  ;        is-valid (= {:id    "robert.pofuk@rbinternational.com"
  ;                     :email "robert.pofuk@rbinternational.com"
  ;                     :roles [:anonymous
  ;                             :eu-central-1_0c2u3Gtmg_PingFederate
  ;                             :lime-limit-managers
  ;                             :lime-account-managers
  ;                             :lime-risk-managers
  ;                             :lime-sem-approvers
  ;                             :lime-sem-experts]}
  ;                    resp)]
  ;
  ;    (pprint (diff resp {:id    "robert.pofuk@rbinternational.com"
  ;                        :email "robert.pofuk@rbinternational.com"
  ;                        :roles [:anonymous
  ;                                :eu-central-1_0c2u3Gtmg_PingFederate
  ;                                :lime-limit-managers
  ;                                :lime-account-managers
  ;                                :lime-risk-managers
  ;                                :lime-sem-approvers
  ;                                :lime-sem-experts]} ))
  ;    (println "JWT RESP:" is-valid)
  ;    (if-not is-valid
  ;      (throw (new RuntimeException "JHT failed")))))
      )
