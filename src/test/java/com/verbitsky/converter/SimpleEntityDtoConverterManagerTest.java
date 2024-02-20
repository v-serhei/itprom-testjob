package com.verbitsky.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.verbitsky.dto.BaseDto;
import com.verbitsky.dto.DepartmentDto;
import com.verbitsky.dto.EmployeeDto;
import com.verbitsky.dto.ProfessionDto;
import com.verbitsky.entity.BaseEntity;
import com.verbitsky.entity.DepartmentEntity;
import com.verbitsky.entity.EmployeeEntity;
import com.verbitsky.entity.ProfessionEntity;
import com.verbitsky.exception.ApplicationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleEntityDtoConverterManagerTest {

    private static SimpleEntityDtoConverterManager converterManager;

    @BeforeAll
    static void setUp() {
        Set<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> converters = new HashSet<>(Arrays.asList(
                new ProfessionEntityDtoConverter(),
                new EmployeeEntityDtoConverter(),
                new DepartmentEntityDtoConverter()
        ));
        converterManager = new SimpleEntityDtoConverterManager(converters);
    }

    @Test
    void testConvertToEntity_Positive() {
        // Test ProfessionDto to ProfessionEntity conversion
        ProfessionDto professionDto = new ProfessionDto();
        professionDto.setId(1L);
        professionDto.setNote("Test Profession");
        ProfessionEntity professionEntity = converterManager.convertToEntity(professionDto, ProfessionEntity.class);
        assertNotNull(professionEntity);
        assertEquals(professionDto.getId(), professionEntity.getId());
        assertEquals(professionDto.getNote(), professionEntity.getNote());

        // Test EmployeeDto to EmployeeEntity conversion
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setNote("Test Employee");
        EmployeeEntity employeeEntity = converterManager.convertToEntity(employeeDto, EmployeeEntity.class);
        assertNotNull(employeeEntity);
        assertEquals(employeeDto.getId(), employeeEntity.getId());
        assertEquals(employeeDto.getNote(), employeeEntity.getNote());

        // Test DepartmentDto to DepartmentEntity conversion
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setNote("Test Department");
        DepartmentEntity departmentEntity = converterManager.convertToEntity(departmentDto, DepartmentEntity.class);
        assertNotNull(departmentEntity);
        assertEquals(departmentDto.getId(), departmentEntity.getId());
        assertEquals(departmentDto.getNote(), departmentEntity.getNote());
    }

    @Test
    void testConvertToEntity_Negative() {
        // Test conversion with unsupported DTO class
        BaseDto unsupportedDto = new BaseDto() {
        };
        assertThrows(ApplicationException.class, () -> converterManager.convertToEntity(unsupportedDto, ProfessionEntity.class));
    }

    @Test
    void testConvertToDto_Positive() {
        // Test ProfessionEntity to ProfessionDto conversion
        ProfessionEntity professionEntity = new ProfessionEntity();
        professionEntity.setId(1L);
        professionEntity.setNote("Test Profession");
        ProfessionDto professionDto = converterManager.convertToDto(professionEntity, ProfessionDto.class);
        assertNotNull(professionDto);
        assertEquals(professionEntity.getId(), professionDto.getId());
        assertEquals(professionEntity.getNote(), professionDto.getNote());

        // Test EmployeeEntity to EmployeeDto conversion
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1L);
        employeeEntity.setNote("Test Employee");
        EmployeeDto employeeDto = converterManager.convertToDto(employeeEntity, EmployeeDto.class);
        assertNotNull(employeeDto);
        assertEquals(employeeEntity.getId(), employeeDto.getId());
        assertEquals(employeeEntity.getNote(), employeeDto.getNote());

        // Test DepartmentEntity to DepartmentDto conversion
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1L);
        departmentEntity.setNote("Test Department");
        DepartmentDto departmentDto = converterManager.convertToDto(departmentEntity, DepartmentDto.class);
        assertNotNull(departmentDto);
        assertEquals(departmentEntity.getId(), departmentDto.getId());
        assertEquals(departmentEntity.getNote(), departmentDto.getNote());
    }

    @Test
    void testConvertToDto_Negative() {
        // Test conversion with unsupported Entity class
        BaseEntity unsupportedEntity = new BaseEntity() {
        };
        assertThrows(ApplicationException.class, () -> converterManager.convertToDto(unsupportedEntity, ProfessionDto.class));
    }
}
