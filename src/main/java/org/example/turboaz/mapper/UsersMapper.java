package org.example.turboaz.mapper;

import org.apache.catalina.User;
import org.example.turboaz.dto.RegisterFinishDto;
import org.example.turboaz.dto.UserResponseDtoPrivate;
import org.example.turboaz.dto.UserResponseDtoPublic;
import org.example.turboaz.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UserResponseDtoPrivate toDtoPrivate(Users user);
    UserResponseDtoPublic toDtoPublic(Users user);
    Users toEntity(RegisterFinishDto finishDto);
}
