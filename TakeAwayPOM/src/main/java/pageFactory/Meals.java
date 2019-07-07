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
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import automationLibrary.DriverClass;


public class Meals {
		
		WebDriver driver;
		Logger log;
		//Locator all meals
		@FindBy(xpath="//div[@class='meal__description-texts']")
		public List<WebElement> meals ;
		
		//Locators for toppins
		@FindBy(xpath="//span[contains(text(),'Tomato')]")
		public WebElement firstToppins ;
				
		
		@FindBy(xpath="//span[contains(text(),'Potato')]")
		public WebElement secondToppins ;
		
		@FindBy(xpath="//button[@class='cartbutton-button cartbutton-button-sidedishes add-btn-icon']")
		public WebElement cartBtn ;
		
		//validate cart items 
		//Locator all meals
		@FindBy(xpath="//div[@id='products']//div[@class='cart-single-meal']")
		public List<WebElement> mealsSelectedOncart ;
		
		@FindBy(xpath="//div[@class='cart-row row-sum js-total-costs-row']//span[@class='cart-sum-price']")
		public WebElement grandTotal;
		
		
		
		 public Meals(WebDriver driver) {
			    this.driver = driver;
		        //This initElements method will create all WebElements
		        //PageFactory.initElements(driver, this);
			    PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		        log = Logger.getLogger("devpinoyLogger");
		}
		 
		 //Select required meals
		 public double selectMealoritems(String meal, String topins) throws InterruptedException {
			 String price = null; //price on application is not in format.. ','is used as '.' so, we have to format in script!, hence taking as string instead of float!
			 Thread.sleep(4000);
			
			 //log.info("Found "+meals.size()+" items! Cheers!");
			 for (int i=0; i<meals.size();i++){
				 
				 //log.info(meals.get(i).findElement(By.xpath("span")).getText());
			      if(meals.get(i).findElement(By.xpath("span")).getText().equals(meal)){
			      
			        if (meal.equals("Duck Breast")) {
			        	meals.get(i).findElement(By.xpath("span")).click();	
			        	Thread.sleep(3000);
			        	if(!topins.equals(null) && topins.equalsIgnoreCase("tomato")){
			        		firstToppins.click();
			        	}
			        	else if(!topins.equals(null) && topins.equalsIgnoreCase("potato")){
			        		secondToppins.click();
			        	}
			        	
			        	price= meals.get(i).findElement(By.xpath("div[@class='meal__price']")).getText();
			        	cartBtn.click();
			        	return Double.parseDouble(price.replaceAll("€ ", "").replaceAll(",", "."))+3; //toppins extra € 3
			       
					}
			        else{
			        	JavascriptExecutor executor = (JavascriptExecutor)driver;
			        	executor.executeScript("arguments[0].click()", meals.get(i).findElement(By.xpath("span")));	
			        	price= meals.get(i).findElement(By.xpath("div[@class='meal__price']")).getText();
			        	return Double.parseDouble(price.replaceAll("€ ", "").replaceAll(",", "."));
			      	
			        }
			      }
			      }
			 log.info("Added "+meal+" to the cart and price is € "+ price);
			 return Double.parseDouble(price);
		}
		 
		
		 
		 public boolean validateCart(Map mealsANDprice, Double totalPrice ) throws InterruptedException {
			 
			 //Thread.sleep(4000);
			 
			 Assert.assertTrue(mealsSelectedOncart.size() == mealsANDprice.size());
			 
			 int i =0;
			 Iterator it = mealsANDprice.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        //log.info(pair.getKey() + " = " + pair.getValue());
			        
			        Assert.assertTrue(pair.getKey().equals(mealsSelectedOncart.get(i).findElement(By.xpath("div/span[@class='cart-meal-name']")).getText()));
			        Assert.assertTrue(pair.getValue().equals(Double.parseDouble(mealsSelectedOncart.get(i++).findElement(By.xpath("div/span[@class='cart-meal-price']")).getText().replaceAll(",", ".").replaceAll("€ ", ""))));
			        
			    }
			    System.out.println(Double.parseDouble(grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")));
			  Assert.assertTrue(Double.parseDouble(grandTotal.getText().replaceAll("€ ", "").replaceAll(",", ".")) == totalPrice);
			 log.info("Cart is validated against added meals/items, order, items prices and total cost looks fine!!");   
			return true;
		}

}
