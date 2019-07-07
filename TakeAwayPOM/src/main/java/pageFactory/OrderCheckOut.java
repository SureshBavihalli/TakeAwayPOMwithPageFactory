package pageFactory;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;
import testScripts.Input;

public class OrderCheckOut {
	
	WebDriver driver;
	Logger log;
			
	//click to checkout
	@FindBy(id="icustomeraddress")
	public WebElement selectAddress;
	
	//comment
	@FindBy(id="iremarks")
	public WebElement commentsForRestaurant;
	
	//Transaction fair
	@FindBy(xpath="//div[@id='sum']//span[@class='cart-sum-name row-green']")
	public WebElement transactionName;
	
	@FindBy(xpath="//div[@id='sum']//span[@class='cart-sum-price row-green']")
	public WebElement transactionFair;
	
	@FindBy(xpath="//*[@id='ipaymentmethods']//label[contains(text(),'iDEAL')]")
	public WebElement iDeal;
	
	@FindBy(xpath="//*[@id='ipaymentmethods']//label[contains(text(),'PayPal')]")
	public WebElement PayPal;
	
	@FindBy(xpath="//*[@id='ipaymentmethods']//label[contains(text(),'VVV Cadeaukaart')]")
	public WebElement VVVCade;
	
	@FindBy(xpath="//*[@id='ipaymentmethods']//label[contains(text(),'CHIPKNIP')]")
	public WebElement Chipknip;
	
	@FindBy(xpath="//*[@id='ipaymentmethods']//label[contains(text(),'Contant')]")
	public WebElement Contant;
	
	
	//click to checkout
	@FindBy(xpath="//button[contains(text(),'Bestellen')]")
	public WebElement checkoutOrder;
	
	//click to submit order
	@FindBy(xpath="//div[@class='checkout-orderbutton-btn']//input[@class='button_form cartbutton-button']")
	public WebElement submitOrder;
	
	//click to accept cookies
	@FindBy(xpath="//div[@class='mlf-buttons-right']")
	public WebElement acceptCookies;
	
	//click to on IDEAL cancel order button
	@FindBy(xpath="//button/span[contains(text(),'annuleren')]")
	public WebElement cancelOrder;
	
	//click to on IDEAL cancel order button
	@FindBy(xpath="//button[contains(text(),'ja')]")
	public WebElement confirmOrderCancel;
	
	@FindBy(linkText="hier")
	public WebElement clcikToTryOtherModeOfpayment;
		
	//order cancellation message
	@FindBy(id="iresponsediv")
	public WebElement orderCancellationMessage;	
	
	@FindBy(xpath="//button[contains(text(),'Food Tracker link kopiëren')]")
	public WebElement trackOrder;	
	
	@FindBy(xpath="//div[@class='restaurant grid']//a[@class='restaurantname']")
	public List<WebElement> ordersInOrderslist;
	
	//+, -, edit and delete buttons
	@FindBy(xpath="//div[@id='products']//button[@class ='cart-meal-edit-add']")
	public WebElement plus;	
	
	@FindBy(xpath="//div[@id='products']//button[@class ='cart-meal-edit-delete']")
	public WebElement minus;	
	
	@FindBy(xpath="//div[@id='products']//button[@class ='cart-meal-edit-comment']")
	public WebElement edit;	
	
	@FindBy(xpath="//div[@id='products']//textarea[@class='cart-meal-textarea']")
	public WebElement addCommentToItem;	
	
	@FindBy(xpath="//div[@id='products']//span[@class='cart-meal-comment grey']")
	public WebElement addedCommentToMeal;	
	
	@FindBy(xpath="//div[@id='products']//button[@class ='cart-meal-delete']")
	public List<WebElement> delete;	
	
	
	 public OrderCheckOut(WebDriver driver) {
		    this.driver = driver;
		    //This initElements method will create all WebElements
	        //PageFactory.initElements(driver, this);
		    PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
		    log = Logger.getLogger("devpinoyLogger");
	}
	 
	 public void selectAddress(String address) throws InterruptedException {
		Thread.sleep(3000);	
		 new Select(selectAddress).selectByVisibleText(address);
		 Thread.sleep(2000);
			/*//also add other information
			AddressPage ap = new AddressPage(driver);
			
			//Sometimes 
			ap.address.sendKeys("#1103, Bangalore");
			ap.phoneNumber.clear();
			ap.phoneNumber.sendKeys("1234567890"); 
			ap.town.clear();
			ap.town.sendKeys("Bangalore");*/
			
	}
	 public void secommentsForRestaurant(String comments){

		 commentsForRestaurant.sendKeys(comments);     
	 }
	 
