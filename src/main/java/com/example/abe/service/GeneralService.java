package com.example.abe.service;

import com.example.abe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {

    private final AuthorityRepository authorityRepository;
    private final ChannelRepository channelRepository;
    private final CiphertextRepository ciphertextRepository;
    private final ClientRepository clientRepository;
    private final PersonalKeysRepository personalKeysRepository;
    private final PublicKeysRepository publicKeysRepository;

    @Autowired
    public GeneralService(AuthorityRepository authorityRepository,
                          ChannelRepository channelRepository,
                          CiphertextRepository ciphertextRepository,
                          ClientRepository clientRepository,
                          PersonalKeysRepository personalKeysRepository,
                          PublicKeysRepository publicKeysRepository) {
        this.authorityRepository = authorityRepository;
        this.channelRepository = channelRepository;
        this.ciphertextRepository = ciphertextRepository;
        this.clientRepository = clientRepository;
        this.personalKeysRepository = personalKeysRepository;
        this.publicKeysRepository = publicKeysRepository;
    }

    public void cleanAllData() {
        authorityRepository.deleteAll();
        channelRepository.deleteAll();
        ciphertextRepository.deleteAll();
        clientRepository.deleteAll();
        personalKeysRepository.deleteAll();
        publicKeysRepository.deleteAll();
    }

}
