package org.example.turboaz.mapper;

import org.example.turboaz.dto.UserResponseDto;
import org.example.turboaz.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UserResponseDto toDto(Users user);
}
