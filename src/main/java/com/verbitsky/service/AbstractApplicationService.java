package com.verbitsky.service;

import org.springframework.data.repository.CrudRepository;

import com.verbitsky.dto.BaseDto;
import com.verbitsky.entity.BaseEntity;
import com.verbitsky.exception.RepositoryException;
import com.verbitsky.exception.ResourceObjectNotFoundException;

import java.util.List;
import java.util.stream.StreamSupport;

abstract class AbstractApplicationService<D extends BaseDto, E extends BaseEntity, R extends CrudRepository<E, Long>>
        implements ApplicationCrudService<D> {
    protected final R repository;

    AbstractApplicationService(R repository) {
        this.repository = repository;
    }

    protected abstract E dtoToEntity(D dto);

    protected abstract D entityToDto(E entity);

    protected abstract E updateEntityFields(E entity, D objectToUpdate);

    @Override
    public D getObjectById(Long id) {
        return entityToDto(repository.findById(id)
                .orElseThrow(() -> new ResourceObjectNotFoundException("entity with id=%s not found".formatted(id))));
    }

    @Override
    public D saveObject(D objectToSave) {
        E entity = dtoToEntity(objectToSave);
        E saved;
        try {
            saved = repository.save(entity);
        } catch (Exception exception) {
            throw new RepositoryException("Can't proceed with entity save op. Wrong entity data");
        }
        return entityToDto(saved);
    }

    @Override
    public D updateObject(Long id, D objectToUpdate) {
        return repository.findById(id)
                .map(entity -> updateEntityFields(entity, objectToUpdate))
                .map(repository::save)
                .map(this::entityToDto)
                .orElseThrow(() -> new ResourceObjectNotFoundException(
                        "Update error: entity with id=%s not found".formatted(id)));
    }

    @Override
    public void deleteObjectById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception exception) {
            throw new RepositoryException("Can't proceed with entity delete op. Delete entity references first");
        }
    }

    @Override
    public List<D> getAllObjects() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::entityToDto)
                .toList();
    }
}
