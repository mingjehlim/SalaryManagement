package com.mingjeh.salarymanagement.model;
import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.Id;  
import javax.persistence.Table;  

@Entity  
@Table
public class Employee {
	// Primary key  
	@Id  
	@Column  
	private String id;  
	@Column  
	private String name;  
	@Column  
	private String login;  
	@Column  
	private double salary;
	
	public Employee() {
		
	}
	
	public Employee(String id, String name, String login, double salary) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.salary = salary;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	} 
}