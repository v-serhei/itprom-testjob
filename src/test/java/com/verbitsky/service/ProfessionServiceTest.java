package com.verbitsky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.verbitsky.converter.DepartmentEntityDtoConverter;
import com.verbitsky.converter.EmployeeEntityDtoConverter;
import com.verbitsky.converter.EntityDtoConverter;
import com.verbitsky.converter.EntityDtoConverterManager;
import com.verbitsky.converter.ProfessionEntityDtoConverter;
import com.verbitsky.converter.SimpleEntityDtoConverterManager;
import com.verbitsky.dto.BaseDto;
import com.verbitsky.dto.ProfessionDto;
import com.verbitsky.entity.BaseEntity;
import com.verbitsky.entity.ProfessionEntity;
import com.verbitsky.repo.ProfessionRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessionServiceTest {
    @Mock
    private ProfessionRepository professionRepository;
    @InjectMocks
    private ProfessionService professionService;
    private ProfessionEntity validEntity;

    @BeforeEach
    void setUp() {
        Set<EntityDtoConverter<? extends BaseDto, ? extends BaseEntity>> converters = new HashSet<>(Arrays.asList(
                new ProfessionEntityDtoConverter(),
                new EmployeeEntityDtoConverter(),
                new DepartmentEntityDtoConverter()
        ));
        EntityDtoConverterManager converterManager = new SimpleEntityDtoConverterManager(converters);
        professionService = new ProfessionService(professionRepository, converterManager);

        validEntity = new ProfessionEntity();
        validEntity.setId(1L);
        validEntity.setName("Software Engineer");
    }
    @Test
    void saveObject_ValidProfessionDto_ReturnsSavedDto() {
        ProfessionDto validProfessionDto = new ProfessionDto();
        validProfessionDto.setId(1L);
        validProfessionDto.setName("Software Engineer");

        when(professionRepository.save(any())).thenReturn(validEntity);

        ProfessionDto savedDto = professionService.saveObject(validProfessionDto);

        System.out.println(savedDto);

        assertNotNull(savedDto);
        assertEquals(validProfessionDto, savedDto);
    }
}