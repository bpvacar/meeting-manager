package com.example.meetingmanager.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.Instant;
import java.util.Set;

@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String location;
    private Instant startTime;
    private Instant endTime;

    @ManyToOne
    private Employee creator;

    @JsonManagedReference
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private Set<Invite> invites;

    // ——— Getters y setters ———
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Instant getStartTime() { return startTime; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }

    public Instant getEndTime() { return endTime; }
    public void setEndTime(Instant endTime) { this.endTime = endTime; }

    public Employee getCreator() { return creator; }
    public void setCreator(Employee creator) { this.creator = creator; }

    public Set<Invite> getInvites() { return invites; }
    public void setInvites(Set<Invite> invites) { this.invites = invites; }
}
