package com.mingjeh.salarymanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mingjeh.salarymanagement.model.Employee;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String>  
{  
    String FILTER_EMPLOYEE_ON_SALARY_QUERY = 
    		"select b from Employee b where b.salary >= ?1 and b.salary <= ?2";
    
    
    @Query(FILTER_EMPLOYEE_ON_SALARY_QUERY)
    List<Employee> findBySalaryAndSalary(double minSalary, double maxSalary);
    
    @Query(FILTER_EMPLOYEE_ON_SALARY_QUERY)
    Page<Employee> findBySalaryAndSalary(double minSalary, double maxSalary, Pageable pageable);
}  