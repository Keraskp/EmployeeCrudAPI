package com.nic.employee.controller;

import com.nic.employee.model.Employee;
import com.nic.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/employees")
	public String createNewEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return "New Employee Joined";
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(empList::add);
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}

	@GetMapping("/employees/{empid}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long empid) {
		Optional<Employee> emp = employeeRepository.findById(empid);
		if (emp.isPresent()) return new ResponseEntity<Employee>(emp.get(), HttpStatus.FOUND);
		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/employees/{empid}")
	public String updateEmployeeById(@PathVariable long empid, @RequestBody Employee employee){
		Optional<Employee> emp = employeeRepository.findById(empid);
		if (emp.isPresent()){
			Employee existEmp = emp.get();
			existEmp.setEmp_age(employee.getEmp_age());
			existEmp.setEmp_city(employee.getEmp_city());
			existEmp.setEmp_name(employee.getEmp_name());
			existEmp.setEmp_salary(employee.getEmp_salary());
			employeeRepository.save(existEmp);
			return "Employee Details of empid: " + empid + " updated";
		}
		return "Employee Details doesn't exist";
	}

	@DeleteMapping("/employees/{empid}")
	public String deleteEmployeeById(@PathVariable Long empid){
		Optional <Employee> emp = employeeRepository.findById(empid);
		if(emp.isPresent()){
			employeeRepository.deleteById(empid);
			return "Employee Deleted Successfully";
		}
		return "Employee Not Found!";
	}

	@DeleteMapping("/employees")
	public String deleteAllEmployees(){
		employeeRepository.deleteAll();
		return "All Employees Deleted Successfully";
	}

}
