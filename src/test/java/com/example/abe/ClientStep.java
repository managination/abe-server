package com.example.abe;

import com.example.abe.model.Client;
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

public class ClientStep {

    public ClientStep(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private final RestTemplate restTemplate;

    @Given("client does not exist with email {string}")
    public void clientDoesNotExistWithEmail(String email) {

        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        List<String> emailList = Arrays.stream(clientArray)
                .map(Client::getEmail)
                .collect(Collectors.toList());
        assertThat(emailList.contains(email)).isFalse();
    }

    @When("user creates client with name {string} and email {string}")
    public void userCreatesClientWithNameAndEmail(String name, String email) {

        Client client = new Client(name, email);
        restTemplate.postForLocation("http://localhost:8080/api/v1/client", client);
    }

    @Then("client exists with name {string} and email {string}")
    public void clientExistsWithNameAndEmail(String name, String email) {

        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        List<String> nameList = Arrays.stream(clientArray)
                .map(Client::getName)
                .collect(Collectors.toList());
        List<String> emailList = Arrays.stream(clientArray)
                .map(Client::getEmail)
                .collect(Collectors.toList());
        assertThat(nameList.contains(name)).isTrue();
        assertThat(emailList.contains(email)).isTrue();
    }

    @When("user updates client with name {string} and email {string} to {string} and {string}")
    public void userUpdatesClientWithNameAndEmailToAnd(String name, String email, String newName, String newEmail) {

        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        Optional<Client> clientOptional = Arrays.stream(clientArray)
                .filter(client -> Objects.equals(client.getName(), name))
                .filter(client -> Objects.equals(client.getEmail(), email))
                .findAny();
        assert clientOptional.isPresent();
        Long clientId = clientOptional.get().getId();
        restTemplate.put("http://localhost:8080/api/v1/client/" + clientId +
                        "?name=" + newName +
                        "&email=" + newEmail,
                null);
    }

    @When("user delete client with name {string} and email {string}")
    public void userDeleteClientWithNameAndEmail(String name, String email) {

        Client[] clientArray = restTemplate.getForObject(
                "http://localhost:8080/api/v1/client",
                Client[].class
        );
        assert clientArray != null;
        Optional<Client> clientOptional = Arrays.stream(clientArray)
                .filter(client -> Objects.equals(client.getName(), name))
                .filter(client -> Objects.equals(client.getEmail(), email))
                .findAny();
        assert clientOptional.isPresent();
        Long clientId = clientOptional.get().getId();
        restTemplate.delete("http://localhost:8080/api/v1/client/" + clientId);
    }
}
