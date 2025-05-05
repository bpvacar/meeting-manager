package com.example.meetingmanager.controller;

import com.example.meetingmanager.model.Meeting;
import com.example.meetingmanager.service.MeetingMediator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
// Permite peticiones CORS desde tu React en localhost:3000
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {

    private final MeetingMediator mediator;

    public MeetingController(MeetingMediator mediator) {
        this.mediator = mediator;
    }

    /** Devuelve todas las reuniones */
    @GetMapping
    public List<Meeting> all() {
        return mediator.findAll();
    }

    /** Crea una nueva reunión */
    @PostMapping
    public Meeting create(@RequestBody Meeting meeting) {
        return mediator.create(meeting);
    }

    /** Actualiza la reunión con id dado */
    @PutMapping("/{id}")
    public Meeting update(
            @PathVariable Long id,
            @RequestBody Meeting meeting
    ) {
        meeting.setId(id);
        return mediator.update(meeting);
    }

    /** Elimina la reunión con id dado */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mediator.delete(id);
    }
}
