package com.wbfmh.sohay.security.controllers;

import com.wbfmh.sohay.security.data.dtos.AuthRequest;
import com.wbfmh.sohay.security.data.dtos.AuthResponse;
import com.wbfmh.sohay.security.data.repos.UserRepository;
import com.wbfmh.sohay.security.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userRepository.findByUsernameAndEnabledIsTrueAndExpiredIsFalseAndAccountLockedIsFalseAndCredentialLockedIsFalse(ar.getUsername())
                .doOnEach(user-> {
                    if(Objects.requireNonNull(user.get()).getExpiryDate().isBefore(LocalDate.now())) {
                        Objects.requireNonNull(user.get()).setExpired(true);
                        userRepository.save(Objects.requireNonNull(user.get())).subscribe();
                    }
                    else if (!passwordEncoder.matches(ar.getPassword(), Objects.requireNonNull(user.get()).getPassword())) {
                        Objects.requireNonNull(user.get()).setPasswordAttempt(Objects.requireNonNull(user.get()).getPasswordAttempt()+1);
                        if (Objects.requireNonNull(user.get()).getPasswordAttempt()>=3) {
                            Objects.requireNonNull(user.get()).setCredentialLocked(true);
                            userRepository.save(Objects.requireNonNull(user.get())).subscribe();
                        }
                    }
                })
                .filter(userDetails -> passwordEncoder.matches(ar.getPassword(), userDetails.getPassword()))
                .flatMap(userDetails -> jwtUtil.generateToken(userDetails.getUsername()).map( token -> ResponseEntity.ok(new AuthResponse(token))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
