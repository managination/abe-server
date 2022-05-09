package com.example.abe.authority;

import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.DCPABE;
import com.example.abe.publicKeys.PublicKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final PublicKeysService publicKeysService;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository,
                            PublicKeysService publicKeysService) {
        this.authorityRepository = authorityRepository;
        this.publicKeysService = publicKeysService;
    }

    public List<AuthorityKeys> getAuthorityKeys() {
        return authorityRepository.findAll();
    }

    public void addNewAuthority(AuthorityRequest body) {
        Optional<AuthorityKeys> optionalAuthority = authorityRepository
                .findAuthorityByName(body.getName());
        if (optionalAuthority.isPresent()) {
            throw new IllegalStateException("authority already exists");
        }
        AuthorityKeys authority = DCPABE.authoritySetup(body.getName(), gp, body.getAttributes());
        authorityRepository.save(authority);
        publicKeysService.addPublicKeys(authority); //add authority PKs to all PKs

    }

}
