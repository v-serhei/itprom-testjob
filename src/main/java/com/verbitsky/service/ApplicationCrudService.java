package com.verbitsky.service;

import com.verbitsky.dto.BaseDto;

import java.util.List;

public interface ApplicationCrudService<T extends BaseDto> {
    T getObjectById(Long id);
    T saveObject(T objectToSave);
    T updateObject(Long id, T objectToUpdate);
    void deleteObjectById(Long id);
    List<T> getAllObjects();
}
