# Medley of the Spring tutorials and guides

## Simple Payroll

**Description**
Simple application to maintain the set of candidates and employees. 
Use-cases:
- add a candidate
- hire a candidate
- dismiss an employee
All operations to test: ```src/main/resources/ManualApiTest.http```

**Notes**
- Spring Security: default password in the application settings and CSRF and CORS filter to ```localhost:4200```
- In-memory DB
- REST API on port 8080
- Environment variables:
  PAYROLL_API_PASS - password for a basic authentication call the API
  PAYROLL_GITHUB_CLIENTID - clientid for OAuth authentication with GitHub
  PAYROLL_GITHUB_CLIENT_SECRET - client secret for OAuth authentication with GitHub

**Run**
Standard IDEA run profile or CLI (JAVA_HOME should point to java 17 installation)
```
mvn build
java -jar ./target/SimplePayroll-0.0.1-SNAPSHOT.jar --server.port=8080
```

## Angular representation

**Description**
Simple web form that supports use-cases of the Simple payroll application. 

**Notes**
- Bootstrap.css

**Run (Windows)**
```
./mvnw generate-resources        # install node and npm locally
./npm install @angular/cli       # install angular cli
./ng build                       
./ng.cmd serve                   # run on localhost:4200
```

**TODO**
- Spring: set up the CORS properly
- Angular: implement registration/login functionality 
- Spring: implement a custom user repo and split access by user/namespaces
- Angular/Spring: implement OAuth authentication with GitHub
