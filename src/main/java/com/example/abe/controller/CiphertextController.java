package com.example.abe.controller;

import com.example.abe.service.CiphertextService;
import com.example.abe.dcpabe.other.Ciphertext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ciphertext")
public class CiphertextController {

    private final CiphertextService cipherTextService;

    @Autowired
    public CiphertextController(CiphertextService cipherTextService) {
        this.cipherTextService = cipherTextService;
    }

    @GetMapping
    public List<Ciphertext> getCiphertext() {
        return cipherTextService.getCiphertext();
    }

    @PostMapping
    public Long createCiphertext(@RequestParam String policy,
                                 @RequestBody String text) {
        return cipherTextService.createCiphertextByString(policy, text);
    }

    @DeleteMapping(path = "{ciphertextId}")
    public void deleteCiphertext(@PathVariable("ciphertextId") Long ciphertextId) {
        cipherTextService.deleteCiphertext(ciphertextId);
    }

}
