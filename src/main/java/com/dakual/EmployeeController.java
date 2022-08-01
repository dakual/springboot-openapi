package com.dakual;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/api")
public class EmployeeController {
    private List<Employee> employees = createList();

    @Operation(summary = "Get all employees")
    @GetMapping("/employees")
    public List<Employee> allEmployees() {
        return employees;
    }

    @Operation(summary = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Employee", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "400", description = "Employee not found", content = @Content) })
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") String id) {
        Employee employee = null;
        for (Employee emp : employees) {
            if (emp.getEmpId().equals(id)) {
                employee = emp;
                break;
            }
        }

        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Delete employee by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> delEmployee(@PathVariable("id") String id) {
        Employee employee = null;
        for (Employee emp : employees) {
            if (emp.getEmpId().equals(id)) {
                employees.remove(emp);
                employee = emp;
                break;
            }
        }

        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee user) {
        employees.add(user);
        System.out.println(employees);
        return user;
    }

    private static List<Employee> createList() {
        List<Employee> tempEmployees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setName("emp1");
        emp1.setDesignation("manager");
        emp1.setEmpId("1");
        emp1.setSalary(3000);

        Employee emp2 = new Employee();
        emp2.setName("emp2");
        emp2.setDesignation("developer");
        emp2.setEmpId("2");
        emp2.setSalary(3000);
        tempEmployees.add(emp1);
        tempEmployees.add(emp2);
        return tempEmployees;
    }
}