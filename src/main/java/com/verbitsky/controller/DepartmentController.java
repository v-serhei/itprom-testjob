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

import com.verbitsky.dto.DepartmentDto;
import com.verbitsky.service.ApplicationCrudService;

import java.util.List;

@RestController
class DepartmentController {
    private final ApplicationCrudService<DepartmentDto> departmentService;

    DepartmentController(ApplicationCrudService<DepartmentDto> departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/departments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(departmentService.getObjectById(id));
    }

    @GetMapping(value = "/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllObjects());
    }

    @PostMapping(value = "/departments",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<DepartmentDto> saveDepartment(@Valid @RequestBody DepartmentDto department) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentService.saveObject(department));
    }

    @PutMapping(value = "/departments/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<DepartmentDto> updateDepartment(@Valid @RequestBody DepartmentDto department,
                                                   @PathVariable Long id) {

        return ResponseEntity.ok(departmentService.updateObject(id, department));
    }

    @DeleteMapping(value = "/departments/{id}")
    ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteObjectById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
