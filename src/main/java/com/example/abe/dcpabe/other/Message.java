package com.example.abe.dcpabe.other;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Arrays;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Message")
@Table(name = "message")
public class Message {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "message_sequence"
    )
    private Long id;

    private final byte[] m;

    @JsonCreator
    public Message(
            @JsonProperty("m") byte[] m) {
        this.m = m;
    }

    public Long getId() {
        return id;
    }

    public byte[] getM() {
        return m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Arrays.equals(getM(), message.getM());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getM());
    }
}