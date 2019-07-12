package com.pb.service2.controllers;

import com.pb.service2.dto.Employee;
import com.pb.service2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BonusController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/pay/{id}")
    public String pay(@PathVariable Long id){
        Employee employee=employeeService.get(id);
        return "Paid Employee "+employee.getName();
    }

    @GetMapping("/pay")
    public String pay(){
        List<Employee> employees=employeeService.getAll();
        return "Paid Total "+ employees.size()+ " Employees";
    }
}
