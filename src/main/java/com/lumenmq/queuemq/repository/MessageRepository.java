package com.lumenmq.queuemq.repository;

import com.lumenmq.queuemq.model.DbMessage;
import com.lumenmq.queuemq.model.SystemMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.sound.midi.SysexMessage;

public interface MessageRepository extends JpaRepository<DbMessage, Integer> {
}
