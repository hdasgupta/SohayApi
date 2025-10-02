package com.wbfmh.sohay.security.services;
import com.wbfmh.sohay.security.data.dtos.UserOutputDto;
import com.wbfmh.sohay.security.data.dtos.UsersDto;
import com.wbfmh.sohay.security.data.entities.Users;
import com.wbfmh.sohay.security.data.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.wbfmh.sohay.security.consts.DBConstant.MAX_USER_VALIDITY_IN_YEAR;
import static com.wbfmh.sohay.security.consts.DBConstant.PASSWORD_ATTEMPT;

@Service
public class UserService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsernameAndEnabledIsTrueAndExpiredIsFalseAndAccountLockedIsFalseAndCredentialLockedIsFalseAndPasswordAttemptLessThan(username,PASSWORD_ATTEMPT)
                .cast(UserDetails.class)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)));
    }

    public Mono<UserOutputDto> register(UsersDto dto) {
        return userRepository.save(
                new Users(
                        null,
                        dto.getUsername(),
                        passwordEncoder.encode(dto.getPassword()),
                        dto.getRoles(),
                        LocalDate.now().plusYears(MAX_USER_VALIDITY_IN_YEAR),
                        0,
                        true,
                        false,
                        false,
                        false
                )
        ).map(userDetails -> {
            return new UserOutputDto(
                    userDetails.getUsername(),
                    userDetails.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .toArray(String[]::new)
            );
        });
    }
}
