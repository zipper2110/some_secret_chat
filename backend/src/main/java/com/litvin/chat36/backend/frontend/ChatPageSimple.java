package com.litvin.chat36.backend.frontend;

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

import java.util.ArrayList;
import java.util.List;

@Route("simple")
public class ChatPageSimple extends VerticalLayout {

    private MessagesService messagesService;
    private String nick = "Me";

    ChatPageSimple(MessagesService messagesService) {
        this.messagesService = messagesService;
        draw();
    }

    private void draw() {
        removeAll();

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        VerticalLayout content = new VerticalLayout();
        content.setWidth("800px");
        content.setHeightFull();

        // 1st part - nickname input ------------------------
        TextField nicknameField = new TextField("nick");
        nicknameField.setValue(nick);

        // 2nd part - message history -----------------------
        List<VerticalLayout> messagesLayouts = new ArrayList<>();
        messagesService.getMessageHistory().forEach(message -> {
            Paragraph author = new Paragraph(message.getAuthorNickname());
            author.getStyle().set("font-size", "12px").set("color", "#AAA");
            Paragraph text = new Paragraph(message.getText());
            Paragraph time = new Paragraph(message.getPostedTime().toString());
            time.getStyle().set("font-size", "10px").set("color", "#CCC");

            VerticalLayout messageLayout = new VerticalLayout(author, text, time);
            messageLayout.getStyle().set("background", "#f5f5ff").set("border-radius", "8px");
            messagesLayouts.add(messageLayout);
        });
        VerticalLayout messagesHistoryLayout = new VerticalLayout();
        messagesLayouts.forEach(messagesHistoryLayout::add);

        // 3rd part - new message -----------------------
        TextField messageInput = new TextField();
        messageInput.setWidthFull();
        messageInput.focus();

        Button sendButton = new Button(new Icon(VaadinIcon.PAPERPLANE), event -> {
            nick = nicknameField.getValue();
            messagesService.postMessage(messageInput.getValue(), nick);
            messageInput.clear();
            Notification.show("message was sent!");
            draw();
        });
        sendButton.addClickShortcut(Key.ENTER);

        HorizontalLayout newMessageLayout = new HorizontalLayout();
        newMessageLayout.setWidthFull();
        newMessageLayout.add(messageInput, sendButton);
        newMessageLayout.setFlexGrow(2, messageInput);

        content.add(
              nicknameField,
              messagesHistoryLayout,
              newMessageLayout
        );
        add(content);
    }
}
