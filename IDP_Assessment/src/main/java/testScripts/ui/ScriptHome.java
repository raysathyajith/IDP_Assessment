package testScripts.ui;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
        driver.get(URL);
        driver.manage().window().maximize();
    }
    
    @Test(enabled=false)
    public void TC_01_PositiveScenario() {
    	try {
			homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			System.out.println("Successfully clicked on Cite button");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test(enabled=false)
    public void TC_02_VerifyTitle() {
    	try {
    		String title = "ZoteroBib: Fast, free bibliography generator - MLA, APA, Chicago, Harvard citations";
			Assert.assertEquals(driver.getTitle(), title, "The title is not as expected");
			System.out.println("The title is as expected, title: " + title);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test(enabled=false)
    public void TC_03_DeleteALL() {
    	try {
    		homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			System.out.println("Successfully clicked on Cite button");
    		homePO.deleteAllBibliography();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test(enabled=true)
    public void TC_04_VerifyVersion() {
    	try {
    		homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			System.out.println("Successfully clicked on Cite button");
    		
			//If any previous record present
			homePO.clickClose();
			
			List<String> org = homePO.getCSLEntries();
			
			//Create link
			homePO.clickCreate();
			homePO.clickView();
			
			//ValidateCSL entries
			List<String> exp = homePO.getCSLEntries();
			
			Assert.assertTrue(org.equals(exp));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //Broken links
    //Functionality test
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
