package org.example.turboaz.mapper;

import org.example.turboaz.dto.CarCreateRequest;
import org.example.turboaz.dto.CarResponseDto;
import org.example.turboaz.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarResponseDto toDto(Car car);
    Car toEntity(CarCreateRequest carCreateRequest);
}
