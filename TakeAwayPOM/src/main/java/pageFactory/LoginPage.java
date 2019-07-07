package pageFactory;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automationLibrary.DriverClass;
import junit.framework.Assert;
import testScripts.Input;

public class LoginPage {

WebDriver driver;
static Logger log;

    //Locator to click on login link
	 @FindBy(linkText="Inloggen")
	 WebElement loginLink;
	 
	//Locator to enter an email adress 
	 @FindBy(id="iusername")
	 WebElement emailAddress;
	 
		 
	//Locator to enter the password
	 @FindBy(id="ipassword")
	 WebElement userPassword;
	 
	//Locator to enter the password
	 @FindBy(xpath="//input[@type = 'submit']")
	 WebElement submitUserCredetials;
	
	//Locator to close the UserInfo popUp
	 @FindBy(xpath="//*[@id='userpanel-wrapper']/section/button")
	 WebElement closeUserInfoPopUp;
		 
	//Locator to click on menu
	@FindBy(xpath="//button[@class='menu button-myaccount userlogin']")
	WebElement getMenuBtn;
	 
	
	//Account activation confirmation locators
	@FindBy(xpath="//div[@class='modal-wrapper modal']//a[contains(text(),'Uitloggen')]")
	WebElement logoutFromPopUp;
		 
	//Account activation confirmation locators
	@FindBy(xpath="//div[@class='notificationfeedbackwrapper']")
	public	WebElement loginErrorMsg;
	
	 public LoginPage(WebDriver driver) {
		 	this.driver = driver;
	        //This initElements method will create all WebElements
	        //PageFactory.initElements(driver, this);
		 	PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		 	log = Logger.getLogger("devpinoyLogger");
	}
	 
	 public void setemailAddress(String strUserName){
		 //new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(emailAddress));
		 emailAddress.sendKeys(strUserName);     
	 }
	 public void setuserPassword(String strUserName){

		 userPassword.sendKeys(strUserName);     
	 }
	 
