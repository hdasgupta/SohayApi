package com.wbfmh.sohay.controllers;

import com.wbfmh.sohay.data.dtos.DoctorsDto;
import com.wbfmh.sohay.data.entities.Doctors;
import com.wbfmh.sohay.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Doctors> create(@RequestBody DoctorsDto doctors) {
        return doctorService.create(doctors);
    }
}
