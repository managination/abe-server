package com.example.abe.authority;

import com.example.abe.dcpabe.key.PersonalKey;
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
    public void registerNewAuthority(
            @RequestBody AuthorityRequest body) {
        authorityService.addNewAuthority(body);
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

}
