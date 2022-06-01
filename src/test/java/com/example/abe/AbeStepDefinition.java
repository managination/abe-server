package com.example.abe;

import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.Ciphertext;
import com.example.abe.model.AuthorityRequestPayload;
import com.example.abe.model.Channel;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbeStepDefinition {

    public AbeStepDefinition(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private final RestTemplate restTemplate;

    private String accessStructure;
    private int ciphertextsStartCount;

    @Given("{string} has created {string} and {string}")
    public void hasCreatedAnd(String authority, String attr1, String attr2) {

        AuthorityRequestPayload requestPayload = new AuthorityRequestPayload(authority, new String[]{attr1, attr2});
        restTemplate.postForLocation("http://localhost:8080/api/v1/authority", requestPayload);
        AuthorityKeys[] authorityKeysArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/authority",
                AuthorityKeys[].class
        );
        assert authorityKeysArray != null;
        assertThat(Arrays.stream(authorityKeysArray)
                .map(aks -> aks.getAuthorityName().equals(authority))
                .collect(Collectors.toList())
                .contains(true)).isTrue();
    }

    @And("a topic {string} exists")
    public void aTopicExists(String topic) {

        restTemplate.postForLocation("http://localhost:8080/api/v1/channel/?topic=" + topic, null);
        Channel[] channels = restTemplate.getForObject(
                "http://localhost:8080/api/v1/channel",
                Channel[].class
        );
        assert channels != null;
        assertThat(Arrays.stream(channels)
                .map(channel -> channel.getTopic().equals(topic))
                .collect(Collectors.toList())
                .contains(true)).isTrue();

    }

    @And("{string} is a client that creates an access structure of {string}")
    public void isAClientThatCreatesAnAccessStructureOf(String clientName, String policy) {

        accessStructure = policy;
    }

    @When("{string} encrypts the message {string} with the access structure")
    public void encryptsTheMessageWithTheAccessStructure(String clientName, String text) {

        Ciphertext[] ciphertexts = restTemplate.getForObject(
                "http://localhost:8080/api/v1/ciphertext",
                Ciphertext[].class
        );
        assert ciphertexts != null;
        ciphertextsStartCount = ciphertexts.length;
        restTemplate.postForLocation("http://localhost:8080/api/v1/ciphertext/?policy=" + accessStructure, text);
    }

    @Then("{string} receives a ciphertext which can be decrypted")
    public void receivesACiphertextWhichCanBeDecrypted(String clientName) {

        Ciphertext[] ciphertexts = restTemplate.getForObject(
                "http://localhost:8080/api/v1/ciphertext",
                Ciphertext[].class
        );
        assert ciphertexts != null;
        int ciphertextsEndCount = ciphertexts.length;
        assertThat(ciphertextsStartCount + 1 == ciphertextsEndCount).isTrue();
    }
}
