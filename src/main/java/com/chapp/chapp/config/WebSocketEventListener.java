package com.chapp.chapp.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chapp.chapp.chat.ChatMessage;
import com.chapp.chapp.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTomplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userName = (String) accessor.getSessionAttributes().get("username");
        if (userName != null) {
            log.info("User disconnected: " + userName);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE).sender(userName)
                    .build();
            messageTomplate.convertAndSend("/topic/public", chatMessage);
        }
    };
}
