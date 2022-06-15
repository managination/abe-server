package com.example.abe.controller;

import com.example.abe.DTO.PersonalKeysRequestDTO;
import com.example.abe.dcpabe.other.PersonalKeys;
import com.example.abe.service.PersonalKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/personalKeys")
public class PersonalKeysController {

    private final PersonalKeysService personalKeysService;

    @Autowired
    public PersonalKeysController(PersonalKeysService personalKeysService) {
        this.personalKeysService = personalKeysService;
    }

    @GetMapping
    public List<PersonalKeys> getPersonalKeys() {
        return personalKeysService.getPersonalKeys();
    }

    @PutMapping(path = "/request")
    public PersonalKeys addPersonalKey(@RequestBody PersonalKeysRequestDTO body) {
        return personalKeysService.addPersonalKey(body);
    }

    @GetMapping(path = "{personalKeysId}")
    public PersonalKeys getPersonalKeysById(@PathVariable("personalKeysId") Long personalKeysId) {
        return personalKeysService.getPersonalKeysById(personalKeysId);
    }

}
