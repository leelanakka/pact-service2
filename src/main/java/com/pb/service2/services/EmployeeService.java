package com.pb.service2.services;

import com.pb.service2.dto.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee get(Long id);

    public List<Employee> getAll();

    public EmployeeService setBaseUrl(String baseUrl);
}
