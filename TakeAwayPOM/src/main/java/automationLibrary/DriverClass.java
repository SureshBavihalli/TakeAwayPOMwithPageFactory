/// <summary> ......................../////////////////rakesh

package automationLibrary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.gargoylesoftware.htmlunit.javascript.host.html.Option;

import configsAndTestData.ConfigLoader;
import configsAndTestData.ConfigMain;

import testScripts.Input;

/// <summary> ////suresh/////
/// Wraps the Selenium WebDriver class.  Encapsulates driver creation, 
/// error handling and destruction
/// </summary>
public  class DriverClass  {  
	   Logger log;
	   public  WebDriver driver;
	   /// <summary>
       /// Public constructor of Driver
       /// </summary>
       /// <param name="url">The URL which the driver will try to connect to when created</param>
       /// <param name="browserType">Currently only Chrome, IE and Firefox supported</param>
	
	public DriverClass() { 
		System.out.println(Input.browserName);
		  this.driver = createDriver(Input.browserName); 
	      //this.driver.get(Input.url);
		  log = Logger.getLogger("devpinoyLogger");
	      log.info(Input.browserName + "is opned and loading application");
	      
	   }  
	   
	 public WebDriver getWebDriver()
	   {
		   return driver;
	   }
	   	
	   private WebDriver createDriver(String browserName) { 
	     /* if (browserName.toUpperCase().equals("FIREFOX") )
	         return firefoxDriver(); */

	      if (browserName.toUpperCase().equals("CHROME") )
	      return chromeDriver(); 
	      
	      if (browserName.toUpperCase().equals("FIREFOX") )
		  return chromeDriver(); 
	      
	      if (browserName.toUpperCase().equals("IE") )
		  return chromeDriver(); 
	      
	      throw new RuntimeException ("invalid browser name"); 
	   } 

	   private WebDriver chromeDriver() { 
	      
	      try { 
	    	  System.setProperty("webdriver.http.factory", "apache");
	    	  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "//BrowserDrivers//chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("chrome.switches","--disable-extensions");
				new DesiredCapabilities();
				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				
				Map<String, Object> prefs = new HashMap<String, Object>();
			
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.prompt_for_download", "false");
				
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
		
				driver = new ChromeDriver(caps);
				driver.manage().window().maximize();
				//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
				return driver;	
	      } 

	      catch (Exception ex) { 
	        ex.printStackTrace();
	    	  throw new RuntimeException
	              ("couldnt create chrome driver"); 
	      } 
	   } 
	  
	
		
	 /// <summary>
    /// Generic method to wait until the given condition is true.
    /// </summary>
    /// <param name="condition">Will wait until this condition is true</param>
    /// <param name="timeout">Optional: override the application timeout value</param>
    /// <param name="message">Optional: override the message used if the element is never found</param>
    /// <param name="interval">Optional: override the polling interval value</param>
    public void WaitUntil(Callable<Boolean> condition, int timeout)
    {
 	   
       // message = (message==null) ? String.format("waiting for true condition on {0}", condition.toString()):message;

        long startingTime = System.currentTimeMillis();
        //timeout = (timeout==null) ? Duration.ofMillis(this.getApplicationTimeout()):timeout;
        //interval = (interval==null) ? Duration.ofMillis(this.getApplicationPollingInterval()):interval;
        boolean running = true;
        Thread thread = Thread.currentThread();
        while (running)
        {
     	   ExecutorService executor = Executors.newFixedThreadPool(1);
            try
            {
         	   Future<Boolean> future = executor.submit(condition);
         	   //System.out.println(future.get()+"----");
                if (future.get()) return;
            }
            catch(Exception e)
            {
         	   //e.printStackTrace();
            }
            

            if ((System.currentTimeMillis() - startingTime) > timeout)
            {
                running = false;
            }
            else
            {
               try {
					thread.join(Input.interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            executor.shutdown();
        }
        
    }
    
    

    public  void waitForPageToBeReady() 
	{
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //This loop will rotate for 100 times to check If page Is ready after every 1 second.
	    //You can replace your if you wants to Increase or decrease wait time.
	  
	    for (int i=0; i<30; i++)
	    { 
	        try 
	        {
	            Thread.sleep(1000);
	        }catch (InterruptedException e) {} 
	        //To check page ready state.

	        if (js.executeScript("return document.readyState").toString().equals("complete"))
	        { 
	        	//Add_Log.info("Page load is complete");
	        	break; 
	        }   
	      }
	 }

    public void scrollingToBottomofAPage()
 	{
 		
 		
 		try
		{
			((JavascriptExecutor) driver).executeScript(
					"window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(500);
		}
		catch (Exception e)
		{
			System.out.println("IGNORE" + e.getLocalizedMessage());
		}
 	}
 	public  void scrollPageToTop()
 	{
 		// Scroll page to make element visisble.

 		for (int i = 0; i < 5; i++)
 		{
 			try
 			{
 				((JavascriptExecutor) driver).executeScript(
 						"window.scrollTo(document.body.scrollHeight, 0)");
 				Thread.sleep(500);
 			}
 			catch (Throwable e)
 			{
 				System.out.println(
 						"IGNORE" + e.getLocalizedMessage() + e.getMessage());
 				e.printStackTrace();
 			}
 		}
 	}
	
	   
	}