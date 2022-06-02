package com.example.abe.DTO;

public class PersonalKeysRequestDTO {

    private Long clientId;
    private Long authorityId;
    private String attribute;

    public PersonalKeysRequestDTO(Long clientId,
                                  Long authorityId,
                                  String attribute) {
        this.clientId = clientId;
        this.authorityId = authorityId;
        this.attribute = attribute;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

}
