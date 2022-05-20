package com.example.abe.channel;

import com.example.abe.ciphertext.CiphertextRepository;
import com.example.abe.dcpabe.other.Ciphertext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final CiphertextRepository ciphertextRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository,
                          CiphertextRepository ciphertextRepository) {
        this.channelRepository = channelRepository;
        this.ciphertextRepository = ciphertextRepository;
    }

    public List<Channel> getChannel() {
        return channelRepository.findAll();
    }

    public void createChannel(String topic) {
        Channel channel = new Channel(topic, List.of());
        channelRepository.save(channel);
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
}
