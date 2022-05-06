package com.example.abe.dcpabe.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "SecretKey")
@Table(name = "secret_key")
public class SecretKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "secret_key_sequence",
            sequenceName = "secret_key_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "secret_key_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

//    @Column(
//            name = "ai",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
    private byte[] ai;

//    @Column(
//            name = "yi",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
    private byte[] yi;

    @JsonCreator
    public SecretKey(
            @JsonProperty("ai") byte[] ai,
            @JsonProperty("yi") byte[] yi) {
        this.ai = ai;
        this.yi = yi;
    }

    public long getId() {
        return id;
    }

    public byte[] getAi() {
        return ai;
    }

    public byte[] getYi() {
        return yi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecretKey secretKey = (SecretKey) o;
        return Arrays.equals(getAi(), secretKey.getAi()) &&
                Arrays.equals(getYi(), secretKey.getYi());
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(getAi());
        result = 31 * result + Arrays.hashCode(getYi());
        return result;
    }
}
