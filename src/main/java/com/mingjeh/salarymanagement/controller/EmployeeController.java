package com.mingjeh.salarymanagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.PutMapping;  
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mingjeh.salarymanagement.common.CSVHelper;
import com.mingjeh.salarymanagement.model.Employee;
import com.mingjeh.salarymanagement.service.EmployeeService;  

@RestController
@RequestMapping(path = "/users")
public class EmployeeController {
	@Autowired  
	EmployeeService employeeService;
	
	// GET /users
	@GetMapping(path = "")
	public ResponseEntity<Map<String, Object>> getUsers(
			@RequestParam(value = "minSalary") double minSalary,
			@RequestParam(value = "maxSalary") double maxSalary,
			@RequestParam(value = "offset") int offset,
			@RequestParam(value = "limit") int limit,
			@RequestParam(value = "sort") String sort
			) 
	{
		try {
			List<Employee> employees = new ArrayList<Employee>();
			List<String> sortList = new ArrayList<String>();
			
			Sort.Direction sortDirection = Sort.Direction.ASC;
			
			if (sort.startsWith("-")) {
				// Desc
				sortDirection = Sort.Direction.DESC;
				sortList.add(sort.replace("-", ""));
			}
			else if (sort.startsWith(" ")) {
				// Asc
				sortList.add(sort.replace(" ", ""));
			}
			
			// Query with filter
			employees = employeeService.getCustomerDataAsPageWithFilteringAndSorting(minSalary, maxSalary, offset, limit, sortList, sortDirection.toString());
			
			// Construct response
			Map<String, Object> response = new HashMap<>();
			response.put("result", employees);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch(Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// POST /users/upload
	@PostMapping(path = "/upload")
	public ResponseEntity<Map<String, Object>> uploadUsers(@RequestBody MultipartFile file) {
		String message = "";
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (CSVHelper.hasCSVFormat(file)) {
				employeeService.saveEmployeeCSV(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				response.put("result", "success");
				response.put("message", message);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else {
				throw new Exception("This is not a CSV file");
			}
		}
		catch(Exception ex) {
			message = "Could not upload the file. Error: " + ex.getMessage();
			response.put("result", "failed");
	  		response.put("message", message);
	  		
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
