package com.verbitsky.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.verbitsky.converter.EntityDtoConverterManager;
import com.verbitsky.dto.ProfessionDto;
import com.verbitsky.entity.ProfessionEntity;
import com.verbitsky.repo.ProfessionRepository;

@Slf4j
@Service
public class ProfessionService
        extends AbstractApplicationService<ProfessionDto, ProfessionEntity, ProfessionRepository> {

    private final EntityDtoConverterManager converterManager;

    ProfessionService(ProfessionRepository repository, EntityDtoConverterManager converterManager) {
        super(repository);
        this.converterManager = converterManager;
    }

    @Override
    protected ProfessionEntity dtoToEntity(ProfessionDto dto) {
        return converterManager.convertToEntity(dto, ProfessionEntity.class);
    }

    @Override
    protected ProfessionDto entityToDto(ProfessionEntity entity) {
        return converterManager.convertToDto(entity, ProfessionDto.class);
    }

    @Override
    protected ProfessionEntity updateEntityFields(ProfessionEntity entity, ProfessionDto dto) {
        entity.setName(dto.getName());
        entity.setNote(dto.getNote());

        return entity;
    }
}
