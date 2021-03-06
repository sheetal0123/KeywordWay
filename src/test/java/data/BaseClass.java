package data;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class BaseClass {

	WebDriver driver;

	public BaseClass() {
		String path = System.getProperty("user.dir");
		System.setProperty("webdriver.gecko.driver", path + "/src/test/resources/drivers/geckodriver");
		driver = new FirefoxDriver();
	}

	public void quitDriver() {
		driver.quit();
	}

	public WebElement getWebElement(String locator, String locatorValue) {

		WebElement webElement = null;

		switch (locator) {
		case "id":
			webElement = driver.findElement(By.id(locatorValue));
			break;
		case "name":
			webElement = driver.findElement(By.name(locatorValue));
			break;
		case "xpath":
			webElement = driver.findElement(By.xpath(locatorValue));
			break;
		case "css":
			webElement = driver.findElement(By.cssSelector(locatorValue));
			break;
		case "className":
			webElement = driver.findElement(By.className(locatorValue));
			break;
		case "linkText":
			webElement = driver.findElement(By.linkText(locatorValue));
			break;
		}
		return webElement;
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void sendKeys(WebElement webElement, String keyword) {
		webElement.sendKeys(keyword);
	}

	public void click(WebElement webElement) {
		webElement.click();
	}

	public void pressEnterKey(WebElement webElement) {
		webElement.sendKeys(Keys.ENTER);
	}

	public boolean verifyUrlContains(String url) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Assert.assertTrue(driver.getCurrentUrl().contains(url), "Wrong URL");
		return driver.getCurrentUrl().contains(url);
	}

	// public void verifyTitleContains(String title) {
	// Assert.assertTrue(driver.getTitle().contains(title), "Wrong Title");
	// }

	public boolean verifyTitleContains(String title) {
		return driver.getTitle().contains(title);
		// Assert.assertTrue(driver.getTitle().contains(title), "Wrong Title");
	}

}
