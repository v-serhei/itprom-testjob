package com.verbitsky.controller;


import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.verbitsky.dto.ProfessionDto;
import com.verbitsky.service.ApplicationCrudService;
import com.verbitsky.service.ProfessionService;

import java.util.List;

@RestController
class ProfessionController {
    private final ApplicationCrudService<ProfessionDto> professionService;

    ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping(value = "/professions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProfessionDto> getProfessionById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(professionService.getObjectById(id));
    }

    @GetMapping(value = "/professions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProfessionDto>> getAllProfessions() {
        return ResponseEntity.ok(professionService.getAllObjects());
    }

    @PostMapping(value = "/professions",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<ProfessionDto> saveProfession(@Valid @RequestBody ProfessionDto profession) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(professionService.saveObject(profession));
    }

    @PutMapping(value = "/professions/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<ProfessionDto> updateProfession(@Valid @RequestBody ProfessionDto profession,
                                                   @PathVariable Long id) {

        return ResponseEntity.ok(professionService.updateObject(id, profession));
    }

    @DeleteMapping("/professions/{id}")
    ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        professionService.deleteObjectById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
