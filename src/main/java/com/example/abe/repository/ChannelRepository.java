package com.example.abe.repository;

import com.example.abe.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("SELECT c FROM Channel c WHERE c.topic = ?1")
    Optional<Channel> findChannelByTopic(String topic);

}
