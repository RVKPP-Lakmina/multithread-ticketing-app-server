package com.example.ticketManager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @SuppressWarnings("null")
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Broker to send messages to the client
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for client to send messages
    }

    @SuppressWarnings("null")
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ticketing-websocket") // WebSocket endpoint
                .setAllowedOrigins("*") // Allow cross-origin requests
                .withSockJS(); // Fallback for browsers without WebSocket support
    }

}
