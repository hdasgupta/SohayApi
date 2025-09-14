package com.wbfmh.sohay.data.repos;

import com.wbfmh.sohay.data.entities.Doctors;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DoctorRepository extends ReactiveCrudRepository<Doctors, Long> {
    Flux<Doctors> findByNameContaining(String name);
}