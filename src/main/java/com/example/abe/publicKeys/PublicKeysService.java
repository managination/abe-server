package com.example.abe.publicKeys;

import com.example.abe.dcpabe.key.PublicKey;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.PublicKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        PublicKeys publicKeys = new PublicKeys();
        publicKeys.subscribeAuthority(authority.getPublicKeys());
        publicKeysRepository.save(publicKeys);
    }

    public PublicKey getPublicKeyByAttribute(String attribute) {
        List<PublicKeys> pks = publicKeysRepository.findAll();
        return pks.get(0).getPK(attribute);
    }

    public void removePublicKey(String attribute) {
        PublicKeys publicKeys = publicKeysRepository.findAll().get(0);
        publicKeys.removePK(attribute);
        publicKeysRepository.save(publicKeys);
    }

    public Set<String> getAllAttributes() {
        List<PublicKeys> pks = publicKeysRepository.findAll();
        Set<String> str = new HashSet<>();
        for(PublicKeys pk : pks) {
            str.addAll(pk.getAllAttributes());
        }
        return str;
    }

}
