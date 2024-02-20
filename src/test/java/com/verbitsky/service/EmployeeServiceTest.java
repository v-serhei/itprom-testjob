package com.verbitsky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.verbitsky.converter.DepartmentEntityDtoConverter;
import com.verbitsky.converter.EmployeeEntityDtoConverter;
import com.verbitsky.converter.EntityDtoConverter;
import com.verbitsky.converter.EntityDtoConverterManager;
import com.verbitsky.converter.ProfessionEntityDtoConverter;
import com.verbitsky.converter.SimpleEntityDtoConverterManager;
import com.verbitsky.dto.BaseDto;
import com.verbitsky.dto.DepartmentDto;
import com.verbitsky.dto.EmployeeDto;
import com.verbitsky.dto.ProfessionDto;
import com.verbitsky.entity.BaseEntity;
import com.verbitsky.entity.DepartmentEntity;
import com.verbitsky.entity.EmployeeEntity;
import com.verbitsky.entity.ProfessionEntity;
import com.verbitsky.repo.DepartmentRepository;
import com.verbitsky.repo.EmployeeRepository;
import com.verbitsky.repo.ProfessionRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ProfessionRepository professionRepository;
    private EmployeeService employeeService;
    private EmployeeDto validEmployeeDto;
    private EmployeeEntity validEmployeeEntity;

    private ProfessionDto professionDto;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        Set<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> converters = new HashSet<>(Arrays.asList(
                new ProfessionEntityDtoConverter(),
                new EmployeeEntityDtoConverter(),
                new DepartmentEntityDtoConverter()
        ));
        EntityDtoConverterManager converterManager = new SimpleEntityDtoConverterManager(converters);
        employeeService = new EmployeeService(converterManager,
                employeeRepository, departmentRepository, professionRepository);


        validEmployeeDto = new EmployeeDto();
        validEmployeeDto.setId(1L);
        validEmployeeDto.setFirstName("John");
        validEmployeeDto.setLastName("Doe");

        validEmployeeEntity = new EmployeeEntity();
        validEmployeeEntity.setId(1L);
        validEmployeeEntity.setFirstName("John");
        validEmployeeEntity.setLastName("Doe");

        DepartmentEntity validDepartmentEntity = new DepartmentEntity();
        validDepartmentEntity.setId(1L);
        validDepartmentEntity.setName("d");
        validDepartmentEntity.setNote("n");

        ProfessionEntity validProfessionEntity = new ProfessionEntity();
        validProfessionEntity.setId(1L);
        validProfessionEntity.setName("p");
        validProfessionEntity.setNote("n");

        departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setName("d");
        departmentDto.setNote("n");
        validEmployeeDto.setDepartment(departmentDto);

        professionDto = new ProfessionDto();
        professionDto.setId(1L);
        professionDto.setName("p");
        professionDto.setNote("n");
        validEmployeeDto.setProfession(professionDto);

        when(departmentRepository.findById(anyLong())).thenReturn(java.util.Optional.of(validDepartmentEntity));
        when(professionRepository.findById(anyLong())).thenReturn(java.util.Optional.of(validProfessionEntity));
    }

    @Test
    void saveObject_ValidEmployeeDto_ReturnsSavedDto() {
        when(employeeRepository.save(any())).thenReturn(validEmployeeEntity);
        EmployeeDto savedDto = employeeService.saveObject(validEmployeeDto);

        assertNotNull(savedDto);
        assertEquals(validEmployeeDto, savedDto);
    }

    @Test
    void updateObject_ValidIdAndDto_ReturnsUpdatedDto() {
        Long id = 1L;
        EmployeeDto updatedDto = new EmployeeDto();
        updatedDto.setId(id);
        updatedDto.setFirstName("Updated John");
        updatedDto.setDepartment(departmentDto);
        updatedDto.setProfession(professionDto);
        EmployeeEntity existingEntity = new EmployeeEntity();
        existingEntity.setId(id);
        existingEntity.setFirstName("John");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(employeeRepository.save(any())).thenReturn(existingEntity);

        EmployeeDto resultDto = employeeService.updateObject(id, updatedDto);

        assertNotNull(resultDto);
        assertEquals(updatedDto, resultDto);
    }
}