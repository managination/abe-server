package com.example.abe.channel;

import com.example.abe.dcpabe.other.Ciphertext;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity(name = "Channel")
@Table(name = "channel")
public class Channel {

    @Id
    @SequenceGenerator(
            name = "channel_sequence",
            sequenceName = "channel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "channel_sequence"
    )
    private Long id;

    private String topic;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<Ciphertext> body;

    public Channel() {
    }

    public Channel(String topic,
                   List<Ciphertext> body) {
        this.topic = topic;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public List<Ciphertext> getBody() {
        return body;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setBody(List<Ciphertext> body) {
        this.body = body;
    }

    public void addCiphertext(Ciphertext ciphertext) {
        this.body.add(ciphertext);
    }

}
