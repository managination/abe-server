package com.example.abe.service;

import com.example.abe.dcpabe.access.AccessStructure;
import com.example.abe.dcpabe.other.*;
import com.example.abe.repository.CiphertextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

//import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class CiphertextService {

    private final CiphertextRepository ciphertextRepository;
    private final PublicKeysService publicKeysService;
    private final GlobalParametersService globalParametersService;

    @Autowired
    public CiphertextService(CiphertextRepository ciphertextRepository,
                             PublicKeysService publicKeysService,
                             GlobalParametersService globalParametersService) {
        this.ciphertextRepository = ciphertextRepository;
        this.publicKeysService = publicKeysService;
        this.globalParametersService = globalParametersService;
    }

    public List<Ciphertext> getCiphertext() {
        return ciphertextRepository.findAll();
    }

    public void createCiphertext(String policy) throws IOException, ClassNotFoundException {

        AccessStructure as = AccessStructure.buildFromPolicy(policy);
        GlobalParameters gp = globalParametersService.getGlobalParameters();
        Message message = DCPABE.generateRandomMessage(gp); // message is generated
        List<PublicKeys> publicKeysList = publicKeysService.getPublicKeys();
        PublicKeys publicKeys = new PublicKeys();
        List<PublicKeys> list = publicKeysList
                .stream()
                .peek(pks -> publicKeys.unitePublicKeys(pks.getPublicKeys()))
                .collect(Collectors.toList());
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

    public Long createCiphertextByString(String policy, String text) throws IOException, ClassNotFoundException {
        AccessStructure as = AccessStructure.buildFromPolicy(policy);
        GlobalParameters gp = globalParametersService.getGlobalParameters();
        Message message = DCPABE.createByString(text, gp);  // message by custom text
        List<PublicKeys> publicKeysList = publicKeysService.getPublicKeys();
        PublicKeys publicKeys = new PublicKeys();
        List<PublicKeys> list = publicKeysList
                .stream()
                .peek(pks -> publicKeys.unitePublicKeys(pks.getPublicKeys()))
                .collect(Collectors.toList());
        Ciphertext ciphertext = DCPABE.encrypt(message, as, gp, publicKeys);
        ciphertextRepository.save(ciphertext);
        return ciphertext.getId();
    }

    public Ciphertext getCiphertextById(Long ciphertextId) {
        return ciphertextRepository.findById(ciphertextId)
                .orElseThrow(() -> new IllegalStateException(
                        ("ciphertext with id " + ciphertextId + " does not exist")));
    }
}
