package com.example.abe.publicKeys;

import com.example.abe.dcpabe.key.PublicKey;
import com.example.abe.dcpabe.other.PublicKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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

    @GetMapping(path = "{attribute}")
    public PublicKey getPublicKeyByAttribute(
            @PathVariable("attribute") String attribute) {
        return publicKeysService.getPublicKeyByAttribute(attribute);
    }

    @GetMapping(path = "/all-attributes")
    public Set<String> getAllAttributes() {
        return publicKeysService.getAllAttributes();
    }

}
