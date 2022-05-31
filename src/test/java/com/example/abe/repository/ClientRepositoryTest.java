package com.example.abe.repository;

import com.example.abe.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindClientByEmail() {

        // given
        String email = "bob@mail.com";
        Client bob = new Client(
                "Bob",
                email
        );
        underTest.save(bob);

        // when
        boolean expected = underTest.findClientByEmail(email).isPresent();

        // then
        assertThat(expected).isTrue();

    }

    @Test
    void itShouldNotFindClientByEmail() {

        // given
        String email = "bob@mail.com";

        // when
        boolean expected = underTest.findClientByEmail(email).isPresent();

        // then
        assertThat(expected).isFalse();

    }

}