package testScripts.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.google.common.collect.Lists;

import base.API;
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

	@Test(enabled = true)
	public void TC_01_PositiveScenario() {
		try {
			homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			System.out.println("Successfully clicked on Cite button");
			homePO.deleteAllBibliography();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = true)
	public void TC_02_VerifyTitle() {
		try {
			String title = "ZoteroBib: Fast, free bibliography generator - MLA, APA, Chicago, Harvard citations";
			Assert.assertEquals(driver.getTitle(), title, "The title is not as expected");
			System.out.println("The title is as expected, title: " + title);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = true)
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

	@Test(enabled = true)
	public void TC_04_VerifyVersion() {
		try {
			homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			System.out.println("Successfully clicked on Cite button");

			// If any previous record present
			homePO.clickClose();

			List<String> org = homePO.getCSLEntries();

			// Create link
			homePO.clickCreate();
			homePO.clickView();

			// ValidateCSL entries
			List<String> exp = homePO.getCSLEntries();

			Assert.assertTrue(org.equals(exp));
			System.out.println("Both lists are equal.");
			homePO.deleteAllBibliography();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true)
	public void TC_05_EditItem() {
		try {
			homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			homePO.editItem("_GoogleTitle_");

			List<String> org = homePO.getCSLEntries();

			Assert.assertEquals(org.size(), 1, "CSL entries are more than one");

			Assert.assertTrue(org.get(0).contains("_GoogleTitle_"), "Given title is not present in Item");

			homePO.deleteAllBibliography();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true)
	public void TC_06_VerifyVersionwithLink() {
		try {
			homePO.deleteAllBibliography();
			homePO.setSearchBox("https://www.google.com/");
			homePO.clickCiteButton();
			System.out.println("Successfully clicked on Cite button");

			// If any previous record present
			homePO.clickClose();

			// Create link
			homePO.clickCreate();
			homePO.clickCopyURL();

			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable t = clipboard.getContents(null);
			String copiedContents = "";
			if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				t.getTransferData(DataFlavor.stringFlavor);
				copiedContents = (String) t.getTransferData(DataFlavor.stringFlavor);
				System.out.println("Clipboard contents: " + copiedContents);
			}

			homePO.clickView();
			Assert.assertEquals(driver.getCurrentUrl(), copiedContents, "URL is not same");

			homePO.deleteAllBibliography();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true)
	public void brokenlinkValidation() {
		try {
			Set<String> links = homePO.getAllLinks();
			System.out.println("All links");

			API api = new API();
			api.setMethod("GET");
			api.setAuthType("noauth", null);
			HashMap<String, String> Headers = new HashMap<String, String>();
			api.setHeader(Headers);
			int sno=1;
			List<String> linkswithresponse = new ArrayList<String>();
			
			for (String s : links) {
				api.setURL(s);
				HttpURLConnection con = api.Connect();
				linkswithresponse.add(sno++ + ". "+ s+ ", Response code : " + con.getResponseCode());
			}
			
			
			for(String s: linkswithresponse) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
