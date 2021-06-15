package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.Methods;

public class Home extends Methods{

	WebDriver driver;
	WebElement e;

	public Home(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	By inpSearchBox = By.xpath("//div[@class='input-group']/input");
	By btnCite = By.xpath("//div[@class='id-input-container']/button");
	By btnManualEntry = By.xpath("//div[@class='cite-tools']/button[contains(text(),'Manual Entry')]");

	
	//Functions
	
	public void setSearchBox(String keysToSend) throws Exception {
		e = driver.findElement(inpSearchBox);
		Set(e, keysToSend);
	}

	public void clickCiteButton() throws Exception {
		e = driver.findElement(btnCite);
		Click(e);
		
	}
}
