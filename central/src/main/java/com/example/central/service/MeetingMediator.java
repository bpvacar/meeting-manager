package com.example.central.service;

import java.util.List;
import com.example.central.model.Meeting;
import com.example.central.repository.MeetingRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MeetingMediator {
    private final MeetingRepository repo;
    private final SimpMessagingTemplate messaging;

    public MeetingMediator(MeetingRepository repo, SimpMessagingTemplate messaging) {
        this.repo = repo;
        this.messaging = messaging;
    }

    public List<Meeting> findAll() {
        return repo.findAll();
    }

    public Meeting create(Meeting meeting) {
        Meeting saved = repo.save(meeting);
        messaging.convertAndSend("/topic/meetings", saved);
        return saved;
    }
    // update, delete similarly
    
    public Meeting update(Meeting meeting) {
        Meeting updated = repo.save(meeting);
        messaging.convertAndSend("/topic/meetings", updated);
        return updated;
    }

    public void delete(Long id) {
        repo.deleteById(id);
        messaging.convertAndSend("/topic/meetings", id);
    }
}