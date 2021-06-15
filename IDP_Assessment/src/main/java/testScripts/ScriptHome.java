package testScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import pageObjects.Home;

public class ScriptHome {

	private WebDriver driver;
	Home homePO;
	
	String URL = "https://zbib.org/";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setupTest() {
        driver = new ChromeDriver();
        homePO = new Home(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    @Test
    public void TC_01_PositiveScenario() {
    	try {
    		driver.get(URL);
			homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    //Broken links
    //Functionality test
     // -> Delete All
     // -> Adding
     // -> Download
     // -> Link to newly created
     // -> Manual Entry
     // -> Edit
    
    //Cross browser testing
    //Invalid values
    //Security testing

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
