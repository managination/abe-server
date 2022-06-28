package com.example.abe.service;

import com.example.abe.dcpabe.other.Ciphertext;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.dcpabe.other.GlobalParameters;
import com.example.abe.dcpabe.other.PersonalKeys;
import com.example.abe.model.Channel;
import com.example.abe.repository.ChannelRepository;
import com.example.abe.repository.CiphertextRepository;
import com.example.abe.repository.PersonalKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final CiphertextRepository ciphertextRepository;
    private final PersonalKeysRepository personalKeysRepository;
    private final GlobalParametersService globalParametersService;

    @Autowired
    public ChannelService(ChannelRepository channelRepository,
                          CiphertextRepository ciphertextRepository,
                          PersonalKeysRepository personalKeysRepository,
                          GlobalParametersService globalParametersService) {
        this.channelRepository = channelRepository;
        this.ciphertextRepository = ciphertextRepository;
        this.personalKeysRepository = personalKeysRepository;
        this.globalParametersService = globalParametersService;
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public void createChannel(String topic) {
        Channel channel = new Channel(topic, List.of());
        channelRepository.save(channel);
    }

    public void deleteChannel(Long channelId) {
        boolean exists = channelRepository.existsById(channelId);
        if (!exists) {
            throw new IllegalStateException("channel with id " + channelId + " does not exist");
        }
        channelRepository.deleteById(channelId);
    }

    @Transactional
    public void updateChannel(String topic, Long ciphertextId) {
        Optional<Channel> optionalChannel = channelRepository
                .findChannelByTopic(topic);
        if (optionalChannel.isEmpty()) {
            throw new IllegalStateException("channel does not exist");
        }
        boolean ciphertextExists = ciphertextRepository.existsById(ciphertextId);
        if (!ciphertextExists) {
            throw new IllegalStateException("ciphertext with id " + ciphertextId + " does not exist");
        }

        Channel channel = optionalChannel.get();
        channel.addCiphertext(ciphertextId);
        channelRepository.save(channel);
    }

    public Channel getChannel(String topic) {
        Optional<Channel> optionalChannel = channelRepository
                .findChannelByTopic(topic);
        if (optionalChannel.isEmpty()) {
            throw new IllegalStateException("channel with topic " + topic + " does not exist");
        }
        return optionalChannel.get();
    }

    public List<String> decryptChannel(String topic, Long personalKeysId) throws IOException, ClassNotFoundException {
        Optional<Channel> optionalChannel = channelRepository
                .findChannelByTopic(topic);
        if (optionalChannel.isEmpty()) {
            throw new IllegalStateException("channel with topic " + topic + " does not exist");
        }
        Optional<PersonalKeys> personalKeysOpt = personalKeysRepository.findById(personalKeysId);
        if (personalKeysOpt.isEmpty()) {
            throw new IllegalStateException("personalKeys with id " + personalKeysId + " does not exist");
        }

        GlobalParameters gp = globalParametersService.getGlobalParameters();
        PersonalKeys personalKeys = personalKeysOpt.get();
        List<Long> ciphertextIdList = optionalChannel.get().getBody();
        List<Ciphertext> ciphertexts = ciphertextIdList.stream()
                .map(ciphertextRepository::getById)
                .collect(Collectors.toList());
        return ciphertexts.stream()
                .map(ciphertext -> {
                    List<Integer> toUse = ciphertext.getAccessStructure().getIndexesList(personalKeys.getAttributes());
                    if (null == toUse || toUse.isEmpty())
                        return DCPABE.createByString("NOT DECRYPTED", gp);
                    else
                        return DCPABE.decrypt(ciphertext, personalKeys, gp);
                })
                .map(message -> new String(message.getM()).replace("\0", ""))
                .collect(Collectors.toList());
    }
}
