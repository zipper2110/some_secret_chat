package com.litvin.chat36.backend.message;

import java.time.Instant;
import java.util.Objects;

public class ChatMessage {

    private final String authorNickname;
    private final Instant postedTime;
    private final String text;

    public ChatMessage(String authorNickname, Instant postedTime, String text) {
        this.authorNickname = authorNickname;
        this.postedTime = postedTime;
        this.text = text;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public Instant getPostedTime() {
        return postedTime;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return authorNickname.equals(that.authorNickname) &&
              postedTime.equals(that.postedTime) &&
              text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorNickname, postedTime, text);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
              "authorNickname='" + authorNickname + '\'' +
              ", postedTime=" + postedTime +
              ", text='" + text + '\'' +
              '}';
    }
}
