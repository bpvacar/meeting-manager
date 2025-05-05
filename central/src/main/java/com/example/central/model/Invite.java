package com.example.central.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Invite {
    @EmbeddedId
    private InviteId id;

    @MapsId("meetingId")
    @ManyToOne
    @JsonBackReference
    private Meeting meeting;

    @MapsId("employeeId")
    @ManyToOne
    private Employee employee;

    private String status;

    // ——— Getters y setters ———
    public InviteId getId() { return id; }
    public void setId(InviteId id) { this.id = id; }

    public Meeting getMeeting() { return meeting; }
    public void setMeeting(Meeting meeting) { this.meeting = meeting; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
