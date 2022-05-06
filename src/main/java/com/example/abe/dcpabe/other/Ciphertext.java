package com.example.abe.dcpabe.other;

import com.example.abe.dcpabe.access.AccessStructure;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity(name = "Ciphertext")
@Table(name = "ciphertext")
public class Ciphertext implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "ciphertext_sequence",
            sequenceName = "ciphertext_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ciphertext_sequence"
    )
    private Long id;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private byte[] c0;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<byte[]> c1;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<byte[]> c2;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<byte[]> c3;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private AccessStructure accessStructure;

    public Ciphertext() {
        c1 = new ArrayList<byte[]>();
        c2 = new ArrayList<byte[]>();
        c3 = new ArrayList<byte[]>();
    }

    public Long getId() {
        return id;
    }

    public byte[] getC0() {
        return c0;
    }

    public void setC0(byte[] c0) {
        this.c0 = c0;
    }

    public byte[] getC1(int x) {
        return c1.get(x);
    }

    public void setC1(byte[] c1x) {
        c1.add(c1x);
    }

    public byte[] getC2(int x) {
        return c2.get(x);
    }

    public void setC2(byte[] c2x) {
        c2.add(c2x);
    }

    public byte[] getC3(int x) {
        return c3.get(x);
    }

    public void setC3(byte[] c3x) {
        c3.add(c3x);
    }

    public AccessStructure getAccessStructure() {
        return accessStructure;
    }

    public void setAccessStructure(AccessStructure accessStructure) {
        this.accessStructure = accessStructure;
    }
}
