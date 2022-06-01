package com.example.abe.service;

import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.dcpabe.other.Message;
import com.example.abe.dcpabe.other.PersonalKeys;
import com.example.abe.model.Channel;
import com.example.abe.repository.ChannelRepository;
import com.example.abe.repository.CiphertextRepository;
import com.example.abe.dcpabe.other.Ciphertext;
import com.example.abe.repository.PersonalKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final CiphertextRepository ciphertextRepository;
    private final PersonalKeysRepository personalKeysRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository,
                          CiphertextRepository ciphertextRepository,
                          PersonalKeysRepository personalKeysRepository) {
        this.channelRepository = channelRepository;
        this.ciphertextRepository = ciphertextRepository;
        this.personalKeysRepository = personalKeysRepository;
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
        Channel channel = optionalChannel.get();

        Ciphertext ciphertext = ciphertextRepository.findById(ciphertextId)
                .orElseThrow(() -> new IllegalStateException(
                        ("ciphertext with id " + ciphertextId + " does not exist")));

        channel.addCiphertext(ciphertext);
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

    //delete later, should not be on service part, just local
    public List<String> decryptChannel(String topic, Long personalKeysId) {
        Optional<Channel> optionalChannel = channelRepository
                .findChannelByTopic(topic);
        if (optionalChannel.isEmpty()) {
            throw new IllegalStateException("channel with topic " + topic + " does not exist");
        }
        Optional<PersonalKeys> personalKeysOpt = personalKeysRepository.findById(personalKeysId);
        if (personalKeysOpt.isEmpty()) {
            throw new IllegalStateException("personalKeys with id " + personalKeysId + " does not exist");
        }
        Ciphertext ciphertext = ciphertextRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException(
                        ("ciphertext with id " + 1L + " does not exist")));

        PersonalKeys personalKeys = personalKeysOpt.get();
        Message message = DCPABE.decrypt(ciphertext, personalKeys, gp);
        List<String> decrypted = new ArrayList<>();
        decrypted.add(new String(message.getM()));
        return decrypted;
    }
}
