package com.verbitsky.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.verbitsky.dto.ProfessionDto;
import com.verbitsky.entity.ProfessionEntity;

import java.util.Set;

@Component
public class ProfessionEntityDtoConverter implements EntityDtoConverter<ProfessionDto, ProfessionEntity> {
    @Override
    public ProfessionEntity convertToEntity(@NonNull ProfessionDto dto) {
        return ProfessionMapper.INSTANCE.toEntity(dto);
    }

    @Override
    public ProfessionDto convertToDto(@NonNull ProfessionEntity entity) {
        return ProfessionMapper.INSTANCE.toDto(entity);
    }

    @Override
    public Set<Class<?>> getSupportedTypes() {
        return Set.of(ProfessionDto.class, ProfessionEntity.class);
    }

    @Mapper
    interface ProfessionMapper {
        ProfessionMapper INSTANCE = Mappers.getMapper(ProfessionMapper.class);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "note", source = "note")
        ProfessionEntity toEntity(ProfessionDto dto);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "note", source = "note")
        ProfessionDto toDto(ProfessionEntity entity);
    }
}
