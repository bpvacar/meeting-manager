package com.example.meetingmanager.repository;

import com.example.meetingmanager.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {}