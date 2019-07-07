package testScripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.DriverClass;
import automationLibrary.Log;
import junit.framework.Assert;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.Utility;


public class TA_01_AccountCreations{

	WebDriver driver;
	LoginPage lp; 
	Logger log;
	
	@BeforeClass
	 public void setup() throws ParseException, InterruptedException, IOException{
			Input in =new Input();
			in.loadEnvConfig();
			log = Logger.getLogger("devpinoyLogger");
			
			
	    }
	 
	 @BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		 
		 driver = new DriverClass().driver;
		 driver.get(Input.url);
		 lp = new LoginPage(driver);
		 Utility.logBefore(testMethod.getName());     
	 }
     
	 @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result, Method testMethod) throws InterruptedException {
	 	 if(ITestResult.FAILURE==result.getStatus()){
	 		Utility bc = new Utility(driver);
	 		bc.screenShot(result);
	 	 }
	 	 Utility.logafter(testMethod.getName());
	 	 lp.clearBrowserCache();
     }
	@AfterClass(alwaysRun=true)
	public void close() throws InterruptedException{
		//driver.quit();
		lp.clearBrowserCache();
				
	}
	
	/* Method : successfulAccountCreation
	 * Author : Suresh Bavihalli
	 * Description : This method is used to create user account on thuisbezorgd
	 * Its fills necessary information and create an account, validate success message 
	 * read activation URL from backend (using Gmail API) and activate the users account.
	 * Also validate confirmation message post activation followed by login as new user and logout!
	 * 
	 * Please refer TC ID : UA_01 in shared excel
	*/
	@Test//(groups={"Pone"})
	public void successfulAccountCreation() throws InterruptedException {
		
		log.info("Web application launched");
		HomePage hp = new HomePage(driver);
		hp.createUserAccount(Input.newUserName, Input.newUserEmailID,Input.newUserPassword);
		Thread.sleep(3000);
		
		Assert.assertTrue(hp.getaccountCreationPopUpMsg().contains(Input.accountCreationPopUpMsg));
		log.info("Account creation is successful for the user : "+Input.newUserName);
				
		//get activation URL from Gmail
		String activationUrl = LoginPage.readActivationLinkGmailMail(Input.emailSubjectForAccActivation,Input.newUserEmailID,Input.newUserPassword);
		driver.navigate().to(activationUrl);
		
		//Validate confirmation message after activating user account
		Assert.assertTrue(hp.getUserCreationConfirmationMsg().contains(Input.confirmationMsgAfterActivation));
		
		//try to access the same activation URL and it should not be valid!
		driver.navigate().to(activationUrl);
		Assert.assertTrue(hp.getUserCreationConfirmationMsg().contains(Input.msgUsingSameActivatedLink));
		
		
	}
	
	/* Method : createDuplicateAccount
	 * Author : Suresh Bavihalli
	 * Description : This method is to validate that, we get an proper error/notification message 
	 * when we try to create an user which is linked to existing user in thuisbezorgd!
	 * 
	 * Please refer TC ID : UA_02 in shared excel
	*/
	@Test//(groups={"Pone"})
	public void createDuplicateAccount() throws InterruptedException {
		
		HomePage hp = new HomePage(driver);
		
		//Just provide existing email id!
		hp.createUserAccount(Input.newUserName, Input.existingUserEmailID,Input.newUserPassword);
		Thread.sleep(3000);
		Assert.assertTrue(hp.getaccountCreationNotificationMsg().equals(Input.msgAddingExsitingUser));
		log.info("Account with an email ID : "+Input.existingUserEmailID+" is already exists!");
		
	}
	/* Method : tryToLoginWithoutActivatingAccount
	 * Author : Suresh Bavihalli
	 * Description : This method is to validate that, user try to login without activating his account after creation.
	 * 
	 * Please refer TC ID : UA_04 in shared excel
	*/
	@Test//(groups={"Pone"})
	  public void tryToLoginWithoutActivatingAccount() throws InterruptedException {
		
		
		HomePage hp = new HomePage(driver);
		hp.createUserAccount(Input.newUserName, Input.newUserEmailID2,Input.newUserPassword);
		Thread.sleep(3000);
		
		Assert.assertTrue(hp.getaccountCreationPopUpMsg().contains(Input.accountCreationPopUpMsg));
		log.info("Account creation is successful for the user : "+Input.newUserName);
		driver.get(Input.url);
		//Login to application as a newly created user!
		lp.loginToApp(Input.newUserEmailID2, Input.newUserPassword);
		Thread.sleep(3000);
		Assert.assertTrue(lp.loginErrorMsg.getText().equals("Bevestig je account om je aan te kunnen melden. Je kunt de bevestigingslink hiervoor in je e-mailpostvak terugvinden."));
		
	

	}
	  
	/* Method : madatoryFiledsWhileCreatingAnAccount
	 * Author : Suresh Bavihalli
	 * Description : this methods to validate all mandatory fields info/error messages while creating an account
	 * 
	 * Please refer TC ID : UA_03 in shared excel
	*/	
	@Test//(groups={"Pone"})
	public void madatoryFiledsWhileCreatingAnAccount() throws InterruptedException {
		SoftAssert softAssertion= new SoftAssert();
		HomePage hp = new HomePage(driver);
		
		//case 1: Sending no values to fields
		hp.createUserAccount("", "","");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(hp.accountCreationNotificationMsg));
		softAssertion.assertTrue(hp.accountCreationNotificationMsg.getText().equals(Input.NoFieldsEntered));
		
		//case 2: Sending no values to email fields
		driver.get(Input.url);
		hp.createUserAccount("Suresh", "","Pass@123");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(hp.accountCreationNotificationMsg));
		softAssertion.assertTrue(hp.accountCreationNotificationMsg.getText().equals(Input.NoFieldsEntered));
				
		//case 3: Sending no values to password fields
		driver.get(Input.url);
		hp.createUserAccount("Suresh", "sureshb075@gmail.com","");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(hp.accountCreationNotificationMsg));
		softAssertion.assertTrue(hp.accountCreationNotificationMsg.getText().equals(Input.whenNoPwdProvided));

		//case 4: Sending wrong email 
		driver.get(Input.url);
		hp.createUserAccount("Suresh", "sureshb075gmail.com","Pass@123");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(hp.accountCreationNotificationMsg));
		softAssertion.assertTrue(hp.accountCreationNotificationMsg.getText().equals(Input.wrongEmail));
		
		softAssertion.assertAll();
	}
	
}
