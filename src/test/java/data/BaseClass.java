package data;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class BaseClass {
	
	WebDriver driver;
	
	public BaseClass(){
		driver = new FirefoxDriver();
	}
	
	public void openUrl(String url){
		driver.get(url);
	}
	
	public void sendKeys(String locator, String locatorValue, String keyword){
		if(locator.equals("id"))
			driver.findElement(By.id(locatorValue)).sendKeys(keyword);
		else if(locator.equals("name"))
			driver.findElement(By.name(locatorValue)).sendKeys(keyword);
		else if(locator.equals("xpath"))
			driver.findElement(By.xpath(locatorValue)).sendKeys(keyword);
		else{
			System.out.println("INVALID LOCATOR");
		}
	}
	
	
	public void click(String locator){
		if(locator.equals("id"))
			driver.findElement(By.id(locator)).click();
	}
	
	public void verifyUrlContains(String url){
		Assert.assertTrue(driver.getCurrentUrl().contains(url), "Wrong URL");
	}
	
	public void verifyTitleContains(String title){
		Assert.assertTrue(driver.getTitle().contains(title), "Wrong Title");
	}
	
	//On which element we need to press enter
	public void pressEnterKey(String locator,String locatorValue){
		if(locator.equals("name"))
			driver.findElement(By.name(locatorValue)).sendKeys(Keys.ENTER);
	}
	
	
	public void quitDriver(){
		driver.quit();
	}

}
