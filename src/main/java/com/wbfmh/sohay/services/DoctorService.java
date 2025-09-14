package com.wbfmh.sohay.services;

import com.wbfmh.sohay.data.dtos.DoctorsDto;
import com.wbfmh.sohay.data.entities.Doctors;
import com.wbfmh.sohay.data.mappers.DoctorD2EMapper;
import com.wbfmh.sohay.data.mappers.DoctorE2DMapper;
import com.wbfmh.sohay.data.repos.DoctorRepository;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorE2DMapper doctorE2DMapper;

    @Autowired
    private DoctorD2EMapper doctorD2EMapper;

    public Mono<Doctors> create(DoctorsDto dto) {
        Driver d;
        return doctorRepository.save(
                doctorD2EMapper.map(dto)
        );
    }
}
