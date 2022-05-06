package com.example.abe.dcpabe.other;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.abe.dcpabe.key.PersonalKey;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity(name = "PersonalKeys")
@Table(name = "personal_keys")
public class PersonalKeys {

    @Id
    @SequenceGenerator(
            name = "personal_keys_sequence",
            sequenceName = "personal_keys_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "personal_keys_sequence"
    )
    private Long id;

    @JsonProperty("userID")
    private String userID;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("personalKeys")
    private Map<String, PersonalKey> personalKeys;

    public PersonalKeys(String userID) {
        this.userID = userID;
        personalKeys = new HashMap<>();
    }

    private PersonalKeys() {
    }

    public void addKey(PersonalKey pkey) {
        personalKeys.put(pkey.getAttribute(), pkey);
    }

    public Long getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    @JsonIgnore
    public Collection<String> getAttributes() {
        return personalKeys.keySet();
    }

    @JsonIgnore
    public PersonalKey getKey(String attribute) {
        return personalKeys.get(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalKeys that = (PersonalKeys) o;
        return Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(personalKeys, that.personalKeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), personalKeys);
    }
}
