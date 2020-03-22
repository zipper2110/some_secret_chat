package com.litvin.chat36.backend.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessagesServiceTest {

    @Autowired
    private MessagesService messagesService;

    @Test
    void canPostMessage() {
        messagesService.postMessage("test", "pewpew");
        List<ChatMessage> messageHistory = messagesService.getMessageHistory();
        assertEquals(1, messageHistory.size(), "message history should have 1 message");
        ChatMessage message = messageHistory.get(0);

        assertEquals("test", message.getText());
        assertEquals("pewpew", message.getAuthorNickname());
    }
}