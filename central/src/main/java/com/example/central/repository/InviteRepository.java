package com.example.central.repository;

import com.example.central.model.Invite;
import com.example.central.model.InviteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, InviteId> {}