package pageFactory;



import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import testScripts.Input;

public class BaseClass {

    WebDriver driver;
  
       public BaseClass(WebDriver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }
    
    
    
    /*public void BckTaskClick() {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBackgroundTask_Button().Visible()  ;}}),Input.wait60);
    	getBackgroundTask_Button().Click();
  	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getSelectRole().Visible()  ;}}),Input.wait60);
    	getBckTask_SelectAll().Click();
    
	}
    */
 /*   public void CloseSuccessMsgpopup() {
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getCloseSucessmsg().Exists() ;}}), Input.wait30);
		getCloseSucessmsg().Click();
	
	}
    
   
	
     public void VerifySuccessMessage(String ExpectedMsg) {
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getSuccessMsgHeader().Visible()  ;}}), Input.wait30); 
      	Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
      	Assert.assertEquals(ExpectedMsg, getSuccessMsg().getText().toString());
  	}
*/       
}