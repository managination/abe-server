package com.example.abe.service;

import com.example.abe.dcpabe.key.PublicKey;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.PublicKeys;
import com.example.abe.repository.PublicKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublicKeysService {

    private final PublicKeysRepository publicKeysRepository;

    @Autowired
    public PublicKeysService(PublicKeysRepository publicKeysRepository) {
        this.publicKeysRepository = publicKeysRepository;
    }

    public List<PublicKeys> getPublicKeys() {
        return publicKeysRepository.findAll();
    }

    public void addPublicKeys(AuthorityKeys authority) {
        Long authorityId = authority.getId();
        boolean publicKeysExist = publicKeysRepository.existsById(authorityId);
        if (publicKeysExist) {
            PublicKeys publicKeys = publicKeysRepository.findById(authorityId)
                    .orElseThrow(() -> new IllegalStateException(
                            ("public keys with id " + authorityId + " does not exist")));

            publicKeys.subscribeAuthority(authority.getPublicKeys());
            publicKeysRepository.save(publicKeys);
        } else {
            PublicKeys publicKeys = new PublicKeys();
            publicKeys.subscribeAuthority(authority.getPublicKeys());
            publicKeysRepository.save(publicKeys);
        }
    }

    public PublicKeys getPublicKeysByAttribute(String attribute) {
        Set<String> allAttributes = getAllAttributes();
        if (!allAttributes.contains(attribute)) {
            throw new IllegalStateException("attribute does not exist");
        }

        List<PublicKeys> publicKeysList = publicKeysRepository.findAll();
        Long publicKeysId = publicKeysList.stream()
                .filter(publicKeys -> publicKeys.getAllAttributes().contains(attribute))
                .findFirst().get().getId();

        return publicKeysRepository.getById(publicKeysId);
    }

    public PublicKey getPublicKeyByAttribute(String attribute) {
        return getPublicKeysByAttribute(attribute).getPK(attribute);
    }

    public void removePublicKey(String attribute) {
        PublicKeys publicKeys = getPublicKeysByAttribute(attribute);
        publicKeys.removePK(attribute);
        publicKeysRepository.save(publicKeys);
    }

    public Set<String> getAllAttributes() {
        List<PublicKeys> publicKeysList = publicKeysRepository.findAll();
        Set<String> attributes = new HashSet<>();
        List<PublicKeys> list = publicKeysList
                .stream()
                .peek(publicKeys -> attributes.addAll(publicKeys.getAllAttributes()))
                .collect(Collectors.toList());
        return attributes;
    }

}
