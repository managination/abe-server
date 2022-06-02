package com.example.abe.DTO;

public class AuthorityDTO {
    private String name;
    private String[] attributes;

    public AuthorityDTO(String name,
                        String[] attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

}
