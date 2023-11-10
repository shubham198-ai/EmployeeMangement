package com.EmployeeManagementSystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EmployeeManagementSystem.Exception.ResourceNotFound;
import com.EmployeeManagementSystem.model.Employee;
import com.EmployeeManagementSystem.repository.EmployeeRepo;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class EmployeeController {
@Autowired
private EmployeeRepo employeeRepo;
@GetMapping("/employees")
public List<Employee>getallEmployees(){
	return employeeRepo.findAll();
}
@PostMapping("/create")
public Employee createEmployee(@RequestBody Employee employee) {
	return employeeRepo.save(employee);
}
@GetMapping("/{id}")
public ResponseEntity<Employee>getbyid(@PathVariable int id){
	Employee employee=employeeRepo.findById(id).orElseThrow(()->
	new ResourceNotFound("employee with id is not exists"+id));
	return ResponseEntity.ok(employee);	
	
}
@PutMapping("/{id}")
public ResponseEntity<Employee>updateEmployee(@PathVariable int id ,@RequestBody Employee employeeDetails ){
	Employee employee=employeeRepo.findById(id).orElseThrow(()->
	new ResourceNotFound("employee with id is not exists"+id));
	employee.setFirstName(employeeDetails.getFirstName());
	employee.setLastName(employeeDetails.getLastName());
	employee.setEmailId(employeeDetails.getEmailId());
Employee updatedEmployee=employeeRepo.save(employee);
return ResponseEntity.ok(updatedEmployee);
}
@DeleteMapping("/{id}")
public ResponseEntity<Map<String,Boolean>>deleteEmployee(@PathVariable int id){
	Employee employee=employeeRepo.findById(id).orElseThrow(()->
	new ResourceNotFound("employee with id is not exists"+id));
	employeeRepo.delete(employee);
	Map<String,Boolean>response=new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return ResponseEntity.ok(response);
}
}
