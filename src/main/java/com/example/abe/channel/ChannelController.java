package com.example.abe.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Channel> getChannel() {
        return channelService.getChannel();
    }

    @PostMapping
    public void createChannel(@RequestParam String topic) {
        channelService.createChannel(topic);
    }

    @PutMapping(path = "{topic}")
    public void updateChannel(@PathVariable("topic") String topic,
                              @RequestParam Long ciphertextId) {
        channelService.updateChannel(topic, ciphertextId);
    }

}
