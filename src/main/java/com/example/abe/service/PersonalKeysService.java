package com.example.abe.service;

import com.example.abe.DTO.PersonalKeysRequestDTO;
import com.example.abe.dcpabe.other.GlobalParameters;
import com.example.abe.model.Client;
import com.example.abe.repository.AuthorityRepository;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.dcpabe.other.PersonalKeys;
import com.example.abe.repository.ClientRepository;
import com.example.abe.repository.PersonalKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

//import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class PersonalKeysService {

    private final PersonalKeysRepository personalKeysRepository;
    private final AuthorityRepository authorityRepository;
    private final ClientRepository clientRepository;
    private final GlobalParametersService globalParametersService;

    @Autowired
    public PersonalKeysService(PersonalKeysRepository personalKeysRepository,
                               AuthorityRepository authorityRepository,
                               ClientRepository clientRepository,
                               GlobalParametersService globalParametersService) {
        this.personalKeysRepository = personalKeysRepository;
        this.authorityRepository = authorityRepository;
        this.clientRepository = clientRepository;
        this.globalParametersService = globalParametersService;
    }

    public List<PersonalKeys> getPersonalKeys() {
        return personalKeysRepository.findAll();
    }

    public PersonalKeys addPersonalKey(PersonalKeysRequestDTO body) throws IOException, ClassNotFoundException {

        Long clientId = body.getClientId();
        Long authorityId = body.getAuthorityId();
        String attribute = body.getAttribute();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException(
                        "client with id " + clientId + " does not exist"));

        PersonalKeys personalKeys = personalKeysRepository.findPersonalKeysById(clientId)
                .orElseThrow(() -> new IllegalStateException(
                        "personal keys with id " + clientId + " does not exist"));

        AuthorityKeys authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new IllegalStateException(
                        ("authority with id " + authorityId + " does not exist")));

        String clientIdAndName = clientId + "_" + client.getName();
        GlobalParameters gp = globalParametersService.getGlobalParameters();
        personalKeys.addKey(DCPABE.keyGen(
                clientIdAndName,
                attribute,
                authority.getSecretKeys().get(attribute),
                gp));
        personalKeysRepository.save(personalKeys);
        return personalKeys;

    }

    public PersonalKeys getPersonalKeysById(Long personalKeysId) {
        return personalKeysRepository.findById(personalKeysId)
                .orElseThrow(() -> new IllegalStateException(
                        ("personalKeys with id " + personalKeysId + " does not exist")));
    }
}
