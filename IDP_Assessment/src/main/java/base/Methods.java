package base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Methods {

	WebDriver driver;

	public Methods(WebDriver driver) {
		this.driver = driver;
	}

	public void Set(WebElement element, String keysToSend) throws Exception {
		try {
			element.sendKeys(keysToSend);
		} catch (Exception e) {
			throw e;
		}
	}

	public void Click(WebElement element) throws Exception {
		try {
				element.click();
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * public boolean isEnabled(WebElement element) { return element.isEnabled(); }
	 * 
	 * public boolean isDisplayed(WebElement element) { if (isEnabled(element)) {
	 * return element.isDisplayed(); } else { throw new
	 * ElementNotInteractableException("Element Not Enabled"); } }
	 */

	public WebElement getWebElement(By value) {
		List<WebElement> elements = getWebElements(value);
		if (elements.size() > 0) {
			return elements.get(0);
		} else {
			return null;
		}
	}

	public List<WebElement> getWebElements(By value) {
		return driver.findElements(value);
	}
}
