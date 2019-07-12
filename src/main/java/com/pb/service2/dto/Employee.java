package com.pb.service2.dto;

import lombok.Data;

@Data
public class Employee {

    Long id;
    private String name;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }
}
