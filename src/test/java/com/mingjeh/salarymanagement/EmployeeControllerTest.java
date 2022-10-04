package com.mingjeh.salarymanagement;

import java.util.Map;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import com.mingjeh.salarymanagement.common.CSVHelper;
import com.mingjeh.salarymanagement.controller.EmployeeController;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
    WebApplicationContext wContext;
	
	@MockBean
    private CSVHelper csvHelper;
    
    @MockBean
    private EmployeeController controller;
    
    @Before
    public void setup() {
    	DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wContext);
        this.mvc = builder.build();
    }
    
    String csvBaseContent = "id,login,name,salary\r\n"
			+ "e0001,hpotter,Harry Potter,0\r\n"
			+ "e0002,rwesley,Ron Weasley??,19234.5\r\n"
			+ "e0003,ssnape,Severus Snape,4000";
    
    // getUsers Test
    @Test
    public void getUsers_whenGetUsers_thenReturnSuccess()
      throws Exception {
    	
    	// Mock Response
        when(controller.getUsers(Mockito.any(Double.class), Mockito.any(Double.class), Mockito.any(int.class), Mockito.any(int.class), Mockito.any(String.class)))
    		.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.OK));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("minSalary", "0");
		requestParams.add("maxSalary", "10000");
		requestParams.add("offset", "0");
		requestParams.add("limit", "30");
		requestParams.add("sort", " id");

        mvc.perform(MockMvcRequestBuilders.get("/users")
        		.params(requestParams))
        .andDo(print())
        .andExpect(status().isOk());
    }
    
    @Test
    public void getUsers_whenGetUsers_thenReturnException()
      throws Exception {
    	
    	// Mock Response
        when(controller.getUsers(Mockito.any(Double.class), Mockito.any(Double.class), Mockito.any(int.class), Mockito.any(int.class), Mockito.any(String.class)))
    		.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("minSalary", "0");
		requestParams.add("maxSalary", "10000");
		requestParams.add("offset", "-1");
		requestParams.add("limit", "30");
		requestParams.add("sort", " id");

        mvc.perform(MockMvcRequestBuilders.get("/users")
        		.params(requestParams))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }
    
    // uploadUsers Test
    @Test
    public void uploadUsers_whenUploadCSV_thenReturnSuccess()
      throws Exception {
    	MockMultipartFile file = new MockMultipartFile("test.csv", "", "text/csv", csvBaseContent.getBytes());

    	// Mock Response
        when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
    		.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.OK));
        when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void uploadUsers_whenUploadNonCSV_thenReturnException()
      throws Exception {
    	MockMultipartFile file = new MockMultipartFile("test.html", "", "text/html", csvBaseContent.getBytes());

    	// Mock Response
    	when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
			.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));
	    when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void uploadUsers_whenUploadDuplicateIdLogin_thenReturnException()
      throws Exception {
    	String csvProblematicContent = csvBaseContent + "\\r\\ne0003,ssnape,Severus Snape2,5000";
    	
    	MockMultipartFile file = new MockMultipartFile("test.csv", "", "text/csv", csvProblematicContent.getBytes());

    	// Mock Response
    	when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
			.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));
    	when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(false);
    	
        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void uploadUsers_whenUploadEmptyFile_thenReturnException()
      throws Exception {
    	String csvProblematicContent = "";
    	
    	MockMultipartFile file = new MockMultipartFile("test.csv", "", "text/csv", csvProblematicContent.getBytes());

    	// Mock Response
    	when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
			.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));
    	when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(false);
		
		mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void uploadUsers_whenUploadWrongColNum_thenReturnException()
      throws Exception {
    	String csvProblematicContent = csvBaseContent + "\\r\\ne0003,ssnape";
    	
    	MockMultipartFile file = new MockMultipartFile("test.csv", "", "text/csv", csvProblematicContent.getBytes());

    	// Mock Response
    	when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
			.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));
    	when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(false);
        
    	mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void uploadUsers_whenUploadNegativeSalary_thenReturnException()
      throws Exception {
    	String csvProblematicContent = csvBaseContent + "\\r\\ne0008,ssnape123,Severus Snape123,-1000";
    	
    	MockMultipartFile file = new MockMultipartFile("test.csv", "", "text/csv", csvProblematicContent.getBytes());

    	// Mock Response
    	when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
			.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));
		when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(false);
	    
        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void uploadUsers_whenUploadInvalidSalary_thenReturnException()
      throws Exception {
    	String csvProblematicContent = csvBaseContent + "\\r\\ne0008,ssnape123,Severus Snape123,20asfsdf33#$";
    	
    	MockMultipartFile file = new MockMultipartFile("test.csv", "", "text/csv", csvProblematicContent.getBytes());

    	// Mock Response
    	when(controller.uploadUsers(Mockito.any(MultipartFile.class)))
			.thenReturn(new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST));
		when(csvHelper.hasCSVFormat(Mockito.any(MultipartFile.class))).thenReturn(false);
	    
        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file("file", file.getBytes())
                .characterEncoding("UTF-8"))
        .andExpect(status().isBadRequest());
    }

}
