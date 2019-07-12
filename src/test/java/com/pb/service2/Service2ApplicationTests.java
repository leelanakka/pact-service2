package com.pb.service2;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.pb.service2.dto.Employee;
import com.pb.service2.services.EmployeeService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Service2ApplicationTests {


    @Autowired
    EmployeeService employeeService;

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("service1", this);

    @Pact(consumer = "service2")
    public RequestResponsePact createPactSingleEmployee(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name", "Bilboo")
                .integerType("id", 1L);
        return builder
                .given("employee exists with id 1")
                .uponReceiving("Get employee details")
                .path("/employees/1")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(body)
                .toPact();
    }

    @Pact(consumer = "service2")
    public RequestResponsePact createPactAllEmployee(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        DslPart body = PactDslJsonArray.arrayEachLike()
                .stringType("name", "Bilboo")
                .integerType("id", 1L)
                .closeObject();
        return builder
                .given("multiple employees exists")
                .uponReceiving("Get all employee details")
                .path("/employees")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(body)
                .toPact();
    }

    @Test
    @PactVerification(value = "service1", fragment = "createPactSingleEmployee")
    public void runTestSingleEmployee() {
        employeeService.setBaseUrl(mockProvider.getUrl());
        final Employee forEntity = employeeService.get(1L);
        assertEquals(forEntity.getId(), new Long(1));
        assertEquals(forEntity.getName(), "Bilbo");


    }

    @Test
    @PactVerification(value = "service1", fragment = "createPactAllEmployee")
    public void runTestAllEmployee() {
        employeeService.setBaseUrl(mockProvider.getUrl());
        final List<Employee> forEntity = employeeService.getAll();
        assertEquals(forEntity.get(0).getId(), new Long(1));
        assertEquals(forEntity.get(0).getName(), "Bilbo");
        assertEquals(1,1);

    }

}
