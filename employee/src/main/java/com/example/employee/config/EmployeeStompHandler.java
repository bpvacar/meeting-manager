package com.example.employee.config;

import com.example.central.model.Meeting;
import com.example.employee.service.MeetingFileWriter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import java.lang.reflect.Type;

public class EmployeeStompHandler extends StompSessionHandlerAdapter {

    private final MeetingFileWriter writer;

    public EmployeeStompHandler(MeetingFileWriter writer) {
        this.writer = writer;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        // Suscríbete al topic /topic/meetings
        session.subscribe("/topic/meetings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Meeting.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Meeting meeting = (Meeting) payload;
                writer.write(meeting);
            }
        });
    }

    @Override
    public void handleException(
            StompSession session,
            StompCommand command,
            StompHeaders headers,
            byte[] payload,
            Throwable exception) {
        exception.printStackTrace();
    }
}
