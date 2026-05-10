package org.example.turboaz.mapper;

import org.example.turboaz.dto.auth.RegisterFinishDto;
import org.example.turboaz.dto.user.UserResponseDtoPrivate;
import org.example.turboaz.dto.user.UserResponseDtoPublic;
import org.example.turboaz.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UserResponseDtoPrivate toDtoPrivate(Users user);
    UserResponseDtoPublic toDtoPublic(Users user);
    Users toEntity(RegisterFinishDto finishDto);
}
