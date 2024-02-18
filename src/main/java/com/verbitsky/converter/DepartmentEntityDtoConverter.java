package com.verbitsky.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.verbitsky.dto.DepartmentDto;
import com.verbitsky.entity.DepartmentEntity;

import java.util.Set;

@Component
class DepartmentEntityDtoConverter implements EntityDtoConverter<DepartmentDto, DepartmentEntity> {
    @Override
    public DepartmentEntity convertToEntity(@NonNull DepartmentDto dto) {
        return DepartmentMapper.INSTANCE.toEntity(dto);
    }

    @Override
    public DepartmentDto convertToDto(@NonNull DepartmentEntity entity) {
        return DepartmentMapper.INSTANCE.toDto(entity);
    }

    @Override
    public Set<Class<?>> getSupportedTypes() {
        return Set.of(DepartmentDto.class, DepartmentEntity.class);
    }

    @Mapper
    interface DepartmentMapper {
        DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "note", source = "note")
        DepartmentEntity toEntity(DepartmentDto dto);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "note", source = "note")
        DepartmentDto toDto(DepartmentEntity entity);
    }
}
