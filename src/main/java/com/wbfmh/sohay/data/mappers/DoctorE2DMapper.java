package com.wbfmh.sohay.data.mappers;

import com.wbfmh.sohay.data.dtos.DoctorsDto;
import com.wbfmh.sohay.data.entities.Doctors;
import org.springframework.stereotype.Component;

@Component
public class DoctorE2DMapper {
    public DoctorsDto map(Doctors doctors) {
        return new DoctorsDto(
                doctors.getId(),
                doctors.getName()
        );
    }
}
