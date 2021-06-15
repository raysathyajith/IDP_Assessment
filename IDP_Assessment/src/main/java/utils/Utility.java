package utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class Utility {

	public String readJson(String response, String path) {
		DocumentContext context = JsonPath.parse(response);
		Object json = context.read(path);
		if(json != null) {
			return json.toString();
		}
		return null;
	}
}
