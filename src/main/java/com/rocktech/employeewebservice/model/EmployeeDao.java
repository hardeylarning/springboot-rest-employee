package com.rocktech.employeewebservice.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EmployeeDao {
    static List<Employee> employees = new ArrayList<>();

//    static {
//        employees.add(new Employee(1234, "Roqeeb", "roq@gmail.com"));
//        employees.add(new Employee(1235, "Akande", "ak@gmail.com"));
//        employees.add(new Employee(1236, "Adelani", "ade@gmail.com"));
//    }
    public List<Employee> getEmployees(){
        return employees;
    }

    public Employee getEmployeeById(int id) {
        return employees.stream().filter(
                emp -> emp.getId() == id).findAny().orElse(null);
    }

    public Employee createEmployee(Employee employee){
        employee.setId(Long.valueOf(employees.size()+1));
        employees.add(employee);
        return employee;
    }

    public Employee deleteEmployee(int id) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()){
            Employee employee = iterator.next();
            if (id== employee.getId()) {
                iterator.remove();
                return employee;
            }
        }
        return null;
    }
}
