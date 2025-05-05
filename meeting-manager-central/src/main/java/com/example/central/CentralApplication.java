package com.example.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CentralApplication implements WebSocketMessageBrokerConfigurer {
    @Value("#{${employees}}")
    private Map<String, Integer> employeePorts;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // configuración STOMP
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-central").setAllowedOriginPatterns("*").withSockJS();
    }

    public static void main(String[] args) {
        SpringApplication.run(CentralApplication.class, args);
    }
}