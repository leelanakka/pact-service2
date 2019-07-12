package com.pb.service2.services;

import com.pb.service2.dto.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeServiceImpl implements EmployeeService {
    String baseUrl = "http://localhost:9001";

    @Override
    public List<Employee> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(baseUrl + "/employees", HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        }).getBody();
    }

    @Override
    public Employee get(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseUrl + "/employees/" + id, Employee.class);
    }

    @Override
    public EmployeeService setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

}
