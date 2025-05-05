package com.example.employee.service;

import com.example.central.model.Meeting;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompSession;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class MeetingFileWriter {
    private final Path file = Path.of("/data/", System.getenv("EMPLOYEE_NAME") + ".json");

    public void write(Meeting m) {
        try (FileWriter fw = new FileWriter(file.toFile(), true)) {
            fw.write(m.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class EmployeeStompHandler extends StompSessionHandlerAdapter {
    private final MeetingFileWriter writer;

    public EmployeeStompHandler(MeetingFileWriter writer) {
        this.writer = writer;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/meetings", this);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Meeting m = (Meeting) payload;
        writer.write(m);
    }
}