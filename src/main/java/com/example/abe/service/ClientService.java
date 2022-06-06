package com.example.abe.service;

import com.example.abe.repository.ClientRepository;
import com.example.abe.dcpabe.other.PersonalKeys;
import com.example.abe.model.Client;
import com.example.abe.repository.PersonalKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PersonalKeysRepository personalKeysRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         PersonalKeysRepository personalKeysRepository) {
        this.clientRepository = clientRepository;
        this.personalKeysRepository = personalKeysRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        String clientEmail = client.getEmail();
        Optional<Client> optionalClient = clientRepository.findClientByEmail(clientEmail);
        if (optionalClient.isPresent()) {
            throw new IllegalStateException("email " + clientEmail + " is taken");
        }
        clientRepository.save(client);

        //create empty personal keys for client
        Client newClient = clientRepository.findClientByEmail(clientEmail)
                .orElseThrow(() -> new IllegalStateException("client with email " + clientEmail + " was not saved"));
        PersonalKeys personalKeys = new PersonalKeys(newClient.getId() + "_" + newClient.getName());
        personalKeysRepository.save(personalKeys);
    }

    public void deleteClient(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("client with id " + clientId + " does not exist");
        }
        clientRepository.deleteById(clientId);
    }

    @Transactional
    public void updateClient(Long clientId,
                             String name,
                             String email) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException(
                        ("client with id " + clientId + " does not exist")));

        if (name != null && name.length() > 0 && !Objects.equals(client.getName(), name)) {
            client.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(client.getEmail(), email)) {
            Optional<Client> optionalClient = clientRepository
                    .findClientByEmail(email);
            if (optionalClient.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            client.setEmail(email);
        }
    }
}
