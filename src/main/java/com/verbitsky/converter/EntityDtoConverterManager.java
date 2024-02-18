package com.verbitsky.converter;

import org.springframework.lang.NonNull;

import com.verbitsky.dto.BaseDto;
import com.verbitsky.entity.BaseEntity;


@SuppressWarnings("unused")
public interface EntityDtoConverterManager {
    <S extends BaseDto, T extends BaseEntity> T convertToEntity(
            @NonNull S source, @NonNull Class<T> targetClass);

    <T extends BaseEntity, S extends BaseDto> S convertToDto(
            @NonNull T source, @NonNull Class<S> targetClass);
}