package com.litvin.chat36.backend.message;

import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    private EventBus eventBus;

    public MessagesService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    Logger logger = LoggerFactory.getLogger(MessagesService.class.getName());

    private final List<ChatMessage> messages = new ArrayList<>();

    public void postMessage(String text, String authorNickname) {
        ChatMessage chatMessage = new ChatMessage(authorNickname, Instant.now(), text);
        messages.add(chatMessage);
        logger.info(chatMessage.toString());

        eventBus.post(new MessagePostedEvent(chatMessage));
    }

    public List<ChatMessage> getMessageHistory() {
        return new ArrayList<>(messages);
    }
}
