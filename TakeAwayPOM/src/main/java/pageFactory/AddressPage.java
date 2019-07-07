package pageFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import automationLibrary.DriverClass;


public class AddressPage {
		
		WebDriver driver;
		Logger log;
		
		//address locators
		@FindBy(id="iaddress")
		public WebElement address ;
				
		@FindBy(id="itown")
		public WebElement town ;
				
		@FindBy(id="isurname")
		public WebElement userName ;
		
		@FindBy(id="iphonenumber")
		public WebElement phoneNumber ;
		
		@FindBy(id="iemail")
		public WebElement emailId ;
		
		@FindBy(id="ideliverytime")
		public WebElement deliveryTime ;
		
		
		
		
		 public AddressPage(WebDriver driver) {
			 	this.driver = driver;
		        //This initElements method will create all WebElements
		        PageFactory.initElements(driver, this);
		        log = Logger.getLogger("devpinoyLogger");
		}
		
         //Function to fill the address
		 public void fillAddress(String addressLine, String townName, String unser, String phNum,String email) {
			 
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(address)); 
			address.sendKeys(addressLine);
			town.sendKeys(townName);
			userName.sendKeys(unser);
			phoneNumber.clear();
			phoneNumber.sendKeys(phNum);
			try{ //email id is not editable in some scenarios!
			emailId.clear();
			emailId.sendKeys(email);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{//when order is done without an an account login
			Select delivey = new Select(deliveryTime);
			delivey.selectByIndex(1);
			}catch (Exception e) {
				// TODO: handle exception
			}
			log.info("Address fields are filled");
			

		}
		 

}
