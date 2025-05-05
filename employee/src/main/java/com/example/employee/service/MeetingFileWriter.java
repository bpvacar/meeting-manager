package com.example.employee.service;

import com.example.central.model.Meeting;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class MeetingFileWriter {

    private final String outputFile = Paths.get("meetings.log").toAbsolutePath().toString();

    public void write(Meeting meeting) {
        try (FileWriter fw = new FileWriter(outputFile, true)) {
            fw.write(meeting.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
