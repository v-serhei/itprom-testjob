package com.verbitsky.converter;

import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Set;

@SuppressWarnings("unused")
public interface EntityDtoConverter<S, T> {
    T convertToEntity(@NonNull S dto);

    S convertToDto(@NonNull T entity);

    Set<Class<?>> getSupportedTypes();

    default boolean supports(Class<?> source, Class<?> target) {
        return getSupportedTypes().containsAll(Arrays.asList(source, target));
    }
}