package org.example.turboaz.mapper;

import org.example.turboaz.dto.CarRequestDto;
import org.example.turboaz.dto.CarResponseDto;
import org.example.turboaz.dto.CarUpdateDto;
import org.example.turboaz.entity.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarResponseDto toDto(Car car);
    Car toEntity(CarRequestDto carCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CarUpdateDto carUpdateDto, @MappingTarget Car car);
}
