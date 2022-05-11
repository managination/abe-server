package com.example.abe.dcpabe.other;

import com.example.abe.dcpabe.key.PublicKey;
import com.example.abe.dcpabe.key.SecretKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity(name = "AuthorityKeys")
@Table(name = "authority_keys")
public class AuthorityKeys implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "authority_keys_sequence",
            sequenceName = "authority_keys_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "authority_keys_sequence"
    )
    private Long id;

    @JsonProperty("name")
    private String name;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("publicKeys")
    private Map<String, PublicKey> publicKeys;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("secretKeys")
    private Map<String, SecretKey> secretKeys;

    public AuthorityKeys(String authorityID) {
        this.name = authorityID;
        publicKeys = new HashMap<>();
        secretKeys = new HashMap<>();
    }

    private AuthorityKeys() {
    }

    public Long getId() {
        return id;
    }

    public String getAuthorityName() {
        return name;
    }

    public Map<String, PublicKey> getPublicKeys() {
        return publicKeys;
    }

    public Map<String, SecretKey> getSecretKeys() {
        return secretKeys;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removePK(String attribute) {
        publicKeys.remove(attribute);
    }

    public void removeSK(String attribute) {
        secretKeys.remove(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityKeys that = (AuthorityKeys) o;
        return Objects.equals(getAuthorityName(), that.getAuthorityName()) &&
                Objects.equals(getPublicKeys(), that.getPublicKeys()) &&
                Objects.equals(getSecretKeys(), that.getSecretKeys());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorityName(), getPublicKeys(), getSecretKeys());
    }
}
