package com.example.abe.repository;

import com.example.abe.dcpabe.other.AuthorityKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<AuthorityKeys, Long> {

    @Query("SELECT a FROM AuthorityKeys a WHERE a.name = ?1")
    Optional<AuthorityKeys> findAuthorityByName(String name);

}
