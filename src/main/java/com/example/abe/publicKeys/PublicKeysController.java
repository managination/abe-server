package com.example.abe.publicKeys;

import com.example.abe.dcpabe.other.PublicKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/publicKeys")
public class PublicKeysController {

    private final PublicKeysService publicKeysService;

    @Autowired
    public PublicKeysController(PublicKeysService publicKeysService) {
        this.publicKeysService = publicKeysService;
    }

    @GetMapping
    public List<PublicKeys> getPublicKeys() {
        return publicKeysService.getPublicKeys();
    }

}
