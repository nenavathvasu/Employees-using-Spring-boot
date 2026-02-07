package com.sathya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sathya.Model.Employee;
import com.sathya.Service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Save single employee
    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee emp) {
    		Employee saveEmp = service.saveEmployee(emp);
    	return ResponseEntity.status(HttpStatus.CREATED)
    						.header("info", "Saved successfully")
    						.body(saveEmp);
    }
    
    //save all 
    @PostMapping("/saveAll")
    public ResponseEntity<List<Employee>> saveAll(@RequestBody List<Employee> emp){
    	List<Employee> saveAll = service.SaveAllEmployee(emp);
    	return ResponseEntity.status(HttpStatus.CREATED)
    				.header("info", "Saved all Employees Successfully")
    				.body(saveAll);
    }

    
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmp(){
    	List<Employee> GetAllEmployee = service.getAllEmployees();
    	return ResponseEntity
    			.status(HttpStatus.OK)
    			.header("info", "Data Retriebed Successful")
    			.body(GetAllEmployee);
    }
    
    // Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = service.getEmployeeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("info", "Data retrieved successfully")
                .body(employee);
    }

    // Delete employee by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    //Delete All employees
    @DeleteMapping
    public ResponseEntity<Void> deleteAllEmployees() {
        service.deleteAllEmployees();
        return ResponseEntity.noContent().build();
    }


}
