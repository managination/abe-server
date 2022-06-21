package com.example.abe;

import com.example.abe.DTO.AuthorityDTO;
import com.example.abe.DTO.PersonalKeysRequestDTO;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.model.Channel;
import com.example.abe.model.Client;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbeStepDefinition {

    public AbeStepDefinition(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private final RestTemplate restTemplate;

    private Long ciphertextId;
    private List<String> decryptedList;

    @Given("{string} has created {string} and {string}")
    public void hasCreatedAnd(String authority, String attributeName1, String attributeName2) {

        AuthorityDTO requestPayload = new AuthorityDTO(authority, new String[]{attributeName1, attributeName2});
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

    @Given("{string} is a client which has access to the private key of {string}")
    public void isAClientWhichHasAccessToThePrivateKeyOf(String clientName, String attributeName) {

        Client client = new Client(clientName, clientName + "@mail.com");
        restTemplate.postForLocation("http://localhost:8080/api/v1/client", client);
        Long clientId = getClientIdByEmail(clientName + "@mail.com");
        Long authorityKeysId = getAuthorityKeysId();
        String attribute = authorityKeysId + "_" + attributeName;

        PersonalKeysRequestDTO personalKeysRequestDTO = new PersonalKeysRequestDTO(clientId, authorityKeysId, attribute);
        restTemplate.put(
                "http://localhost:8080/api/v1/personalKeys/request",
                personalKeysRequestDTO
        );
    }

    @Given("a topic {string} exists")
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

    @When("anyone creates ciphertext by text {string} and an access structure of {string}")
    public void anyoneCreatesCiphertextByTextAndAnAccessStructureOf(String text, String policy) {

        Long authorityKeysId = getAuthorityKeysId();
        assert policy != null;
        String policyWithId = appendIdToPolicy(policy, authorityKeysId);
        System.out.println(policyWithId);
        ciphertextId = restTemplate.postForObject(
                "http://localhost:8080/api/v1/ciphertext/?policy=" + policyWithId,
                text,
                Long.class
        );
    }

    @Then("a new ciphertext exists in topic {string}" )
    public void aNewCiphertextExistsInTopic(String topic) {

        Channel channel = restTemplate.getForObject(
                "http://localhost:8080/api/v1/channel/" + topic,
                Channel.class
        );
        assert channel != null;
        assertThat(channel.getTopic().equals(topic)).isTrue();
        assertThat(channel.getBody().contains(ciphertextId)).isTrue();
    }

    @And("anyone publishes ciphertext to the topic {string}")
    public void anyonePublishesCiphertextToTheTopic(String topic) {

        restTemplate.put(
                "http://localhost:8080/api/v1/channel/" + topic + "?ciphertextId=" + ciphertextId,
                null);
    }

    @When("{string} decrypts topic {string}" )
    public void decryptsTopic(String clientName, String topic) {

        Long personalKeysId = getClientIdByEmail(clientName+"@mail.com");
        String[] decryptedArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/channel/decrypt/?topic=" + topic +
                        "&personalKeysId=" + personalKeysId,
                String[].class
        );
        assert decryptedArray != null;
        decryptedList = Arrays.stream(decryptedArray).peek(System.out::println).collect(Collectors.toList());
    }

    @Then("{string} is able to read message {string}" )
    public void isAbleToReadMessage(String clientName, String text) {

        assertThat(decryptedList.contains(text)).isTrue();
        System.out.println("Client " + clientName + " can read the message");
    }

    @Then("{string} is not able to read message {string}" )
    public void isNotAbleToReadMessage(String clientName, String text) {

        assertThat(decryptedList.contains(text)).isFalse();
        System.out.println("Client " + clientName + " can not read the message");
    }

    @After
    public void cleanAllData() {
        restTemplate.delete("http://localhost:8080/api/v1/general/cleanAllData");
    }

    private Long getClientIdByEmail(String clientEmail) {
        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        Optional<Client> clientOptional = Arrays.stream(clientArray)
                .filter(client -> Objects.equals(client.getEmail(), clientEmail))
                .findAny();
        assert clientOptional.isPresent();
        return clientOptional.get().getId();
    }

    private Long getAuthorityKeysId() {
        AuthorityKeys[] authorityKeysArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/authority",
                AuthorityKeys[].class
        );
        assert authorityKeysArray != null;
        Optional<AuthorityKeys> authorityKeysOptional = Arrays.stream(authorityKeysArray)
                .findAny(); // in case only one authority exists
        assert authorityKeysOptional.isPresent();
        return authorityKeysOptional.get().getId();
    }

    private String appendIdToPolicy(String policy, Long authorityId) {
        List<String> parts = Arrays.stream(policy.split(" "))
                .collect(Collectors.toList())
                .stream().map(part -> {
                    if (!Objects.equals(part, "or" ) && !Objects.equals(part, "and" ))
                        return authorityId + "_" + part;
                    else
                        return part;
                })
                .collect(Collectors.toList());
        return String.join("+", parts);
    }
}
