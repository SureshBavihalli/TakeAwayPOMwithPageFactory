package testScripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import automationLibrary.DriverClass;
import junit.framework.Assert;
import pageFactory.AddressPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.Meals;
import pageFactory.OrderCheckOut;
import pageFactory.Utility;


public class TA_03_PlacingOrders{

	WebDriver driver;
	Logger log;
	LoginPage lp;
	static Map<String, Double> mealAndPriceRest2 = new LinkedHashMap<String, Double>();
	static double  priceRest2;
	static double total_price_rest2=0;
	String restaurantName1 = "QA Restaurant Selenium";
	String restaurantName2 = "Pan Pizza Man";
	HomePage hp;
	Meals meals;
	OrderCheckOut oc; 
	
	@BeforeClass
	 public void setup() throws ParseException, InterruptedException, IOException{
			Input in =new Input();
			in.loadEnvConfig();
			
			log = Logger.getLogger("devpinoyLogger");
		
	    }
	
	 @BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		//driver.manage().deleteAllCookies();
		driver = new DriverClass().driver;
		lp = new LoginPage(driver);
		hp = new HomePage(driver);
		meals= new Meals(driver);
		oc =  new OrderCheckOut(driver);
		Utility.logBefore(testMethod.getName());     
	 }
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result, Method testMethod) throws InterruptedException {
	 	 if(ITestResult.FAILURE==result.getStatus()){
	 		Utility bc = new Utility(driver);
	 		bc.screenShot(result);
	 	 }
	 	 Utility.logafter(testMethod.getName());
	 	 //driver.close();
	 	 driver.quit();
	 	 lp.clearBrowserCache();
    }
	@AfterClass(alwaysRun=true)
	public void close() throws InterruptedException{
		//driver.quit();
		lp.clearBrowserCache();
				
	}
	/* Method : orderTroughUserAccountUsingContantPaymentMode
	 * Author : Suresh Bavihalli
	 * Description : This a big method!!, to validate Test case ID : OP_01 in shared excel
	 *  
	*/
	@Test
	public void orderTroughUserAccountUsingContantPaymentMode() throws InterruptedException {
		
		double price;
		double total_price=0;
		Map<String, Double> mealAndPrice = new LinkedHashMap<String, Double>();
		driver.get(Input.url);
		Utility.waitForLoad(driver);
		
		//login to application
		lp.loginToApp(Input.existingUserEmailID,Input.existingUserPassword);
		
		//search for location
		
		hp.setserachByLocation("8889AA");
		//Thread.sleep(4000);
		
		//Select restaurant
		Assert.assertTrue(hp.selectRestaurant("QA Restaurant Selenium"));
		//Thread.sleep(4000);
		
		//select all the required meals
		Meals meals= new Meals(driver);
		
		price = meals.selectMealoritems("Duck Breast","tomato");
		mealAndPrice.put("Duck Breast",price);
		total_price += price;
		
		price = meals.selectMealoritems("Salami",null);
		mealAndPrice.put("Salami",price);
		total_price += price;
		
		price = meals.selectMealoritems("Coke",null);
		mealAndPrice.put("Coke",price);
		total_price += price;
		log.info(total_price); 
		
		//Validate cart items, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPrice,total_price));
		
		//Checkout order
		oc = new OrderCheckOut(driver);
		oc.checkoutOrder.click();
		
		//Select address, use an existing address
		oc.selectAddress(Input.existingAddress);
		
		//Comments to restaurant 
		oc.secommentsForRestaurant("No suger to meals please!");
		
		//Options for below call : iDEAL, PayPal, VVVCadeaukaart and CHIPKNIP
		//transaction fair is validated according to the option selected
		oc.paymentMode("iDEAL");
		
		//Submit an order
		JavascriptExecutor executor = (JavascriptExecutor)driver;
    	executor.executeScript("arguments[0].click()", oc.submitOrder);	
		
    	oc.acceptCookiesPopUp();
    	
    	//Cancel the order
    	oc.orderCancellation();
    	
    	//Return to cart
    	oc.clcikToTryOtherModeOfpayment.click();
    	
    	//Comments to restaurant 
    	oc.secommentsForRestaurant("No suger to meals please!");
    			
    	
    	//Payment with Contant
    	oc.paymentMode("Contant");
    	JavascriptExecutor executor1 = (JavascriptExecutor)driver;
    	executor1.executeScript("arguments[0].click()", oc.submitOrder);	
    	
    	//Verify order placement 
    	Assert.assertTrue(oc.trackOrder.isDisplayed());
    	//Logout
    	lp.logoutFromPopup();
	}
	
	/* Method : placeOrderWithoutAnAccount
	 * Author : Suresh Bavihalli
	 * Description : This a big method!!, to validate Test case ID : OP_02 in shared excel
	 *  
	*/
	@Test
	public void placeOrderWithoutAnAccount() throws InterruptedException {
		
		double price;
		double total_price=0;
		String restaurantName = "QA Restaurant Selenium";
		Map<String, Double> mealAndPrice = new LinkedHashMap<String, Double>();
		driver.get(Input.url);
		Utility.waitForLoad(driver);
		
		//search for location
		hp.setserachByLocation("8889AA");
		
		//Select restaurant
		Assert.assertTrue(hp.selectRestaurant(restaurantName));
		
		//select all the required meals
		Meals meals= new Meals(driver);
				
		price = meals.selectMealoritems("Duck Breast","tomato");
		mealAndPrice.put("Duck Breast",price);
		total_price += price;
		log.info(total_price); 
		
		//Validate cart items, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPrice,total_price));

		//Checkout order
		oc.checkoutOrder.click();
		
		//Select address, use an existing address
		AddressPage  ap = new AddressPage(driver);
		ap.fillAddress("11, RR Nagar", "Bangalore", "SureshBavihalli", "0123456789", Input.existingUserEmailID);
		
       	oc.paymentMode("Contant");
    	
    	JavascriptExecutor executor1 = (JavascriptExecutor)driver;
    	executor1.executeScript("arguments[0].click()", oc.submitOrder);	
    	
    	//Verify order placement 
    	Assert.assertTrue(oc.trackOrder.isDisplayed());
    	
    	//verify email notification
    	Assert.assertTrue(lp.orderConfirmationEmail(Input.existingUserEmailID,Input.existingUserPassword,restaurantName));
    	
    	//navigate to my ordersPage and check of recently placed order!
    	driver.get(Input.url+"mijnaccount/mijn-bestelhistorie");
      	Assert.assertTrue(oc.checkOrder(restaurantName));
    	
	}
	
	/* Method : addingItemsToCartInTwoRestaurants
	 * Author : Suresh Bavihalli
	 * Description : This method is to validate Test case ID : OP_04 in shared excel
	 *  
	*/
	@Test
	public void addingItemsToCartInTwoRestaurants() throws InterruptedException {
		double priceRest1;
		double total_price_rest1=0;
		mealAndPriceRest2.clear();
	    priceRest2 = 0;
		total_price_rest2=0;
		
		driver.get(Input.url);
		Utility.waitForLoad(driver);
		
		Map<String, Double> mealAndPriceRest1 = new LinkedHashMap<String, Double>();
	
		//search for location
		hp.setserachByLocation("8889AA");
		
		//Select first restaurant
		Assert.assertTrue(hp.selectRestaurant(restaurantName1));
				
		//select all the required meals
		priceRest1 = meals.selectMealoritems("Salami",null);
		mealAndPriceRest1.put("Salami",priceRest1);
		total_price_rest1 += priceRest1;
		
		log.info(total_price_rest1); 
		
		//Validate cart itemsin restaurant 1, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPriceRest1,total_price_rest1));
		
		//Visit second restaurant and add meals to cart
		driver.navigate().back();
		Assert.assertTrue(hp.selectRestaurant(restaurantName2));
		
		priceRest2 = meals.selectMealoritems("ola",null);
		mealAndPriceRest2.put("ola",priceRest2);
		total_price_rest2 += priceRest2;
		log.info(total_price_rest2); 
		
		//Validate cart items in restaurant 2, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPriceRest2,total_price_rest2));
		
		//return back to first restaurant and check the cart
		//Validate cart items, price and total price!, even order of items!
		driver.navigate().back();
		Assert.assertTrue(hp.selectRestaurant(restaurantName1));
		Assert.assertTrue(meals.validateCart(mealAndPriceRest1,total_price_rest1));
		
		
		

	}
	/* Method : addMealsToCartInTwoRestaurantsAndOrderFromOne
	 * Author : Suresh Bavihalli
	 * Description : This method is to validate Test case ID : OP_05 in shared excel
	 *  
	*/
	//Since adding items to cart in two restaurants is covered in "addingItemsToCartInTwoRestaurants"
	//Lets continue from there using depends on method and priority
	@Test//(dependsOnMethods = {"addingItemsToCartInTwoRestaurants"})
	public void addMealsToCartInTwoRestaurantsAndOrderFromOne() throws InterruptedException {
		mealAndPriceRest2.clear();
	    priceRest2 = 0;
		total_price_rest2=0;
		addingItemsToCartInTwoRestaurants(); //addingItemsToCartInTwoRestaurants steps should be done
		                                     //Since this class methods execute methods with seperate browser, we can not use depends on method to 
		                                     //continue from addingItemsToCartInTwoRestaurants..
		//Place an order
		//Checkout order
		OrderCheckOut oc = new OrderCheckOut(driver);
		oc.checkoutOrder.click();
		
		//Select address, use an existing address
		AddressPage  ap = new AddressPage(driver);
		ap.fillAddress("11, RR Nagar", "Bangalore", "SureshBavihalli", "0123456789", Input.existingUserEmailID);
		
				
		//Options for below call : iDEAL, PayPal, VVVCadeaukaart and CHIPKNIP
		//transaction fair is validated according to the option selected
	   	oc.paymentMode("Contant");
      
    	JavascriptExecutor executor1 = (JavascriptExecutor)driver;
    	executor1.executeScript("arguments[0].click()", oc.submitOrder);	
    	
    	//Verify order placement 
    	Assert.assertTrue(oc.trackOrder.isDisplayed());
    	
    	//Lets go two other restaurant and validate the cart, that is Pan Pizza Man!
    	driver.get(Input.url);
    	
    	Utility.waitForLoad(driver);
    	//search for location
    	Thread.sleep(5000);
    	hp.setserachByLocation("8889AA");
		//Thread.sleep(4000);
    	
    	Assert.assertTrue(hp.selectRestaurant(restaurantName2));
		//Thread.sleep(4000);
		
		//Validate cart items in restaurant 2, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPriceRest2,total_price_rest2));
	

	}
	/* Method : modeOfPayments_TransactionCharges
	 * Author : Suresh Bavihalli
	 * Description : This method is to validate Test case ID : OP_07 in shared excel
	 *  
	*/
	@Test
	public void modeOfPayments_TransactionCharges() throws InterruptedException{
		driver.get(Input.url);
		Utility.waitForLoad(driver);
		
		double price;
		double total_price=0;
		String restaurantName = "QA Restaurant Selenium";
		Map<String, Double> mealAndPrice = new LinkedHashMap<String, Double>();
	
		
		//search for location
		HomePage hp = new HomePage(driver);
		hp.setserachByLocation("8889AA");
		//Thread.sleep(4000);
		
		//Select restaurant
		Assert.assertTrue(hp.selectRestaurant(restaurantName));
		
		//select all the required meals
		Meals meals= new Meals(driver);
		
		price = meals.selectMealoritems("Salami",null);
		mealAndPrice.put("Salami",price);
		total_price += price;
		log.info(total_price); 
		
		//Validate cart items, price and total price!, even order of items!
		Assert.assertTrue(meals.validateCart(mealAndPrice,total_price));
		
		//Checkout order
		oc.checkoutOrder.click();
		//Thread.sleep(6000);
		
		//Options for below call : iDEAL, PayPal, VVVCadeaukaart and CHIPKNIP
		//transaction fair is validated according to the option selected
		Assert.assertTrue(oc.paymentMode("Contant"));//just to make cart remove extra transaction fee added other modes of payment
		Assert.assertTrue(oc.paymentMode("iDEAL")); 
		//Thread.sleep(2000);
		Assert.assertTrue(oc.paymentMode("Contant"));//just to make cart remove extra transaction fee added other modes of payment
		Assert.assertTrue(oc.paymentMode("PayPal"));
		//Thread.sleep(2000);
		Assert.assertTrue(oc.paymentMode("Contant"));//just to make cart remove extra transaction fee added other modes of payment
		Assert.assertTrue(oc.paymentMode("VVVCadeaukaart"));
		//Thread.sleep(2000);
		Assert.assertTrue(oc.paymentMode("Contant"));//just to make cart remove extra transaction fee added other modes of payment
		Assert.assertTrue(oc.paymentMode("CHIPKNIP"));
				

	}
	
	
	
	
}
