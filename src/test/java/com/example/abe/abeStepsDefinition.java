package com.example.abe;

import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.model.AuthorityRequestPayload;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class abeStepsDefinition {

    public abeStepsDefinition(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private final RestTemplate restTemplate;

    @Given("{string} has created {string} and {string}")
    public void hasCreatedAnd(String authority, String attr1, String attr2) {
        AuthorityRequestPayload requestPayload = new AuthorityRequestPayload(authority, new String[]{attr1, attr2});
        restTemplate.postForLocation("http://localhost:8080/api/v1/authority", requestPayload);

        assertThat(restTemplate.getForObject("http://localhost:8080/api/v1/authority", AuthorityKeys[].class))
                .isNotEmpty();
    }

    @And("a topic {string} exists")
    public void aTopicExists(String arg0) {
    }

    @And("{string} is a client that creates an access structure of {string}")
    public void isAClientThatCreatesAnAccessStructureOf(String arg0, String arg1) {
    }

    @When("{string} encrypts the message {string} with the access structure")
    public void encryptsTheMessageWithTheAccessStructure(String arg0, String arg1) {
    }

    @Then("{string} receives a ciphertext which can be decrypted")
    public void receivesACiphertextWhichCanBeDecrypted(String arg0) {

    }
}
