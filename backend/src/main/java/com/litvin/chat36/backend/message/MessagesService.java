package com.litvin.chat36.backend.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    Logger logger = LoggerFactory.getLogger(MessagesService.class.getName());

    private final List<ChatMessage> messages = new ArrayList<>();

    public void postMessage(String text, String authorNickname) {
        ChatMessage chatMessage = new ChatMessage(authorNickname, Instant.now(), text);
        messages.add(chatMessage);
        logger.info(chatMessage.toString());
    }

    public List<ChatMessage> getMessageHistory() {
        return new ArrayList<>(messages);
    }
}
