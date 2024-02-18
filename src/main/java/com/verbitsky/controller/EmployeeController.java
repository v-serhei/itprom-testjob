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

import com.verbitsky.dto.EmployeeDto;
import com.verbitsky.service.ApplicationCrudService;

import java.util.List;

@RestController
public class EmployeeController {

    private final ApplicationCrudService<EmployeeDto> employeeService;

    public EmployeeController(ApplicationCrudService<EmployeeDto> employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(employeeService.getObjectById(id));
    }

    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllObjects());
    }

    @PostMapping(value = "/employees",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employee) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.saveObject(employee));
    }

    @PutMapping(value = "/employees/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<EmployeeDto> updateEmployee(@Valid @RequestBody EmployeeDto employee,
                                               @PathVariable Long id) {

        return ResponseEntity.ok(employeeService.updateObject(id, employee));
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteObjectById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
