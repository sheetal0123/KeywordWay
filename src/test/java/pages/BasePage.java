package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

/**
 *  Base class contains all browser specific methods
 *  All high level methods will come here
 *
 */
public class BasePage {

	WebDriver driver;
	public static Properties properties;
	String cmd_drv;
	String drv;
	
	
	public void getProperties() {
		// reading from properties file
		properties = new Properties();
		try {
			//todo: path shd be relative
			properties.load(new FileInputStream(
					"/Users/sheetalsingh/Documents/workspace/KeywordWay/keyword.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// reading from command line
		cmd_drv = System.getProperty("driver");

		// priority will be given to cmd driver
		if (cmd_drv != null && !cmd_drv.trim().isEmpty()) {
			drv = cmd_drv;
			System.out.println("CMD Driver: " + drv);
		} else {
			drv = properties.getProperty("driver");
			System.out.println("Properties Driver: " + drv);
		}
	}
	
	
	
	public WebDriver initDriver() {
		if (drv.equals("firefox")) {
			return new FirefoxDriver();
		} else if (drv.equals("chrome")) {
			//todo
		} else {
			System.out.println("Driver: no driver selected");
		}
		return null;
	}
	
	
	public void openUrl(String url){
		driver.get(url);
	}
	
	public void sendKeys(By element, String keyword){
		driver.findElement(element).sendKeys(keyword);
	}
	
	public void click(By element){
		driver.findElement(element).click();
	}
	
	public void verifyUrlContains(String url){
		Assert.assertTrue(driver.getCurrentUrl().contains(url), "Wrong URL");
	}
	
	public void verifyTitleContains(String title){
		Assert.assertTrue(driver.getTitle().contains(title), "Wrong Title");
	}
	
	
}
