package com.example.abe.personalKeys;

import com.example.abe.dcpabe.other.PersonalKeys;
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

    @PutMapping(path = "{clientName}")
    public PersonalKeys addPersonalKey(@PathVariable("clientName") String clientName,
                                       @RequestParam Long authorityId,
                                       @RequestParam String attribute) {
        return personalKeysService.addPersonalKey(clientName, authorityId, attribute);
    }

}
