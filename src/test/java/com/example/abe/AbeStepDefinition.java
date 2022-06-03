package com.example.abe;

import com.example.abe.DTO.AuthorityDTO;
import com.example.abe.DTO.PersonalKeysRequestDTO;
import com.example.abe.dcpabe.access.AccessStructure;
import com.example.abe.dcpabe.key.PublicKey;
import com.example.abe.dcpabe.other.*;
import com.example.abe.model.Channel;
import com.example.abe.model.Client;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbeStepDefinition {

    public AbeStepDefinition(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private final RestTemplate restTemplate;

    private String accessStructure;
    private int ciphertextsStartCount;
    private Ciphertext ciphertext;

    @After
    public void deleteAuthorityAndClient() {
        AuthorityKeys[] authorityKeysArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/authority",
                AuthorityKeys[].class
        );
        assert authorityKeysArray != null;
        Optional<AuthorityKeys> authorityKeysOptional = Arrays.stream(authorityKeysArray)
                .findAny();
        if (authorityKeysOptional.isPresent()) {
            Long authorityKeysId = authorityKeysOptional.get().getId();
            restTemplate.delete("http://localhost:8080/api/v1/authority/" + authorityKeysId);
        }

        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        Optional<Client> clientOptional = Arrays.stream(clientArray)
                .findAny();
        if (clientOptional.isPresent()) {
            Long clientId = clientOptional.get().getId();
            restTemplate.delete("http://localhost:8080/api/v1/client/" + clientId);
        }
    }

    @Given("{string} has created {string} and {string}")
    public void hasCreatedAnd(String authority, String attr1, String attr2) {

        AuthorityDTO requestPayload = new AuthorityDTO(authority, new String[]{attr1, attr2});
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

    @And("{string} is a client which has access to the private key of {string}")
    public void isAClientWhichHasAccessToThePrivateKeyOf(String clientName, String attr1) {

        Client client = new Client(clientName, clientName + "@mail.com");
        restTemplate.postForLocation("http://localhost:8080/api/v1/client", client);
        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        Optional<Client> clientOptional = Arrays.stream(clientArray)
                .filter(cl -> Objects.equals(cl.getName(), clientName))
                .findAny();
        assert clientOptional.isPresent();
        Long clientId = clientOptional.get().getId();

        AuthorityKeys[] authorityKeysArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/authority",
                AuthorityKeys[].class
        );
        assert authorityKeysArray != null;
        Optional<AuthorityKeys> authorityKeysOptional = Arrays.stream(authorityKeysArray)
                .findAny();
        assert authorityKeysOptional.isPresent();
        Long authorityKeysId = authorityKeysOptional.get().getId();

        PersonalKeysRequestDTO personalKeysRequestDTO = new PersonalKeysRequestDTO(clientId, authorityKeysId, attr1);
        restTemplate.put(
                "http://localhost:8080/api/v1/personalKeys/request",
                personalKeysRequestDTO
                );
    }

    @When("{string} receives the ciphertext of {string} encrypted with the access structure {string}")
    public void receivesTheCiphertextOfEncryptedWithTheAccessStructure(String clientName, String text, String policy) {

        AccessStructure as = AccessStructure.buildFromPolicy(policy);

        PublicKeys publicKeys = new PublicKeys();
        PublicKey publicKey1 = restTemplate.getForObject(
                "http://localhost:8080/api/v1/publicKeys/by-attribute/Attribute1",
                PublicKey.class
        );
        PublicKey publicKey2 = restTemplate.getForObject(
                "http://localhost:8080/api/v1/publicKeys/by-attribute/Attribute2",
                PublicKey.class
        );
        Map<String, PublicKey> pksMap = new HashMap<>();
        pksMap.put("Attribute1", publicKey1);
        pksMap.put("Attribute2", publicKey2);
        publicKeys.subscribeAuthority(pksMap);

        Message message = DCPABE.createByString(text, gp);
        // for local variable
        ciphertext = DCPABE.encrypt(message, as, gp, publicKeys);
        // for endpoint
        //restTemplate.postForLocation("http://localhost:8080/api/v1/ciphertext/?policy=" + policy, text);
    }

    @Then("{string} is able to decrypt the message and read {string}")
    public void isAbleToDecryptTheMessageAndRead(String clientName, String text) {

        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        Optional<Client> clientOptional = Arrays.stream(clientArray)
                .findAny();
        assert clientOptional.isPresent();
        Long clientId = clientOptional.get().getId();

        PersonalKeys personalKeys = restTemplate.getForObject(
                "http://localhost:8080/api/v1/personalKeys/" + clientId,
                PersonalKeys.class
        );
        assertThat(personalKeys != null).isTrue();
//        assertThat(ciphertext != null).isTrue();

//        Message dMessage = DCPABE.decrypt(ciphertext, personalKeys, gp);
//        String dText = new String(dMessage.getM());
//        System.out.println("text " + text);
//        System.out.println("dText " + dText);
//        assertThat(true).isTrue();
    }

    @Then("{string} is not able to decrypt the message and read {string}")
    public void isNotAbleToDecryptTheMessageAndRead(String clientName, String text) {

        PersonalKeys personalKeys = restTemplate.getForObject(
                "http://localhost:8080/api/v1/personalKeys/1",
                PersonalKeys.class
        );
        assertThat(personalKeys != null).isTrue();
        assertThat(ciphertext != null).isTrue();
    }

    @And("{string} updates the topic {string} with the ciphertext")
    public void updatesTheTopicWithTheCiphertext(String clientName, String topic) {

        Ciphertext[] ciphertextArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/ciphertext",
                Ciphertext[].class
        );
        assert ciphertextArray != null;
        Optional<Ciphertext> ciphertextOptional = Arrays.stream(ciphertextArray)
                .findAny();
        assert ciphertextOptional.isPresent();
        Long ciphertextId = ciphertextOptional.get().getId();

        restTemplate.put(
                "http://localhost:8080/api/v1/channel/" + topic + "?ciphertextId=" + ciphertextId,
                null
        );

    }

    @Then("{string} reads the last entry in the {string} body")
    public void readsTheLastEntryInTheBody(String clientName, String topic) {

        Channel channel = restTemplate.getForObject(
                "http://localhost:8080/api/v1/channel/" + topic,
                Channel.class
        );
        assert channel != null;
        Optional<Ciphertext> ciphertextOptional = Arrays.stream(channel.getBody().toArray(new Ciphertext[0]))
                .findAny();
        assert ciphertextOptional.isPresent();

    }
}
