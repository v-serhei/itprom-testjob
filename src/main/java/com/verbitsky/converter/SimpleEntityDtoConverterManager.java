package com.verbitsky.converter;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.verbitsky.dto.BaseDto;
import com.verbitsky.entity.BaseEntity;
import com.verbitsky.exception.ApplicationException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SimpleEntityDtoConverterManager implements EntityDtoConverterManager  {
    private final Set<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> converters;

    public SimpleEntityDtoConverterManager(
            Collection<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> converters) {

        this.converters = new HashSet<>(converters);
    }

    @Override
    public <S extends BaseDto, T extends BaseEntity> T convertToEntity(
            @NonNull S source, @NonNull Class<T> targetClass) {

        EntityDtoConverter<S, T> entityDtoConverter = findConverter(source.getClass(), targetClass);
        return entityDtoConverter.convertToEntity(source);
    }

    @Override
    public <T extends BaseEntity, S extends BaseDto> S convertToDto(
            @NonNull T source, @NonNull Class<S> targetClass) {

        EntityDtoConverter<S, T> entityDtoConverter = findConverter(source.getClass(), targetClass);
        return entityDtoConverter.convertToDto(source);
    }

    @SuppressWarnings("unchecked")
    private <S extends BaseDto, T extends BaseEntity> EntityDtoConverter<S, T> findConverter(
            Class<?> sourceClass, Class<?> targetClass) {

        Optional<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> dtoConverter = converters
                .stream()
                .filter(converter -> converter.supports(sourceClass, targetClass))
                .findFirst();

        return dtoConverter
                .map(converter -> (EntityDtoConverter<S, T>) converter)
                .orElseThrow(() -> new ApplicationException("Converter wasn't found"));
    }
}
