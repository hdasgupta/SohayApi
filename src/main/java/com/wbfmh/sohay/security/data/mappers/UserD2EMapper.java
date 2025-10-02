package com.wbfmh.sohay.security.data.mappers;

import com.wbfmh.sohay.security.data.dtos.UsersDto;
import com.wbfmh.sohay.security.data.entities.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.wbfmh.sohay.security.consts.DBConstant.MAX_USER_VALIDITY_IN_YEAR;

@Component
public class UserD2EMapper {
    public Users map(UsersDto dto) {
        return new Users(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRoles(),
                LocalDate.now().plusYears(MAX_USER_VALIDITY_IN_YEAR),
                0,
                true,
                false,
                false,
                false
        );
    }
}
