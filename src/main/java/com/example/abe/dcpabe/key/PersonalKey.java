package com.example.abe.dcpabe.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "PersonalKey")
@Table(name = "personal_key")
public class PersonalKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "personal_key_sequence",
            sequenceName = "personal_key_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "personal_key_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "attribute",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private final String attribute;

    @Column(
            name = "key",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private final byte[] key;

    @JsonCreator
    public PersonalKey(
            @JsonProperty("attribute") String attribute,
            @JsonProperty("key") byte[] key) {
        this.attribute = attribute;
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public String getAttribute() {
        return attribute;
    }

    public byte[] getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalKey that = (PersonalKey) o;
        return Objects.equals(getAttribute(), that.getAttribute()) &&
                Arrays.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getAttribute());
        result = 31 * result + Arrays.hashCode(getKey());
        return result;
    }
}
