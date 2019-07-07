# TakeAway Assignment

Setup required to run the project:
1. JDK 8
2. Eclipse IDE, neon or any higher version
3. Maven and TestNG plugins in Eclipse 
4. Chrome browser
5. Access to Gmail in a machine where run the scripts
6. Browser set up to access Gmail emails from API
7. User credentials for Gmail: automationsightlineconsilio@gmail.com and pwd is : consilio@123(test account) 

Steps  To run the scripts
-
 Its a maven project
  
 1. Import and Build the project in eclipse
  
 2. Refresh the project post build
  
 3. run the testng.xml
  
 4. Check logs in :  src/main/java/logs/execution.logs
 
 5. Check HTML report for results, under \test-output\emailable-report.html
 
## I have mainly focused on following factors during scripting

1. Using Page Object Module(POM) with page factory framework. Created required pages to write scripts.
2. Added screenshot for failures : path to screenshots is - check folder 'Screenshots' in work space
3. Used XMLs for environment data( like URL and user credentials ) and test data like (first name, last name and email inputs during account creation)
4. Used asserts to validate  test output.
5. Used Log4j for logs

##What I would have covered, if I had more time.
1. I would have covered P2 and negative test scenarios
2. Adding graphs or pie chart for results


Thanks
Suresh Bavihalli
