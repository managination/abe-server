package com.example.abe.service;

import com.example.abe.model.Client;
import com.example.abe.repository.ClientRepository;
import com.example.abe.repository.PersonalKeysRepository;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock private ClientRepository clientRepository;
    @Mock private PersonalKeysRepository personalKeysRepository;
//    private AutoCloseable autoCloseable;  // use instead of @ExtendWith annotation
    private ClientService underTest;

    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ClientService(
                clientRepository,
                personalKeysRepository
        );
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void canGetAllClients() {

        // when
        underTest.getClients();

        // then
        verify(clientRepository).findAll();

    }

    @Test
    void canAddNewEmail() {

        // given
        Client client = new Client(
                "Bob",
                "bob@mail.com"
        );

        // when
        underTest.addNewClient(client);

        // then
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());

        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(client);

    }

    @Test
    void willThrowWhenEmailIsTaken() {

        // given
        Client client = new Client(
                "Bob",
                "bob@mail.com"
        );

        given(clientRepository.findClientByEmail(anyString()).isPresent())  // remove isPresent in case of boolean
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addNewClient(client))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email " + client.getEmail() + " is taken");

        verify(clientRepository, never()).save(any());

    }



    @Test
    @Disabled
    void deleteClient() {
    }
}