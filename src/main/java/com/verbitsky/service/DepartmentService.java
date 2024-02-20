package com.verbitsky.service;

import org.springframework.stereotype.Service;

import com.verbitsky.converter.EntityDtoConverterManager;
import com.verbitsky.dto.DepartmentDto;
import com.verbitsky.entity.DepartmentEntity;
import com.verbitsky.repo.DepartmentRepository;

import java.util.Objects;

@Service
public class DepartmentService
        extends AbstractApplicationService<DepartmentDto, DepartmentEntity, DepartmentRepository> {

    private final EntityDtoConverterManager converterManager;

    public DepartmentService(EntityDtoConverterManager converterManager, DepartmentRepository repository) {
        super(repository);
        this.converterManager = converterManager;
    }

    @Override
    protected DepartmentEntity dtoToEntity(DepartmentDto dto) {
        return converterManager.convertToEntity(dto, DepartmentEntity.class);
    }

    @Override
    protected DepartmentDto entityToDto(DepartmentEntity entity) {
        return converterManager.convertToDto(entity, DepartmentDto.class);
    }

    @Override
    public DepartmentDto saveObject(DepartmentDto objectToSave) {
        Long parentDepartmentId = objectToSave.getParentDepartmentId();
        if (parentDepartmentId == null || repository.findById(parentDepartmentId).isPresent()) {
            return super.saveObject(objectToSave);
        } else {
            throw new IllegalArgumentException("Can't save department: wrong parent department id");
        }
    }

    @Override
    protected DepartmentEntity updateEntityFields(DepartmentEntity entity, DepartmentDto dto) {
        entity.setName(dto.getName());
        entity.setNote(dto.getNote());

        updateParentDepartmentId(entity, dto);

        return entity;
    }

    private void updateParentDepartmentId(DepartmentEntity entity, DepartmentDto dto) {
        Long parentDepartmentId = dto.getParentDepartmentId();
        if (Objects.nonNull(parentDepartmentId)) {
            repository.findById(parentDepartmentId).ifPresentOrElse(
                    department -> entity.setParentDepartmentId(department.getId()),
                    () -> {
                        throw new IllegalArgumentException("Can't update department: parent department doesn't exists");
                    });
        } else {
            entity.setParentDepartmentId(null);
        }
    }
}