	 //method is to check orders in orders page
	 public boolean checkOrder(String restaurantName) {
		 for (int i = 0; i < ordersInOrderslist.size(); i++) {
				
	    		if(ordersInOrderslist.get(i).getText().equals(restaurantName)){
	    			log.info("Order is listed in my orders page!, from restaurant "+ restaurantName);
	    
	    			return true;
	    		}
			}
		 log.info("Order is NOT listed in my orders page!!!!!!, from restaurant "+ restaurantName);
		 return false;
	}
	 
	
	 //method is to check for transaction cost for each payment mode
	 public boolean paymentMode(String mode) throws InterruptedException {
      Thread.sleep(3000);
	  double grandTotalInCart;
      double TransactionFair; 
      Meals meals = new Meals(driver);
      //new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(iDeal));
      grandTotalInCart = Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", "."));
		
      if(mode.equals("iDEAL")){
			 TransactionFair = Input.iDEALTrandsactionFee;
			 JavascriptExecutor executor = (JavascriptExecutor)driver;
	        	executor.executeScript("arguments[0].click()", iDeal);	
			 //iDeal.click();
	         //Thread.sleep(3000);	
			 Assert.assertTrue(transactionName.getText().equals("Transactiekosten (iDEAL)"));
			 
			 
			 Assert.assertTrue(Double.parseDouble(transactionFair.getText().replaceAll("€ ", "").replaceAll(",", "."))==TransactionFair);
			 Assert.assertTrue(Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")) == grandTotalInCart+TransactionFair );
			 log.info("Transaction cost and total cost on cart looks fine for payment mode "+mode);
			 return true;
			 
		 }
		 else if(mode.equals("PayPal")){
			 TransactionFair = Input.PayPalTrandsactionFee;
			 JavascriptExecutor executor = (JavascriptExecutor)driver;
	         executor.executeScript("arguments[0].click()", PayPal);	
			 //iDeal.click();
	         //Thread.sleep(3000);	
			 Assert.assertTrue(transactionName.getText().equals("Transactiekosten (PayPal)"));
			 Assert.assertTrue(Double.parseDouble(transactionFair.getText().replaceAll("€ ", "").replaceAll(",", "."))==TransactionFair);
			 Assert.assertTrue(Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")) == grandTotalInCart+TransactionFair );
			 log.info("Transaction cost and total cost on cart looks fine for payment mode "+mode);
			 return true;
		 }
		 else if(mode.equals("VVVCadeaukaart")){
			 TransactionFair = Input.VVVCadeaukaartTransactionFee;
			 JavascriptExecutor executor = (JavascriptExecutor)driver;
	        	executor.executeScript("arguments[0].click()", VVVCade );	
			 //iDeal.click();
	         //Thread.sleep(3000);	
			 Assert.assertTrue(transactionName.getText().equals("Transactiekosten (VVV Cadeaukaart )"));
			 Assert.assertTrue(Double.parseDouble(transactionFair.getText().replaceAll("€ ", "").replaceAll(",", "."))==TransactionFair);
			 Assert.assertTrue(Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")) == grandTotalInCart+TransactionFair );
			 log.info("Transaction cost and total cost on cart looks fine for payment mode "+mode);
			 return true;
		 }
		 else if(mode.equals("CHIPKNIP")){
			 TransactionFair = Input.CHIPKNIPTrandsactionFee;
			 JavascriptExecutor executor = (JavascriptExecutor)driver;
	        	executor.executeScript("arguments[0].click()", Chipknip);	
			 //iDeal.click();
	         //Thread.sleep(3000);	
			 Assert.assertTrue(transactionName.getText().equals("Transactiekosten (CHIPKNIP)"));
			 Assert.assertTrue(Double.parseDouble(transactionFair.getText().replaceAll("€ ", "").replaceAll(",", "."))==TransactionFair);
			 Assert.assertTrue(Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")) == grandTotalInCart+TransactionFair );
			 log.info("Transaction cost and total cost on cart looks fine for payment mode "+mode);
			 return true;
		 }
		 else if(mode.equals("Contant")){
			 TransactionFair = 0; //may later we can pull from object repository
			 JavascriptExecutor executor = (JavascriptExecutor)driver;
	        	executor.executeScript("arguments[0].click()", Contant);	
			 //iDeal.click();
	         //Thread.sleep(3000);	
	         grandTotalInCart = Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", "."));
	        
			 Assert.assertTrue(Double.parseDouble(meals.grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")) == grandTotalInCart+TransactionFair );
			 log.info("Transaction cost and total cost on cart looks fine for payment mode "+mode);
			 return true;
		 }
      	log.info("Transaction cost and total cost on cart --NOT-- looks fine for payment mode "+mode);
      	Thread.sleep(5000);
		return false;
	}
	 
	 public void deleteCartItems() throws InterruptedException {
		//Thread.sleep(3000);
		int size =delete.size();
		 for (int i = 0; i < size;) {
			delete.get(i).click();
			//Thread.sleep(2000);
			size = delete.size();
		}

	}
	 
	 public void orderCancellation() throws InterruptedException {
		 	  cancelOrder.click();
		 	  Thread.sleep(10000);
			  confirmOrderCancel.click();
		      Thread.sleep(6000); //message display taking some time on app, it not usual flow.
		      
		      try{
		    	  cancelOrder.click();
			 	  Thread.sleep(6000);
				  confirmOrderCancel.click();
			      Thread.sleep(6000); //message display taking some time on app, it not usual flow.
		      }catch (Exception e) {
				// TODO: handle exception
			 }
		      
		    //Validate cancellation message
	    	//Thread.sleep(6000);
	    	//new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(oc.orderCancellationMessage));
	    	
	    	Thread.sleep(10000); //message display taking some time on app, it not usual flow.
	    	Assert.assertTrue(orderCancellationMessage.getText().equals(Input.iDEALorderCancellationMessage));
		 
	}

	public void acceptCookiesPopUp() {
		try{
    		Thread.sleep(8000);
    		acceptCookies.click();
    		Thread.sleep(3000);
    	}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
