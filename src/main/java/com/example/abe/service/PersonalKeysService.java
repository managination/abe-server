package com.example.abe.service;

import com.example.abe.repository.AuthorityRepository;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.dcpabe.other.PersonalKeys;
import com.example.abe.repository.PersonalKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class PersonalKeysService {

    private final PersonalKeysRepository personalKeysRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public PersonalKeysService(PersonalKeysRepository personalKeysRepository,
                               AuthorityRepository authorityRepository) {
        this.personalKeysRepository = personalKeysRepository;
        this.authorityRepository = authorityRepository;
    }

    public List<PersonalKeys> getPersonalKeys() {
        return personalKeysRepository.findAll();
    }

    public PersonalKeys addPersonalKey(String clientName, Long authorityId, String attribute) {
        AuthorityKeys authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new IllegalStateException(
                        ("authority with id " + authorityId + " does not exist")));

        Optional<PersonalKeys> optionalPersonalKeys = personalKeysRepository.findPersonalKeysByName(clientName);
        if (optionalPersonalKeys.isEmpty()) {
            throw new IllegalStateException("personal keys for client " + clientName + " does not exist");
        } else {
            PersonalKeys personalKeys = optionalPersonalKeys.get();
            personalKeys.addKey(DCPABE.keyGen(clientName, attribute, authority.getSecretKeys().get(attribute), gp));
            personalKeysRepository.save(personalKeys);
            return personalKeys;
        }
    }
}
