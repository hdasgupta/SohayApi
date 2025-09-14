package com.wbfmh.sohay.security.controllers;

import com.wbfmh.sohay.security.data.dtos.UserOutputDto;
import com.wbfmh.sohay.security.data.dtos.UsersDto;
import com.wbfmh.sohay.security.data.entities.Users;
import com.wbfmh.sohay.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Endpoint to register a new user.
     * The password will be encrypted before being saved to the database.
     *
     * @param dto The user object containing username and password.
     * @return A Mono of a ResponseEntity with the created User.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<UserOutputDto> registerUser(@RequestBody UsersDto dto) {
        return userService.register(dto);
    }

    /**
     * A protected endpoint that returns the details of the currently authenticated user.
     * @param user A Mono that resolves to the authenticated User object.
     * @return A Mono of the authenticated User.
     */
    @GetMapping("/me")
    public Mono<Users> getAuthenticatedUser(@AuthenticationPrincipal Mono<Users> user) {
        return user;
    }
}
