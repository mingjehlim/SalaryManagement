package com.mingjeh.salarymanagement.service;
import java.io.IOException;
import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mingjeh.salarymanagement.common.CSVHelper;
import com.mingjeh.salarymanagement.model.Employee;
import com.mingjeh.salarymanagement.repository.EmployeeRepository;  

@Service  
public class EmployeeService {

	@Autowired  
	EmployeeRepository employeeRepository;  
	
	@Autowired  
	CSVHelper csvHelper;
	
	// Generic methods
	public List<Employee> getAllUsers() {
		List<Employee> employees = new ArrayList<Employee>();  
		employeeRepository.findAll().forEach(e -> employees.add(e));  
		return employees;
	}
	
	public Employee getUserById(String id)   
	{  
		return employeeRepository.findById(id).get();  
	}  

	public void save(Employee employee) {
		employeeRepository.save(employee);		
	}
	
	public void delete(String id)   
	{  
		employeeRepository.deleteById(id);  
	}  
	
	public void update(Employee employee, String employeeId)   
	{  
		employeeRepository.save(employee); 
	}  
	// End Generic methods
	
	public List<Employee> getCustomerDataAsPageWithFilteringAndSorting(double minSalary, double maxSalary, int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        Page employeePage = employeeRepository.findBySalaryAndSalary(minSalary, maxSalary, pageable);
        
        List<Employee> employeeList = employeePage.toList();
        return employeeList;
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
    
    public void saveEmployeeCSV(MultipartFile file) throws Exception {
		try {
			List<Employee> employees = csvHelper.csvToEmployeeModel(file.getInputStream());
			employeeRepository.saveAll(employees);
        } catch (DataIntegrityViolationException e) {
        	throw new Exception("Unable to upload and save data due to duplicated values for id or login");
        } catch (Exception e) {
        	throw e;
        }
    }
}
