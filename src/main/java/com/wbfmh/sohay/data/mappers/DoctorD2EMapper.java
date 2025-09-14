package com.wbfmh.sohay.data.mappers;

import com.wbfmh.sohay.data.dtos.DoctorsDto;
import com.wbfmh.sohay.data.entities.Doctors;
import org.springframework.stereotype.Component;

@Component
public class DoctorD2EMapper {
    public Doctors map(DoctorsDto doctors) {
        return new Doctors(
                doctors.getId(),
                doctors.getName()
        );
    }
}
