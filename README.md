# SalaryManagement

Pre-requisite - the following should be present in the computer:
- Java JDK
- Spring 
- Maven
- NodeJs
- Angular

# Steps to run the application:
1. Download the github folder
2. Use a terminal program to start the Back end and front end of the application (See commands below). Open a browser and navigate to http://localhost:4200 to start using the application
3. User Story 1 can be viewed in the "Upload employees" page. You can start to upload some employees. By default the application has no employees.
4. User Story 2 can be viewed in the "View employees" page
5. Automated testing can be done to the Back end endpoints, hosted at url http://localhost:8080

Back End
1. At project root folder (```/SalaryManagement```), run command : ```mvn spring-boot:run```

Front End
1. At project root folder (```/SalaryManagement```), run command : ```cd angular-site```
2. In ```/angular-site``` folder, run command : ```ng serve```