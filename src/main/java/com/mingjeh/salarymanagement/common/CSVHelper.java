package com.mingjeh.salarymanagement.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.mingjeh.salarymanagement.model.Employee;

public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Title", "Description", "Published" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Employee> csvToEmployeeModel(InputStream is) throws Exception {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    		CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) 
	    {
	    	List<Employee> employees = new ArrayList<Employee>();
	    	List<CSVRecord> csvRecords = csvParser.getRecords();
	    	
	    	// Validate empty file
	    	if (csvRecords.isEmpty()) {
	    		throw new Exception("Csv file is empty");
	    	}
	    	
	    	
	    	for (CSVRecord csvRecord : csvRecords) {
	    		// Check comment
	    		if (csvRecord.get(0).startsWith("#")) {
	    			continue;
	    		}
	    		
	    		// Validate num of cols
	    		if (csvRecord.size() != 4) {
	    			throw new Exception("Csv file has wrong number of columns");
	    		}
	    		
	    		String id = csvRecord.get("id");
	    		String name = csvRecord.get("name");
				String login = csvRecord.get("login");
				Double salary = Double.parseDouble(csvRecord.get("salary"));
	    		
	    		if (salary < 0.0) {
	    			throw new Exception("One or more salaries is less than 0.0");
	    		}
	    		
	    		Employee employee = new Employee(id, name, login, salary);
	
    	  		employees.add(employee);
	      }
	
	      return employees;
	    } catch (NumberFormatException e) {
			throw new Exception("Invalid salary format");
		} catch (Exception e) {
			throw e;
		}
	}
}
