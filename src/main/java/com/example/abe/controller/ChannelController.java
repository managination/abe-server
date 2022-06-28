package com.example.abe.controller;

import com.example.abe.model.Channel;
import com.example.abe.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/channel")
public class ChannelController {

    private final ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

    @PostMapping
    public void createChannel(@RequestParam String topic) {
        channelService.createChannel(topic);
    }

    @DeleteMapping(path = "{channelId}")
    public void deleteChannel(@PathVariable("channelId") Long channelId) {
        channelService.deleteChannel(channelId);
    }

    @PutMapping(path = "{topic}")
    public void updateChannel(@PathVariable("topic") String topic,
                              @RequestParam Long ciphertextId) {
        channelService.updateChannel(topic, ciphertextId);
    }

    @GetMapping(path = "{topic}")
    public Channel getChannel(@PathVariable("topic") String topic) {
        return channelService.getChannel(topic);
    }

    @GetMapping(path = "/decrypt")
    public List<String> decryptChannel(@RequestParam String topic,
                                       @RequestParam Long personalKeysId) throws IOException, ClassNotFoundException {
        return channelService.decryptChannel(topic, personalKeysId);
    }

}
