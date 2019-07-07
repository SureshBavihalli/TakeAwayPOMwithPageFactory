package pageFactory;
import java.io.File;import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import automationLibrary.Log;
import testScripts.Input;

public class Utility {
	WebDriver driver;
	 static Logger log = Logger.getLogger("devpinoyLogger");
	public Utility(WebDriver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }
    
	public static int dynamicNameAppender() {
		 Random random = new Random();
			return random.nextInt(1000000);
		
	}

	public static void logBefore(String methodName) {
		 log.info("****************************************************************************************");
				 
		 log.info("****************************************************************************************");
				 
		 log.info("$$$$$$$$$$$$$$$$$$$$$           Executing  -    "+methodName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
				 
		 log.info("****************************************************************************************");
				 
		 log.info("****************************************************************************************\n");

	}
	
	public static void logafter(String methodName) {
		log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
		 
		log.info("X");
		 
		log.info("X");
		 
		log.info("X");
		 
		log.info("X");

	}
	
 public void screenShot(ITestResult result) {
	 if(Input.screenShotOnFail.equalsIgnoreCase("YES"))
	 try{
		 // To create reference of TakesScreenshot
   		 TakesScreenshot screenshot=(TakesScreenshot)driver;
   		 // Call method to capture screenshot
   		 File src=screenshot.getScreenshotAs(OutputType.FILE);
   		 String name[] = result.getMethod().toString().replace(".", "--").split("\\(");
   		
   		String path=System.getProperty("user.dir")+"/Screenshots/"+name[0]+".png";
   		 
   		 // result.getName() will return name of test case so that screenshot name will be same as test case name
   		 FileUtils.copyFile(src, new File(path));
   		 log.info("Successfully captured a screenshot, please check : "+ path);
   		Reporter.log("<a href='"+ path + "'> <img src='"+ path + "' height='100' width='100'/> </a>");
   		 }catch (Exception e){
   			 e.printStackTrace();
   		 log.info("Exception while taking screenshot "+e.getMessage());
   		 } 
}
 public static void waitForLoad(WebDriver driver) {
     ExpectedCondition<Boolean> pageLoadCondition = new
             ExpectedCondition<Boolean>() {
                 public Boolean apply(WebDriver driver) {
                     return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                 }
             };
     WebDriverWait wait = new WebDriverWait(driver, 30);
     wait.until(pageLoadCondition);
 }
 
}
