package com.verbitsky.service;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.verbitsky.converter.EntityDtoConverterManager;
import com.verbitsky.dto.EmployeeDto;
import com.verbitsky.entity.DepartmentEntity;
import com.verbitsky.entity.EmployeeEntity;
import com.verbitsky.entity.ProfessionEntity;
import com.verbitsky.exception.ResourceObjectNotFoundException;
import com.verbitsky.repo.DepartmentRepository;
import com.verbitsky.repo.EmployeeRepository;
import com.verbitsky.repo.ProfessionRepository;

import java.util.Objects;

@Service
public class EmployeeService
        extends AbstractApplicationService<EmployeeDto, EmployeeEntity, EmployeeRepository> {

    private final EntityDtoConverterManager converterManager;
    private final DepartmentRepository departmentRepository;
    private final ProfessionRepository professionRepository;

    public EmployeeService(EntityDtoConverterManager converterManager,
                           EmployeeRepository repository,
                           DepartmentRepository departmentRepository,
                           ProfessionRepository professionRepository) {

        super(repository);
        this.converterManager = converterManager;
        this.departmentRepository = departmentRepository;
        this.professionRepository = professionRepository;
    }

    @Override
    protected EmployeeEntity dtoToEntity(EmployeeDto dto) {
        return converterManager.convertToEntity(dto, EmployeeEntity.class);
    }

    @Override
    protected EmployeeDto entityToDto(EmployeeEntity entity) {
        return converterManager.convertToDto(entity, EmployeeDto.class);
    }

    @Override
    public EmployeeDto saveObject(EmployeeDto objectToSave) {
        DepartmentEntity department =
                converterManager.convertToEntity(objectToSave.getDepartment(), DepartmentEntity.class);
        ProfessionEntity profession =
                converterManager.convertToEntity(objectToSave.getProfession(), ProfessionEntity.class);

        if (isDepartmentValid(department) && isProfessionValid(profession)) {
            return super.saveObject(objectToSave);
        } else {
            throw new IllegalArgumentException(
                    "Provided employee object is not valid: check department and profession");
        }
    }

    @Override
    protected EmployeeEntity updateEntityFields(EmployeeEntity entity, EmployeeDto dto) {
        updateProfession(entity, dto);
        updateDepartment(entity, dto);

        updateMiddleName(entity, dto);
        updateNote(entity, dto);

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());

        return entity;
    }

    private static void updateNote(EmployeeEntity entity, EmployeeDto dto) {
        String note = dto.getNote();
        if (Objects.nonNull(note)) {
            entity.setNote(note);
        }
    }

    private static void updateMiddleName(EmployeeEntity entity, EmployeeDto dto) {
        String middleName = dto.getMiddleName();
        if (StringUtils.isNotBlank(middleName)) {
            entity.setMiddleName(middleName);
        }
    }

    private void updateProfession(EmployeeEntity entity, EmployeeDto dto) {
        ProfessionEntity professionToUpdate =
                converterManager.convertToEntity(dto.getProfession(), ProfessionEntity.class);

        if (isProfessionValid(professionToUpdate)) {
            entity.setProfession(converterManager.convertToEntity(dto.getProfession(), ProfessionEntity.class));
        } else {
            throw new ResourceObjectNotFoundException("Can't update employee: Profession not found. Create profession first");
        }
    }

    private void updateDepartment(EmployeeEntity entity, EmployeeDto dto) {
        DepartmentEntity departmentToUpdate =
                converterManager.convertToEntity(dto.getDepartment(), DepartmentEntity.class);

        if (isDepartmentValid(departmentToUpdate)) {
            entity.setDepartment(converterManager.convertToEntity(dto.getDepartment(), DepartmentEntity.class));
        } else {
            throw new ResourceObjectNotFoundException("Can't update employee: Department not found. Create department first");
        }
    }

    private boolean isProfessionValid(ProfessionEntity professionToUpdate) {
        return professionRepository
                .findById(professionToUpdate.getId())
                .map(professionEntity -> professionEntity.equals(professionToUpdate))
                .orElse(false);
    }

    private boolean isDepartmentValid(DepartmentEntity department) {
        return departmentRepository
                .findById(department.getId())
                .map(departmentEntity -> departmentEntity.equals(department))
                .orElse(false);
    }
}