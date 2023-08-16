package com.opensw.safeguard.domain.firebase;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmMessage {

    private boolean validate_only;
    private Message message;

    @Builder
    public FcmMessage(boolean validate_only, Message message) {
        this.validate_only = validate_only;
        this.message = message;
    }

    @Getter
    @NoArgsConstructor
    public static class Message {
        private Notification notification;
        private String token;

        @Builder

        public Message(Notification notification, String token) {
            this.notification = notification;
            this.token = token;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Notification {
        private String title;
        private String body;
        private String image;

        @Builder
        public Notification(String title, String body, String image) {
            this.title = title;
            this.body = body;
            this.image = image;
        }
    }

}
