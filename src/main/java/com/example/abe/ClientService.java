package com.example.abe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        Optional<Client> optionalClient = clientRepository
                .findClientByEmail(client.getEmail());
        if (optionalClient.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        clientRepository.save(client);
    }

    public void deleteClient(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("client with id " + clientId + " does not exist");
        }
        clientRepository.deleteById(clientId);
    }

}
