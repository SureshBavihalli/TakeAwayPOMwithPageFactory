package testScripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.DriverClass;
import junit.framework.Assert;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.Meals;
import pageFactory.OrderCheckOut;
import pageFactory.Utility;

public class TA_02_CartValidation {

	WebDriver driver;
	LoginPage lp; 
	Logger log;
	
	@BeforeClass
	 public void setup() throws ParseException, InterruptedException, IOException{
			Input in =new Input();
			in.loadEnvConfig();
			log = Logger.getLogger("devpinoyLogger");
			driver = new DriverClass().driver;
			lp = new LoginPage(driver);
			driver.get(Input.url);
			driver.manage().deleteAllCookies();
			
	    }
	
	 @BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		
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
	
	/* Method : buttonsOnCart
	 * Author : Suresh Bavihalli
	 * Description : This method is to validate +, -, edit and delete buttons on shopping cart
	 * please refer Test case ID : OP_06 for more details
	*/
	@Test//(groups={"p1"})
	public void buttonsOnCart() throws InterruptedException {
		SoftAssert softAssertion= new SoftAssert();
		double price;
		double total_price=0;
		String restaurantName = "QA Restaurant Selenium";
		Map<String, Double> mealAndPrice = new LinkedHashMap<String, Double>();
	
		
		//Navigate to restaurant directly!
		HomePage hp = new HomePage(driver);
		hp.setserachByLocation("8889AA");
		Thread.sleep(4000);
		
		//Select restaurant
		Assert.assertTrue(hp.selectRestaurant(restaurantName));
		Thread.sleep(4000);
		
		//select all the required meals
		Meals meals= new Meals(driver);
		
		price = meals.selectMealoritems("Salami",null);
		mealAndPrice.put("Salami",price);
		total_price += price;
		log.info(total_price); 
		
		//Validate cart items, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPrice,total_price));
		
		//Case 1 :Click on + twice,so price should be increased to 3 times
		OrderCheckOut oc = new OrderCheckOut(driver);
		oc.plus.click();
		oc.plus.click();
		//multiply meal price by 3, so lets modify price in map!
		mealAndPrice.put("Salami", mealAndPrice.get("Salami") *3);
		total_price = mealAndPrice.get("Salami");
		System.out.println(mealAndPrice);
		//Validate cart items, price and total price!, even order of items!
		softAssertion.assertTrue(meals.validateCart(mealAndPrice,total_price));
		
		
		//Case 2 :Click on - twice,so price should be decreased to one third times
		oc.minus.click();
		oc.minus.click();
		//multiply meal price by 3, so lets modify price in map!
		mealAndPrice.put("Salami", mealAndPrice.get("Salami") /3);
		total_price = mealAndPrice.get("Salami");
		System.out.println(mealAndPrice);
		//Validate cart items, price and total price!, even order of items!
		softAssertion.assertTrue(meals.validateCart(mealAndPrice,total_price));
		
		//Case 3 :Click on edit and add comment to the item
		oc.edit.click();
		Thread.sleep(2000);
		oc.addCommentToItem.sendKeys("No chees to my pizza! Please!!");
		oc.edit.click(); //to save and come out of edit mode
		Thread.sleep(2000);
		softAssertion.assertTrue(oc.addedCommentToMeal.getText().equals("No chees to my pizza! Please!!"));
				
		//Case 4 :Click on delete item,so total price shows as 0.
		oc.delete.get(0).click();
		//remove item from map to match the cart!
		mealAndPrice.remove("Salami");
		//total_price = mealAndPrice.get("Salami");
		total_price = 0.00; //since no items in map!
		//Validate cart items, price and total price!, even order of items!
		softAssertion.assertTrue(meals.validateCart(mealAndPrice,total_price));
		
		softAssertion.assertAll(); //catch all failure cases
	}
}
