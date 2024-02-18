package com.verbitsky.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.verbitsky.dto.EmployeeDto;
import com.verbitsky.entity.EmployeeEntity;

import java.util.Set;

@Component
public class EmployeeEntityDtoConverter implements EntityDtoConverter<EmployeeDto, EmployeeEntity>{
    @Override
    public EmployeeEntity convertToEntity(@NonNull EmployeeDto dto) {
        return EmployeeMapper.INSTANCE.toEntity(dto);
    }

    @Override
    public EmployeeDto convertToDto(@NonNull EmployeeEntity entity) {
        return EmployeeMapper.INSTANCE.toDto(entity);
    }

    @Override
    public Set<Class<?>> getSupportedTypes() {
        return Set.of(EmployeeDto.class, EmployeeEntity.class);
    }

    @Mapper
    interface EmployeeMapper {
        EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "note", source = "note")
        @Mapping(target = "profession", source = "profession")
        @Mapping(target = "department", source = "department")
        EmployeeEntity toEntity(EmployeeDto dto);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "note", source = "note")
        @Mapping(target = "profession", source = "profession")
        @Mapping(target = "department", source = "department")
        EmployeeDto toDto(EmployeeEntity entity);
    }
}
