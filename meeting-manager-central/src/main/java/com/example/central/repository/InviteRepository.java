package com.example.meetingmanager.repository;

import com.example.meetingmanager.model.Invite;
import com.example.meetingmanager.model.InviteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, InviteId> {}