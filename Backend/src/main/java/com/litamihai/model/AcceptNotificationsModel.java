package com.litamihai.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "AcceptNotifications")
public class AcceptNotificationsModel implements Serializable {
    private String endpoint;
    private String expirationTime;
    Keys keys;

    public static class Keys {
        private String p256dh;
        private String auth;

        public Keys(String p256dh, String auth) {
            this.p256dh = p256dh;
            this.auth = auth;
        }

        public String getP256dh() {
            return p256dh;
        }

        public void setP256dh(String p256dh) {
            this.p256dh = p256dh;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Keys getKeys() {
        return keys;
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public AcceptNotificationsModel(String endpoint, String expirationTime, Keys keys) {
        this.endpoint = endpoint;
        this.expirationTime = expirationTime;
        this.keys = keys;
    }

    @Override
    public String toString() {
        return "AcceptNotificationsModel{" +
                "endpoint='" + endpoint + '\'' +
                ", expirationTime='" + expirationTime + '\'' +
                ", keys=" + keys +
                '}';
    }
}
