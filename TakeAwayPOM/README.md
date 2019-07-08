# TakeAway Assignment
This is a Page Object Model with Page factory features 

Setup required to run the project:
1. JDK 8
2. Eclipse IDE, Neon or any higher version
3. Maven and TestNG plugins in Eclipse 
4. Chrome browser
5. Access to Gmail in a machine where run the scripts
6. User credentials for Gmail: automationsightlineconsilio@gmail.com and pwd is : consilio@123(test account) 

Steps  To run the scripts
-
 Its a maven project
  
 1. Build the project in eclipse
  
 2. Refresh the project post build
  
 3. run the testng.xml
  
 4. Check logs in :  src/main/java/logs/execution.logs
 
 5. Refresh test-output folder under project and check HTML report for results, under \test-output\emailable-report.html
 
 6. Screenshots of failed scripts can be found in folder : TakeAwayPOM/Screenshots/
 
## I have mainly focused on following factors during scripting

1. Using Page Object Module(POM) with page factory framework. Created required pages to write scripts.
2. Added screenshot for failures : path to screenshots is - check folder 'Screenshots' in work space
3. Used XMLs for environment data( like URL and user credentials ) and test data like (first name, last name and email inputs during account creation)
4. Used asserts to validate  test output.
5. Used Log4j for logs

##What we could improve
1. Parallel execution
2. Adding graphs or pie chart for execution report, adding extended report
3. Make projects works for firefox, IE and other browsers 



Thanks
Suresh Bavihalli
