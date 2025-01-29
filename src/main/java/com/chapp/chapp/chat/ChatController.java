package com.chapp.chapp.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller

public class ChatController {

    // add user when new user join the chat

    // send chat message will
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public") // in the config file topic
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor accessor) {
        // add user name in web socket session
        accessor.getSessionAttributes().put("username", chatMessage.getSender());// to get who send the message
        return chatMessage;
    }

    // send chat message will
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public") // in the config file topic
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
