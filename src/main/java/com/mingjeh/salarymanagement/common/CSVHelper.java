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

	public static List<Employee> csvToEmployeeModel(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    		CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) 
	    {
	    	List<Employee> employees = new ArrayList<Employee>();
	    	List<CSVRecord> csvRecords = csvParser.getRecords();
	    	
	    	for (CSVRecord csvRecord : csvRecords) {
	    		Employee employee = new Employee(
		              csvRecord.get("id"),
		              csvRecord.get("name"),
		              csvRecord.get("login"),
		              Double.parseDouble(csvRecord.get("salary"))
	            );
	
    	  		employees.add(employee);
	      }
	
	      return employees;
	    } catch (IOException ex) {
	      throw new RuntimeException("fail to parse CSV file: " + ex.getMessage());
	    }
	}
}
