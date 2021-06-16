package testScripts.apitests;

import java.net.HttpURLConnection;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.*;

import base.API;
import utils.Utility;

public class Tests extends API {

	final String BaseURL = "https://api.zotero.org/";
	String PathURL = "";
	String Method = "";
	String AuthType = "";
	HashMap<String, String> Headers;

	final String key_ReadAccess = "lffUSc70L3NoDk11lp6Dleah";
	final String key_WriteAccess = "I8qf8gPTk4SpFeuGAoXgrhfe";
	final String userID = "8147058";

	Utility utils;
	
	String addedItem;
	String version;
	
	@BeforeTest
	public void setup() {

	}

	@BeforeMethod
	public void beforeMethod() {
		Headers = new HashMap<String, String>();
		utils = new Utility();
	}
	
	@BeforeMethod
	public void afterMethod() {
		Headers = null;
	}

	@Test(groups = "Positive", enabled=true)
	public void TC_01_getItems() {
		PathURL = "users/"+userID+"/items?v=3";
		formURL(BaseURL, PathURL);
		setAuthType("Bearer", key_ReadAccess);
		setMethod("GET");
		
		Headers.put("Content-Type", "application/json");
		setHeader(Headers);
		try {
			HttpURLConnection connect = Connect();
			int code = connect.getResponseCode();
			Assert.assertEquals(code, 200, "Response code is not as expected!");
			System.out.println(readResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = "Positive", enabled=true)
	public void TC_02_AddNewBook() {
		PathURL = "users/"+userID+"/items";
		formURL(BaseURL, PathURL);
		setAuthType("Bearer", key_WriteAccess);
		setMethod("POST");
		String ibody = "[\r\n" + 
				"    {\r\n" + 
				"        \"itemType\": \"book\",\r\n" + 
				"        \"title\": \"My Book\",\r\n" + 
				"        \"creators\": [\r\n" + 
				"            {\r\n" + 
				"                \"creatorType\": \"author\",\r\n" + 
				"                \"firstName\": \"Sam\",\r\n" + 
				"                \"lastName\": \"McAuthor\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"creatorType\": \"editor\",\r\n" + 
				"                \"name\": \"John T. Singlefield\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"tags\": [\r\n" + 
				"            {\r\n" + 
				"                \"tag\": \"awesome\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"tag\": \"rad\",\r\n" + 
				"                \"type\": 1\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"collections\": [],\r\n" + 
				"        \"relations\": {\r\n" + 
				"            \"owl:sameAs\": \"http://zotero.org/groups/1/items/JKLM6543\",\r\n" + 
				"            \"dc:relation\": \"http://zotero.org/groups/1/items/PQRS6789\",\r\n" + 
				"            \"dc:replaces\": \"http://zotero.org/users/1/items/BCDE5432\"\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"]";
		
		setBody(ibody);
		Headers.put("Content-Type", "application/json");
		setHeader(Headers);
		try {
			HttpURLConnection connect = Connect();
			int code = connect.getResponseCode();
			Assert.assertEquals(code, 200, "Response code is not as expected!");
			System.out.println("Response Code:"+ code);
			String response = readResponse();
			System.out.println("API Response :\n" + response);
			String jsonContent = utils.readJson(response, "$.failed");
			Assert.assertEquals(jsonContent, "{}");
			addedItem = utils.readJson(response, "$.success.0");
			version = utils.readJson(response, "$.successful.0.version");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = "Negative", enabled=true)
	public void TC_03_AddNewBook() {
		System.out.println("Negative Test scenario: Testing Scope of access | Adding new book using read access key");
		PathURL = "users/"+userID+"/items";
		formURL(BaseURL, PathURL);
		setAuthType("Bearer", key_ReadAccess);
		setMethod("POST");
		String ibody = "[\r\n" + 
				"    {\r\n" + 
				"        \"itemType\": \"book\",\r\n" + 
				"        \"title\": \"My Book\",\r\n" + 
				"        \"creators\": [\r\n" + 
				"            {\r\n" + 
				"                \"creatorType\": \"author\",\r\n" + 
				"                \"firstName\": \"Sam\",\r\n" + 
				"                \"lastName\": \"McAuthor\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"creatorType\": \"editor\",\r\n" + 
				"                \"name\": \"John T. Singlefield\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"tags\": [\r\n" + 
				"            {\r\n" + 
				"                \"tag\": \"awesome\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"tag\": \"rad\",\r\n" + 
				"                \"type\": 1\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"collections\": [],\r\n" + 
				"        \"relations\": {\r\n" + 
				"            \"owl:sameAs\": \"http://zotero.org/groups/1/items/JKLM6543\",\r\n" + 
				"            \"dc:relation\": \"http://zotero.org/groups/1/items/PQRS6789\",\r\n" + 
				"            \"dc:replaces\": \"http://zotero.org/users/1/items/BCDE5432\"\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"]";
		
		setBody(ibody);
		Headers.put("Content-Type", "application/json");
		setHeader(Headers);
		try {
			HttpURLConnection connect = Connect();
			int code = connect.getResponseCode();
			Assert.assertEquals(code, 403, "Response code is not as expected!");
			System.out.println("Response Code:"+ code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = "Positive", enabled=true, dependsOnMethods = "TC_02_AddNewBook")
	public void TC_04_DeleteNewBook() {
		System.out.println("Positive Test scenario: Deleting item");
		PathURL = "users/"+userID+"/items/"+ addedItem;
		formURL(BaseURL, PathURL);
		setAuthType("Bearer", key_WriteAccess);
		setMethod("DELETE");
		
		Headers.put("Content-Type", "application/json");
		Headers.put("If-Unmodified-Since-Version", version);
		setHeader(Headers);
		try {
			HttpURLConnection connect = Connect();
			int code = connect.getResponseCode();
			Assert.assertEquals(code, 204, "Response code is not as expected!");
			System.out.println("Response Code:"+ code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
