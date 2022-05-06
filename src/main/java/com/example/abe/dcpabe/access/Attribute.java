package com.example.abe.dcpabe.access;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Attribute")
@Table(name = "attribute")
public class Attribute extends TreeNode {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "attribute_sequence",
            sequenceName = "attribute_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "attribute_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

//    @Column(
//            name = "name",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
    private String name;

//    @Column(
//            name = "x",
//            nullable = false,
//            columnDefinition = "INT"
//    )
    private int x;

    public Attribute(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + x;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Attribute))
            return false;
        Attribute other = (Attribute) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return x == other.x;
    }
}
