package com.example.abe.controller;

import com.example.abe.model.AuthorityRequestPayload;
import com.example.abe.model.Channel;
import com.example.abe.service.AuthorityService;
import com.example.abe.dcpabe.other.AuthorityKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping
    public List<AuthorityKeys> getAuthorityKeys() {
        return authorityService.getAuthorityKeys();
    }

    @PostMapping
    public void createAuthority(
            @RequestBody AuthorityRequestPayload body) {
        authorityService.createAuthority(body);
    }

    @DeleteMapping(path = "{authorityId}")
    public void deleteAuthority(
            @PathVariable("authorityId") Long authorityId,
            @RequestParam(required = false) String attribute) {
        authorityService.deleteAuthority(authorityId, attribute);
    }

    @PutMapping(path = "{authorityId}")
    public void updateAuthority(@PathVariable("authorityId") Long authorityId,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String[] attributes) {
        authorityService.updateAuthority(authorityId, name, attributes);
    }

    @GetMapping(path = "{authorityName}")
    public AuthorityKeys AuthorityKeysByName(@PathVariable("authorityName") String authorityName) {
        return authorityService.AuthorityKeysByName(authorityName);
    }

}
