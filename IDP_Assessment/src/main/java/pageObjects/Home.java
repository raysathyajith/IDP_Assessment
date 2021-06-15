package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Methods;

public class Home extends Methods{

	WebDriver driver;
	WebElement e;
	WebDriverWait wait;

	public Home(WebDriver driver) {
		super(driver);
		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
	}

	By inpSearchBox = By.xpath("//div[@class='input-group']/input");
	By btnCite = By.xpath("//div[@class='id-input-container']/button");
	By btnManualEntry = By.xpath("//div[@class='cite-tools']/button[contains(text(),'Manual Entry')]");
	By btnDeleteAll = By.xpath("//button[contains(text(),'Delete All')]");
	By btnDelete = By.xpath("//div[@class='buttons']/button[contains(text(),'Delete')]");

	By txtCSLentry = By.xpath("//div[@class='csl-entry']");
	
	By btnCreate = By.xpath("//button[contains(text(),'Create')]");
	By btnView = By.xpath("//a[contains(text(),'View')]");
	By btnClose = By.xpath("//div[@class=\"actions\"]/button[contains(text(),'Close')]");
	
	
	//Functions
	
	public void ClickElement(By value) throws Exception {
		e = driver.findElement(value);
		Click(e);
	}
	
	public void setSearchBox(String keysToSend) throws Exception {
		e = driver.findElement(inpSearchBox);
		Set(e, keysToSend);
	}

	public void clickCiteButton() throws Exception {
		ClickElement(btnCite);
	}
	
	public void deleteAllBibliography() throws Exception {
		ClickElement(btnDeleteAll);
		wait.until(ExpectedConditions.presenceOfElementLocated(btnDelete));
		ClickElement(btnDelete);
	}
	
	public List<String> getCSLEntries() {
		List<WebElement> entries = driver.findElements(txtCSLentry);
		List<String> sentries = new ArrayList<>();
		for(WebElement e: entries) {
			sentries.add(e.getText());
		}
		return sentries;
	}
	
	public void clickView() throws Exception {
		wait.until(ExpectedConditions.presenceOfElementLocated(btnView));
		ClickElement(btnView);
	}
	
	public void clickCreate() throws Exception {
		ClickElement(btnCreate);
	}
	
	public void clickClose() throws Exception {
		if(driver.findElements(btnClose).size()>0) {
			ClickElement(btnClose);
		}
	}
}
