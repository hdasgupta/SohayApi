package com.wbfmh.sohay.security.data.repos;

import com.wbfmh.sohay.security.data.entities.Users;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<Users, Long> {
    Mono<Users> findByUsernameAndEnabledIsTrueAndExpiredIsFalseAndAccountLockedIsFalseAndCredentialLockedIsFalse(String username);
}

