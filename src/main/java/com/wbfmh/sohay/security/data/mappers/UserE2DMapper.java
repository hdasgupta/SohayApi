package com.wbfmh.sohay.security.data.mappers;

import com.wbfmh.sohay.security.data.dtos.UsersDto;
import com.wbfmh.sohay.security.data.entities.Users;
import org.springframework.stereotype.Component;

@Component
public class UserE2DMapper {
    public UsersDto map(Users dto) {
        return new UsersDto(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRoles()
        );
    }
}
