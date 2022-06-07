package com.example.abe.service;

import com.example.abe.DTO.AuthorityDTO;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final PublicKeysService publicKeysService;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository,
                            PublicKeysService publicKeysService) {
        this.authorityRepository = authorityRepository;
        this.publicKeysService = publicKeysService;
    }

    public List<AuthorityKeys> getAuthorityKeys() {
        return authorityRepository.findAll();
    }

    public void createAuthority(AuthorityDTO body) {
        Optional<AuthorityKeys> optionalAuthority = authorityRepository
                .findAuthorityByName(body.getName());
        if (optionalAuthority.isPresent()) {
            throw new IllegalStateException("authority already exists");
        }
        AuthorityKeys authority = DCPABE.authoritySetup(body.getName(), gp);
        authorityRepository.save(authority);
        Long authorityId = authorityRepository.findAuthorityByName(body.getName())
                .orElseThrow(() -> new IllegalStateException("authority was not saved")).getId();
        updateAuthority(authorityId, body.getName(), body.getAttributes());

    }

    //deletes authority or just attribute if it is present in parameter
    public void deleteAuthority(Long authorityId, String attribute) {

        boolean authorityExists = authorityRepository.existsById(authorityId);
        if (!authorityExists) {
            throw new IllegalStateException("authority with id " + authorityId + " does not exist");
        }

        Set<String> allAttributes = publicKeysService.getAllAttributes();
        if (attribute != null && !allAttributes.contains(attribute)) {
            throw new IllegalStateException("attribute does not exist");
        }

        if (attribute == null) {
            authorityRepository.deleteById(authorityId);
        } else {
            AuthorityKeys authority = authorityRepository.getById(authorityId);
            authority.removePK(attribute);
            authority.removeSK(attribute);
            authorityRepository.save(authority);
            publicKeysService.removePublicKey(attribute); //remove authority PKs from all PKs
        }
    }

    @Transactional
    public void updateAuthority(Long authorityId,
                                String name,
                                String[] attributes) {
        AuthorityKeys authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new IllegalStateException(
                        ("authority with id " + authorityId + " does not exist")));
        if (attributes == null || attributes.length == 0) {
            throw new IllegalStateException("attribute was not defined");
        }
        if (name != null && name.length() > 0 && !Objects.equals(authority.getAuthorityName(), name)) {
            authority.setName(name);
        }

        String[] attributesWithId = Arrays.stream(attributes)
                .map(attribute -> authorityId + "_" + attribute)
                .toArray(String[]::new);
        boolean attributeRepeated = Arrays.stream(attributesWithId)
                .map(attribute -> {
                    return authority.getPublicKeys().containsKey(attribute);
                        }
                ).collect(Collectors.toList()).contains(true);
        if (attributeRepeated) {
            throw new IllegalStateException("one of attribute already exists");
        }
        DCPABE.authorityUpdate(authority, gp, attributesWithId);
        authorityRepository.save(authority);
        publicKeysService.addPublicKeys(authority); //add authority PKs to all PKs
    }

    public AuthorityKeys AuthorityKeysByName(String authorityName) {
        Optional<AuthorityKeys> optionalAuthority = authorityRepository
                .findAuthorityByName(authorityName);
        if (optionalAuthority.isEmpty()) {
            throw new IllegalStateException("authority with name " + authorityName + " does not exist");
        }
        return optionalAuthority.get();
    }

}
