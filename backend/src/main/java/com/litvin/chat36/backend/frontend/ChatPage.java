package com.litvin.chat36.backend.frontend;

import com.litvin.chat36.backend.message.ChatMessage;
import com.litvin.chat36.backend.message.MessagesService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class ChatPage extends VerticalLayout {

    private MessagesService messagesService;
    private TextField nicknameField = new TextField("nick");

    ChatPage(MessagesService messagesService) {
        this.messagesService = messagesService;
        setWidth("800px");

        NewMessageLayout newMessageLayout = new NewMessageLayout(messagesService);
        add(
              nicknameField,
              new MessagesHistory(messagesService.getMessageHistory()),
              newMessageLayout
        );

    }

    public static class MessageCard extends VerticalLayout {

        public MessageCard(ChatMessage message) {
            Paragraph author = new Paragraph(message.getAuthorNickname());
            author.getStyle().set("font-size", "10px").set("color", "#CCC");

            Paragraph text = new Paragraph(message.getText());

            Paragraph time = new Paragraph(message.getPostedTime().toString());
            time.getStyle().set("font-size", "10px").set("color", "#CCC");

            add(author, text, time);

            getStyle().set("background", "#f5f5ff").set("border-radius", "8px");
        }
    }

    public static class MessagesHistory extends VerticalLayout {

        public MessagesHistory(List<ChatMessage> history) {
            for (ChatMessage chatMessage : history) {
                add(new MessageCard(chatMessage));
            }
        }
    }

    public static class NewMessageLayout extends HorizontalLayout {

        private TextField messageInput = new TextField();
        private Button sendButton = new Button(new Icon(VaadinIcon.PAPERPLANE));

        public NewMessageLayout(MessagesService messagesService) {
            setWidthFull();

            add(
                messageInput,
                sendButton
            );

            messageInput.setWidthFull();
            setFlexGrow(2, messageInput);

            sendButton.addClickListener(event -> {
                messagesService.postMessage(messageInput.getValue(), "me");
                messageInput.clear();
                Notification.show("message was sent!");
            });
            sendButton.addClickShortcut(Key.ENTER);
        }
    }
}
