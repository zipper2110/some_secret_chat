package com.litvin.chat36.backend.frontend;

import com.litvin.chat36.backend.message.MessagePostedEvent;
import com.litvin.chat36.backend.message.MessagesService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

@Route("simple")
@Push
@PreserveOnRefresh
@PWA(name = "Chat Huyat!", shortName = "Chat36")
@Viewport(Viewport.DEFAULT)
public class ChatPageSimple extends VerticalLayout {

    private MessagesService messagesService;
    private EventBus eventBus;
    private String nick = "Me";

    ChatPageSimple(MessagesService messagesService, EventBus eventBus) {
        this.messagesService = messagesService;
        this.eventBus = eventBus;
        draw();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        eventBus.register(this);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        eventBus.unregister(this);
    }

    @Subscribe
    public void onMessagePosted(MessagePostedEvent event) {
        getUI().ifPresent(ui -> ui.access(() -> {
            Notification.show("Recieved new message!", 500, Notification.Position.TOP_CENTER);
            draw();
        }));
    }

    private void draw() {
        removeAll();

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        getStyle()
              .set("overflow", "hidden")
              .set("padding", "0");

        VerticalLayout content = new VerticalLayout();
        content.getStyle().set("max-width", "800px");
        content.setSizeFull();

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
        messagesHistoryLayout.getStyle().set("overflow-x", "scroll");
        messagesLayouts.forEach(messagesHistoryLayout::add);
        messagesHistoryLayout.setId("xx");

        // 3rd part - new message -----------------------
        TextField messageInput = new TextField();
        messageInput.setWidthFull();
        messageInput.focus();

        Button sendButton = new Button(new Icon(VaadinIcon.PAPERPLANE), event -> {
            nick = nicknameField.getValue();
            messagesService.postMessage(messageInput.getValue(), nick);
            messageInput.clear();
            Notification.show("message was sent!", 500, Notification.Position.TOP_CENTER);
        });
        sendButton.addClickShortcut(Key.ENTER, KeyModifier.CONTROL);

        HorizontalLayout newMessageLayout = new HorizontalLayout();
        newMessageLayout.setWidthFull();
        newMessageLayout.add(messageInput, sendButton);
        newMessageLayout.setFlexGrow(2, messageInput);

        content.add(
              nicknameField,
              messagesHistoryLayout,
              newMessageLayout
        );
        content.expand(messagesHistoryLayout);
        add(content);

        UI.getCurrent().getPage().executeJs("var x = document.getElementById(\"xx\");\n" +
                    "x.scrollTop = x.scrollHeight;");

        UI.getCurrent().getPage().executeJs(
              "setTimeout(() => {" +
                    "var x = document.getElementById(\"xx\");\n" +
                    "x.scrollTop = x.scrollHeight;" +
                    "}, 500);"
        );
    }
}
