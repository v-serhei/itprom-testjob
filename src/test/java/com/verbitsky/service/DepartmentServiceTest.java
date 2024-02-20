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
import com.verbitsky.entity.BaseEntity;
import com.verbitsky.entity.DepartmentEntity;
import com.verbitsky.exception.RepositoryException;
import com.verbitsky.exception.ResourceObjectNotFoundException;
import com.verbitsky.repo.DepartmentRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;

    private DepartmentDto validDepartmentDto;
    private DepartmentEntity validDepartmentEntity;

    @BeforeEach
    void setUp() {
        Set<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> converters = new HashSet<>(Arrays.asList(
                new ProfessionEntityDtoConverter(),
                new EmployeeEntityDtoConverter(),
                new DepartmentEntityDtoConverter()
        ));
        EntityDtoConverterManager converterManager = new SimpleEntityDtoConverterManager(converters);
        departmentService = new DepartmentService(converterManager, departmentRepository);


        validDepartmentDto = new DepartmentDto();
        validDepartmentDto.setId(1L);
        validDepartmentDto.setName("Test Department");
        validDepartmentEntity = new DepartmentEntity();
        validDepartmentEntity.setId(1L);
        validDepartmentEntity.setName("Test Department");
    }

    @Test
    void saveObject_ValidDepartmentDto_ReturnsSavedDto() {
        lenient().when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(departmentRepository.save(any())).thenReturn(validDepartmentEntity);

        DepartmentDto savedDto = departmentService.saveObject(validDepartmentDto);

        assertNotNull(savedDto);
        assertEquals(validDepartmentDto, savedDto);
    }

    @Test
    void saveObject_InvalidParentDepartmentId_ThrowsIllegalArgumentException() {
        DepartmentDto invalidDepartmentDto = new DepartmentDto();
        invalidDepartmentDto.setId(1L);
        invalidDepartmentDto.setName("Test Department");
        invalidDepartmentDto.setParentDepartmentId(2L); // Assuming parent department does not exist

        assertThrows(IllegalArgumentException.class, () -> departmentService.saveObject(invalidDepartmentDto));
    }

    @Test
    void updateObject_ValidIdAndDto_ReturnsUpdatedDto() {
        Long id = 1L;
        DepartmentDto updatedDto = new DepartmentDto();
        updatedDto.setId(id);
        updatedDto.setName("Updated Test Department");
        DepartmentEntity existingEntity = new DepartmentEntity();
        existingEntity.setId(id);
        existingEntity.setName("Test Department");

        when(departmentRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(departmentRepository.save(any())).thenReturn(existingEntity);

        DepartmentDto resultDto = departmentService.updateObject(id, updatedDto);

        assertNotNull(resultDto);
        assertEquals(updatedDto, resultDto);
    }

    @Test
    void updateObject_InvalidId_ThrowsResourceObjectNotFoundException() {
        Long invalidId = 999L;
        DepartmentDto updatedDto = new DepartmentDto();
        updatedDto.setId(invalidId);
        updatedDto.setName("Updated Test Department");

        assertThrows(ResourceObjectNotFoundException.class, () -> departmentService.updateObject(invalidId, updatedDto));
    }

    @Test
    void deleteObjectById_ValidId_NoExceptionThrown() {
        Long id = 1L;
        doNothing().when(departmentRepository).deleteById(id);

        assertDoesNotThrow(() -> departmentService.deleteObjectById(id));
    }

    @Test
    void deleteObjectById_InvalidId_ThrowsRepositoryException() {
        Long invalidId = 999L;
        doThrow(RepositoryException.class).when(departmentRepository).deleteById(invalidId);

        assertThrows(RepositoryException.class, () -> departmentService.deleteObjectById(invalidId));
    }
}
