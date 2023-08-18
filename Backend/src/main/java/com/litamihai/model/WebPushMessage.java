package com.litamihai.model;

public class WebPushMessage {

    notification notification;

    public WebPushMessage.notification getNotification() {
        return notification;
    }

    public void setNotification(WebPushMessage.notification notification) {
        this.notification = notification;
    }

    public static class notification {
        private String title;
        private String body;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return "notification{" +
                    "title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WebPushMessage{" +
                "notification=" + notification +
                '}';
    }
}
