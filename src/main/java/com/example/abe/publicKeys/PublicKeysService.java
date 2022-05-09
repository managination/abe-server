package com.example.abe.publicKeys;

import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.PublicKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
