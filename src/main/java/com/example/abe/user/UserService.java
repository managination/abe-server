package com.example.abe.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public List<User> getUsers() {
        return List.of(
                new User(
                        1L,
                        "Alice",
                        "alice@gmail.com",
                        List.of("authority creator", "boss")
                ),
                new User(2L,
                        "Bob",
                        "bob@gmail.com",
                        List.of("worker"))
        );
    }
}
