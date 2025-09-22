package com.wbfmh.sohay.security.data.mappers;

import com.wbfmh.sohay.security.data.dtos.UsersDto;
import com.wbfmh.sohay.security.data.entities.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserD2EMapper {
    public Users map(UsersDto dto) {
        return new Users(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRoles(),
                LocalDate.now().plusYears(1),
                0,
                true,
                false,
                false,
                false
        );
    }
}
