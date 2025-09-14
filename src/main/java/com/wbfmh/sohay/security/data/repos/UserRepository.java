package com.wbfmh.sohay.security.data.repos;

import com.wbfmh.sohay.security.data.entities.Users;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserRepository extends ReactiveCrudRepository<Users, Long> {
    Mono<Users> findByUsername(String username);
}

