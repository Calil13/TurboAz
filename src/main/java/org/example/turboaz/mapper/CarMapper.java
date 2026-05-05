package org.example.turboaz.mapper;

import org.example.turboaz.dto.CarCreateRequest;
import org.example.turboaz.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toEntity(CarCreateRequest carCreateRequest);
}
