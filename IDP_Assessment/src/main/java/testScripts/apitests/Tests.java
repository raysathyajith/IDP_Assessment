package testScripts.apitests;

import java.util.HashMap;

import org.apache.hc.core5.http.HttpResponse;
import org.testng.annotations.*;

import base.API;

public class Tests extends API {

	//String BaseURL = "https://api.zotero.org";//https://dummyapi.io/
	String BaseURL = "https://reqres.in/";
	String PathURL = "";
	String Method = "";
	String AuthType = "";
	HashMap<String, String> Headers;
	
	@BeforeTest
	public void setup() {
		
		
	}
	
	@BeforeMethod
	public void beforeMethod() {
		Headers = new HashMap<String,String>();
	}
	
	@Test
	public void getTopLevelCollections() {
		PathURL = "api/users/2";
		formURL(BaseURL, PathURL);
		setAuthType("noauth");
		setMethod("GET");
		
		Headers.put("Content-Type", "application/json");
		setHeader(Headers);
		try {
		Connect();
		HttpResponse response = (HttpResponse) getResponseObject();
		System.out.println(response.getCode());
		System.out.println(response.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
