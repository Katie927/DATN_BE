package com.DATN.Bej.config; // (Hoặc package của bạn)

import org.springframework.beans.factory.annotation.Value; // <-- 1. THÊM IMPORT NÀY
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// ... (các import khác)
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUser;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
            .setAllowedOrigins("*")
            .withSockJS();
    }

@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(rabbitHost) 
                .setRelayPort(61613)
                .setClientLogin(rabbitUser)
                .setClientPasscode(rabbitPassword);

        registry.setUserDestinationPrefix("/user");
    }
}