package com.lumenmq.queuemq.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Message")
public class DbMessage {
    @Id
    @GeneratedValue
    private int id;
    private String source;
    private String message;

    public DbMessage(String message) {
        this.message = message;
    }

}
