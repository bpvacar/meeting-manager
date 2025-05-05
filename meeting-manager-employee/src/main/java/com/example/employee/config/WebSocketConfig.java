package com.example.employee.config;

import com.example.employee.service.MeetingFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.List;

@Configuration
public class WebSocketClientConfig {
    @Bean
    public WebSocketStompClient stompClient(MeetingFileWriter writer,
                                            StompSessionHandler handler) {
        List<Transport> transports =
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketStompClient client = new WebSocketStompClient(new SockJsClient(transports));
        client.connect("ws://central:8080/ws", handler);
        return client;
    }

    @Bean
    public StompSessionHandler stompHandler(MeetingFileWriter writer) {
        return new EmployeeStompHandler(writer);
    }
}