package com.litvin.chat36.backend;

import org.greenrobot.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfiguration {

    @Bean
    public static EventBus getEventBus() {
        return EventBus.getDefault();
    }
}
