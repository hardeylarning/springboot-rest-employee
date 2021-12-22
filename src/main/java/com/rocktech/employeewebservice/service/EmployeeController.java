package com.rocktech.employeewebservice.service;

import com.rocktech.employeewebservice.exception_handler.EmployeeNotFound;
import com.rocktech.employeewebservice.model.Employee;
import com.rocktech.employeewebservice.model.EmployeeDao;
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
public class EmployeeController {

    @Autowired()
    EmployeeDao employeeDao;

    @GetMapping("employees")
    public List<Employee> getAll() {
        return employeeDao.getEmployees();
    }

    @GetMapping("employees/{id}")
    public EntityModel<Employee> getEmployeeById (@PathVariable int id) {
        Employee employee = employeeDao.getEmployeeById(id);
        if (null == employee){
            throw new EmployeeNotFound("Employee Not Found");
        }
        EntityModel<Employee> model = EntityModel.of(employee);
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass())
                        .getAll())
                .withRel("all-employees");
        model.add(link);
        return model;
    }

    @PostMapping("employees/user")
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee employee){
       Employee newEmployee = employeeDao.createEmployee(employee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{name}")
                .buildAndExpand(newEmployee.getName())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(path = "employees/delete/{id}")
    public void deleteEmployee(@PathVariable int id){
      Employee employee =  employeeDao.deleteEmployee(id);
        if (null == employee){
            throw new EmployeeNotFound("Employee Not Found");
        }
    }
}