	public boolean loginToApp(String strUserName,String strPasword) throws InterruptedException{
    	//driver.waitForPageToBeReady();
        //Fill user name
		driver.get(Input.url);
		Thread.sleep(5000);
		getMenuBtn.click();
		//new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(loginLink));
		
		loginLink.click();
		//new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(loginLink));
		setemailAddress(strUserName);

        //Fill password
		setuserPassword(strPasword);

        //Click Login button
		submitUserCredetials.click();
		//Thread.sleep(3000);
		
		//Close pop up!
		try{
		closeUserInfoPopUp.click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		log.info("Logged into application");
		return true;

    }
	
	//function to logout from application
    public void logoutFromPopup() throws InterruptedException{
    	
    	try{
	    	//new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(logoutFromPopUp));
	    	logoutFromPopUp.click();
	    	//Thread.sleep(8000);
	    	//new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(loginLink));
	    	Assert.assertTrue(loginLink.isDisplayed());
	    	log.info("Logged out");
    	}catch (Exception e) {
    		//Thread.sleep(3000);
        	getMenuBtn.click();
        	//new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(logoutFromPopUp));
	    	logoutFromPopUp.click();
	    	//Thread.sleep(3000);
	    	//new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(loginLink));
	    	Assert.assertTrue(loginLink.isDisplayed());
	    	log.info("Logged out");
		}
    	
    	
    	
    }
    
    //This function is to read user activation link from emails
    @SuppressWarnings("null")
	public static String readActivationLinkGmailMail(String SujjectLine,String userID, String userPwd) {
	  log.info("passed : "+SujjectLine);
      Properties props = new Properties();
      String url = null;
     
      try {
	            int waitForMail = 20;
	            for(int wait = 0; wait<waitForMail; wait++){
	            	   Session session = Session.getDefaultInstance(props, null);
	 	 
	            	   
	            	   
	 	            Store store = session.getStore("imaps");
	 	            store.connect("smtp.gmail.com", userID,"consilio@123" );
	 	 
	 	            Folder inbox = store.getFolder("inbox");
	 	            inbox.open(Folder.READ_WRITE);
	 	            //int messageCount = inbox.getMessageCount();
	 	            //log.info("Total Messages:- " + messageCount);
	 	            
	 	            // search for all "unseen" messages
	            	Flags seen = new Flags(Flags.Flag.SEEN);
		            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		            Message messages[] = inbox.search(unseenFlagTerm);
		            boolean mailReceived= false;
	            	for (int i = messages.length-1; i > messages.length-5; i--) {


	            	if(messages[i].getSubject().trim().startsWith(SujjectLine)){
	            		mailReceived=true;
		                log.info("e-mail Subject:- " + messages[i].getSubject());
		                Object msgContent = messages[i].getContent();
		
		                String content = ""; 
		                String link = null;
		                if (msgContent instanceof Multipart) {
		
		                     Multipart multipart = (Multipart) msgContent;
		
		                    
		
		                      BodyPart bodyPart = multipart.getBodyPart(0);
		                      link=bodyPart.getContent().toString();
		                      content= link.substring(link.indexOf("Account Bevestigen"));
		                      url = content.substring(content.indexOf("(https://www")+1, content.indexOf("confirmaccount-nl)")+17);
		                     	
		                    
		                }
		                else{
		                	if (msgContent instanceof String){
		                	
		                	link=msgContent.toString();
		                	  content= link.substring(link.indexOf("Account Bevestigen"));
		                      url = content.substring(content.indexOf("(https://www")+1, content.indexOf("confirmaccount-nl)")+17);
		                      log.info(url+"--+");	
	                      }
	                     
		                }
	              
	            	}
	            }

          	
          	if(mailReceived)
          		break;
          	else{
          		if(wait >= waitForMail){
          			log.info("no mail, waited for given time..");
          		}
          		//Thread.sleep(5000);
          		log.info("Waiting for mail!");
          	}
          	 inbox.close(true);
           	store.close();
	           }
	           
	           

      	} catch (Exception e) {
      		e.printStackTrace();
      	}
	      	log.info("URL read from the email : "+url); 
      return url;
      
    }
    
    //method to check order confirmation emails
    @SuppressWarnings("null")
	public boolean orderConfirmationEmail(String userID, String userPwd, String restaurantName) {
	 
      Properties props = new Properties();
      String url = null;
      boolean mailReceived = false;
      try {
	            int waitForMail = 20;
	            for(int wait = 0; wait<waitForMail; wait++){
	            	   Session session = Session.getDefaultInstance(props, null);
	 	        	   
	 	            Store store = session.getStore("imaps");
	 	            store.connect("smtp.gmail.com", userID,"consilio@123" );
	 	 
	 	            Folder inbox = store.getFolder("inbox");
	 	            inbox.open(Folder.READ_WRITE);
	 	            // search for all "unseen" messages
	            	Flags seen = new Flags(Flags.Flag.SEEN);
		            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		            Message messages[] = inbox.search(unseenFlagTerm);
		            mailReceived= false;
	            	for (int i = messages.length-1; i > messages.length-5; i--) {

	            	if(messages[i].getSubject().trim().startsWith("Bedankt voor je bestelling bij "+restaurantName+"!")){
	            		Object msgContent = messages[i].getContent();
	            		mailReceived=true;
	            	}
	            }

          	
          	if(mailReceived)
          		break;
          	else{
          		if(wait >= waitForMail){
          			log.info("no mail, waited for given time..");
          		}
          		//Thread.sleep(5000);
          		log.info("Waiting for mail!");
          	}
          	 inbox.close(true);
           	store.close();
	   }
	           
	           

      	} catch (Exception e) {
      		e.printStackTrace();
      	}
      if(mailReceived) {
    	  log.info("Order confirmation email is received!, cheers!!");
      return true;
      }
      else{
    	  log.info("Order confirmation email is NOT received!, something went wrong!!");
      return false;	  
      
      }
    }
    
    //method to close all chrome browser instances and chromedriver instances to make execution smoother   
    public static void clearBrowserCache() throws InterruptedException {
   	 try {
   	        String[] command = {"cmd.exe", "/C", "Start", System.getProperty("user.dir")+ "//BrowserDrivers//chromeBrowser.bat"};
   	        Runtime.getRuntime().exec(command); 
   	        Thread.sleep(4000);
   	       /* try {
				//Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
   	        //p.destroyForcibly();
   	    } catch (IOException ex) {
   	    }
	}

}