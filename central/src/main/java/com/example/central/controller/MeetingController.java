package com.example.central.controller;

import com.example.central.model.Meeting;
import com.example.central.repository.MeetingRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingRepository repo;
    private final SimpMessagingTemplate messagingTemplate;

    public MeetingController(MeetingRepository repo,
                             SimpMessagingTemplate messagingTemplate) {
        this.repo = repo;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<Meeting> all() {
        return repo.findAll();
    }

    @PostMapping
    public Meeting create(@RequestBody Meeting meeting) {
        Meeting saved = repo.save(meeting);
        // publica en /topic/meetings
        messagingTemplate.convertAndSend("/topic/meetings", saved);
        return saved;
    }

    // PUT & DELETE
}
