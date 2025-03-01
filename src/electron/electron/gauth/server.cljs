(ns electron.gauth.server
  (:require ["node-static" :as node-static]
            ["http" :as http]
            ["path" :as node-path]))

(defn start-static-server []
  (let [public-dir (node-path/join js/__dirname "public")
        file-server (node-static/Server. public-dir)]
    (-> (http/createServer
          (fn [request response]
            (.addListener request "end"
                          (fn []
                            (.serve file-server request response)))
            (.resume request)))
        (.listen 9990 "127.0.0.1"))))

(defn main []
    (start-static-server))