(ns electron.gauth.server
  (:require ["node-static" :as node-static]
            ["http" :as http]
            ["path" :as node-path]
            ["process" :as process]))

(defn start-static-server []
  (let [public-dir (node-path/join js/__dirname "public")
        file-server (node-static/Server. public-dir)]
    (-> (http/createServer
          (fn [request response]
            (.addListener request "end"
                          (fn []
                           (let [url (.-url request)
                                 params (js/URLSearchParams. (subs url (inc (.indexOf url "?"))))]
                             (js/console.log "Code param:" (.get params "code"))
                              ;; Send message to main process
                             (when (.-send process)
                               (.send process (str "Code param: " (.get params "code")))))
                            (.serve file-server request response)))
            (.resume request)))
        (.listen 9990 "127.0.0.1"))))

(defn main []
    (start-static-server))