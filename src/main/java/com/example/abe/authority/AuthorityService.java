package com.example.abe.authority;

import com.example.abe.dcpabe.key.PersonalKey;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.publicKeys.PublicKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public void addNewAuthority(AuthorityRequest body) {
        Optional<AuthorityKeys> optionalAuthority = authorityRepository
                .findAuthorityByName(body.getName());
        if (optionalAuthority.isPresent()) {
            throw new IllegalStateException("authority already exists");
        }
        AuthorityKeys authority = DCPABE.authoritySetup(body.getName(), gp, body.getAttributes());
        authorityRepository.save(authority);
        publicKeysService.addPublicKeys(authority); //add authority PKs to all PKs

    }

    public void deleteAuthority(Long authorityId, String attribute) {

        boolean authorityExists = authorityRepository.existsById(authorityId);
        if (!authorityExists) {
            throw new IllegalStateException("authority with id " + authorityId + " does not exist");
        }

        boolean attributeExists = authorityRepository.findAll().get(0).getPublicKeys().containsKey(attribute);
        if (!attributeExists) {
            authorityRepository.deleteById(authorityId);
        } else {
            AuthorityKeys authority = authorityRepository.findById(authorityId)
                    .orElseThrow(() -> new IllegalStateException(
                            ("authority with id " + authorityId + " does not exist")));

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

        if (name != null && name.length() > 0 && !Objects.equals(authority.getAuthorityName(), name)) {
            authority.setName(name);
        }

        if (attributes != null && attributes.length > 0 &&
                !Arrays.equals(authority.getPublicKeys().keySet().toArray(), attributes)) {
            DCPABE.authorityUpdate(authority, gp, attributes);
            authorityRepository.save(authority);
            publicKeysService.addPublicKeys(authority); //add authority PKs to all PKs
        }
    }

    public PersonalKey getPersonalKey(String clientName, Long authorityId, String attribute) {
        AuthorityKeys authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new IllegalStateException(
                        ("authority with id " + authorityId + " does not exist")));

        return DCPABE.keyGen(clientName, attribute, authority.getSecretKeys().get(attribute), gp);
        // personal key is generated, but isn't stored
    }
}
