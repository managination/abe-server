package com.example.abe.repository;

import com.example.abe.dcpabe.other.PersonalKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonalKeysRepository extends JpaRepository<PersonalKeys, Long> {

    @Query("SELECT p FROM PersonalKeys p WHERE p.userID = ?1")
    Optional<PersonalKeys> findPersonalKeysByName(String userID);

}
