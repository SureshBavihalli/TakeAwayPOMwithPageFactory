package pageFactory;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automationLibrary.DriverClass;

public class HomePage {

WebDriver driver;
Logger log;    
    //Locator to click on menu
	 @FindBy(linkText="Bekijk persoonsgegevens")
	public WebElement viewUsersPersonalData;
	 
	//Locator to click on menu
	@FindBy(id="imysearchstring")
	public WebElement serachByLocation;
	
	//Locator to click on menu
	@FindBy(id="submit_deliveryarea")
	public WebElement serachBtn;
	
	//Locator all restaurantname
	@FindBy(xpath="//a[@class ='restaurantname']")
	public List<WebElement> restaurants ;
	
	//Locator to click on menu
		 @FindBy(xpath="//button[@class='menu button-myaccount userlogin']")
		 WebElement getMenuBtn;

	   
	    //UI locators to create new user account
		 @FindBy(css="#userpanel-wrapper > section.bottom-content > section > a.button_form.button-cta-small")
		 WebElement CreateAccount;
		 
		 @FindBy(id="iaccountsurname")
		 WebElement NewUserName;
		 
		 @FindBy(id="iaccountuser")
		 WebElement NewUserEmeilId;
		 
		 @FindBy(id="iaccountpass")
		 WebElement NewUserPassword;
		 
		 @FindBy(id="iaccountpass2")
		 WebElement NewUserConfirmPassword;
		 
		 @FindBy(id="registerbutton")
		 WebElement registerbutton;
		   
		 @FindBy(xpath="//div[@class='notificationfeedback']")
		 public WebElement accountCreationNotificationMsg;
		 
		 @FindBy(xpath="//section[@class='register-modal-screen']")
		 WebElement accountCreationPopUp;
		 
		 @FindBy(xpath="//section[@class='bottom-content']/p")
		 WebElement accountCreationPopUpMsg;
		 
		 //Account activation confirmation locators
		 @FindBy(xpath="//div[@class='small-content']")
		 WebElement accountCreationMsg;
	
	 public HomePage(WebDriver driver) {
		   	this.driver = driver;
	        //This initElements method will create all WebElements
	        //PageFactory.initElements(driver, this);
	        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	        log = Logger.getLogger("devpinoyLogger");
	}
	 
	 	 
	 public String getaccountCreationNotificationMsg(){

	     return    accountCreationNotificationMsg.getText();

	    }
	 
	 public String getaccountCreationPopUpMsg(){

	     return    accountCreationPopUpMsg.getText();

	    }
	 
	 
	 public String getUserCreationConfirmationMsg(){

	     return    accountCreationMsg.getText();

	    }
	 public void setNewUserName(String strUserName){
		// new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(NewUserName));
		 NewUserName.sendKeys(strUserName);     
	 }

	 public void setNewUserEmeilId(String emailId){

		 NewUserEmeilId.sendKeys(emailId);     
	 }

	 public void setNewUserPassword(String password){

		 NewUserPassword.sendKeys(password);     
	 }

	 public void setNewUserConfirmPassword(String password){

		 NewUserConfirmPassword.sendKeys(password);     
	 }

	 //method to search the given location
	 //Searching location is going bit crazy !
	 //should handle cookies, search by clicking search key or just by pressing enter key!
	 //So here I am trying multiple time by checking restaurant size, since doing it is valid
	 int trySearch = 1;
		 public void setserachByLocation(String location) throws InterruptedException{
			Thread.sleep(4000);	
				 try{
					 	serachByLocation.sendKeys("a"+Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
						serachByLocation.sendKeys(location);
						serachBtn.click();
						log.info("Searching for "+location+".......");
						Thread.sleep(3000);
					}catch (Exception e) {
					}
				 try{
						serachByLocation.sendKeys("a"+Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
						serachByLocation.sendKeys(location+Keys.ENTER);
						Thread.sleep(3000);
					}catch (Exception e1) {
					
						
						
					}
					
				 if(restaurants.size()==0 && trySearch <5){
					 setserachByLocation(location);
				 }
					
		 }
		 
		 //method to select the given restaurant from the list
		 public boolean selectRestaurant(String restaurantname) throws InterruptedException {
			/* try {
				//Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			// new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(restaurants.get(0)));
			 Thread.sleep(6000);
			 log.info("Found "+restaurants.size()+" restaurants in a given location! Cheers!");
			 for (int i=0; i<restaurants.size();i++){
				 
				 //log.info(restaurants.get(i).getText());
			      if(restaurants.get(i).getText().equals(restaurantname)){
			      	restaurants.get(i).click();
			      	log.info("Selected "+restaurantname);
			      	return true;
			      }
			 }
			 return false;
		}
		 
/* This function is to create new user account on TakeAway!
 *     
*/    public void createUserAccount(String name, String email, String password) throws InterruptedException {
        
		log.info("Account is being created for the user : "+name);
        log.info("with an email ID : "+email);
	    
        getMenuBtn.click();
		Thread.sleep(3000);
	   // new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(CreateAccount));
	    CreateAccount.click();
	    Thread.sleep(2000);
	    this.setNewUserName(name);
		this.setNewUserEmeilId(email);
		this.setNewUserPassword(password);
		this.setNewUserConfirmPassword(password);
		registerbutton.click();
		
		
	}

}
