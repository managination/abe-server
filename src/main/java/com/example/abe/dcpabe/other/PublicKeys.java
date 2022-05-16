package com.example.abe.dcpabe.other;

import com.example.abe.dcpabe.key.PublicKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity(name = "PublicKeys")
@Table(name = "public_keys")
public class PublicKeys {

    @Id
    @SequenceGenerator(
            name = "public_keys_sequence",
            sequenceName = "public_keys_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "public_keys_sequence"
    )
    private Long id;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("publicKeys")
    private Map<String, PublicKey> publicKeys;

    public PublicKeys() {
        publicKeys = new HashMap<>();
    }

    public void subscribeAuthority(Map<String, PublicKey> pks) {
        publicKeys.putAll(pks);
    }

    public void unitePublicKeys(Map<String, PublicKey> pks) {
        publicKeys.putAll(pks);
    }

    public Long getId() {
        return id;
    }

    public PublicKey getPK(String attribute) {
        return publicKeys.get(attribute);
    }

    public Map<String, PublicKey> getPublicKeys() {
        return publicKeys;
    }

    public Set<String> getAllAttributes() {
        return publicKeys.keySet();
    }

    public void removePK(String attribute) {
        publicKeys.remove(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicKeys that = (PublicKeys) o;
        return Objects.equals(publicKeys, that.publicKeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicKeys);
    }
}
