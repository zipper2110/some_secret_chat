package com.litvin.chat36.backend.message;

public class MessagePostedEvent {
    private final ChatMessage message;

    public MessagePostedEvent(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }
}
