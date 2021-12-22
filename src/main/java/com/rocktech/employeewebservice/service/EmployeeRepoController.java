package com.rocktech.employeewebservice.service;

import com.rocktech.employeewebservice.exception_handler.EmployeeNotFound;
import com.rocktech.employeewebservice.model.Department;
import com.rocktech.employeewebservice.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class EmployeeRepoController {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @GetMapping("jpa/employees")
    List<Employee> getAll (){
        return employeeRepo.findAll();
    }

    @GetMapping("jpa/employees/{id}")
    public EntityModel<Employee> getEmployeeById (@PathVariable Long id) {
        Employee employee = employeeRepo.findById(id).get();
        EntityModel<Employee> model = EntityModel.of(employee);
        Link link = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                        this.getClass())
                                .getAll())
                .withRel("all-employees");
        model.add(link);
        return model;
    }

    @PostMapping("jpa/employees/create")
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee employee){
        Employee newEmployee = employeeRepo.save(employee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{name}")
                .buildAndExpand(newEmployee.getName())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("jpa/dept/create/{empId}")
    public ResponseEntity<Object> saveDepartment(@PathVariable Long empId,
                                                 @RequestBody Department department){
        Employee employee = employeeRepo.findById(empId).get();

        department.setEmployee(employee);
        departmentRepo.save(department);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{dept}")
                .buildAndExpand(department.getDept())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(path = "employee/delete/{id}")
    public void deleteEmployee(@PathVariable Long id){
        Employee employee =  employeeRepo.getById(id);
        employeeRepo.delete(employee);
    }
}
