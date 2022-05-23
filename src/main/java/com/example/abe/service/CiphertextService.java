package com.example.abe.service;

import com.example.abe.dcpabe.access.AccessStructure;
import com.example.abe.dcpabe.other.Ciphertext;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.dcpabe.other.Message;
import com.example.abe.dcpabe.other.PublicKeys;
import com.example.abe.repository.CiphertextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class CiphertextService {

    private final CiphertextRepository ciphertextRepository;
    private final PublicKeysService publicKeysService;

    @Autowired
    public CiphertextService(CiphertextRepository ciphertextRepository,
                             PublicKeysService publicKeysService) {
        this.ciphertextRepository = ciphertextRepository;
        this.publicKeysService = publicKeysService;
    }

    public List<Ciphertext> getCiphertext() {
        return ciphertextRepository.findAll();
    }

    public void createCiphertext(String policy) {

        AccessStructure as = AccessStructure.buildFromPolicy(policy);
        Message message = DCPABE.generateRandomMessage(gp); //for now message is generated
        List<PublicKeys> publicKeysList = publicKeysService.getPublicKeys();
        PublicKeys publicKeys = new PublicKeys();
        for(PublicKeys pubKs : publicKeysList) {
            publicKeys.unitePublicKeys(pubKs.getPublicKeys());
        }
        Ciphertext ciphertext = DCPABE.encrypt(message, as, gp, publicKeys);

        ciphertextRepository.save(ciphertext);

    }

    public void deleteCiphertext(Long ciphertextId) {
        boolean exists = ciphertextRepository.existsById(ciphertextId);
        if (!exists) {
            throw new IllegalStateException("ciphertext with id " + ciphertextId + " does not exist");
        }
        ciphertextRepository.deleteById(ciphertextId);
    }
}
