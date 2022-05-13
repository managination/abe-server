package com.example.abe.ciphertext;

import com.example.abe.dcpabe.access.AccessStructure;
import com.example.abe.dcpabe.other.Ciphertext;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.dcpabe.other.Message;
import com.example.abe.dcpabe.other.PublicKeys;
import com.example.abe.publicKeys.PublicKeysService;
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
        PublicKeys publicKeys = publicKeysService.getPublicKeys().get(0);
        Ciphertext ciphertext = DCPABE.encrypt(message, as, gp, publicKeys);

        ciphertextRepository.save(ciphertext);

    }
}
