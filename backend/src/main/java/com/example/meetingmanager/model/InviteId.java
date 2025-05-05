package com.example.meetingmanager.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InviteId implements Serializable {
    private Long meetingId;
    private Long employeeId;
    // equals & hashCode
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InviteId)) return false;
        InviteId inviteId = (InviteId) o;
        return Objects.equals(meetingId, inviteId.meetingId) && Objects.equals(employeeId, inviteId.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, employeeId);
    }
}