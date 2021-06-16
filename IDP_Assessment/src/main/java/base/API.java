package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class API {

	HttpURLConnection connect;

	private String iURL;
	private String AuthType;
	private String AuthValue;
	private String Method;
	private String Body;
	private String Response;
	private HashMap<String, String> Headers;

	public void setHeader(HashMap<String, String> Headers) {
		this.Headers = Headers;
	}

	public String getBody() {
		return new String(Body);
	}

	public void setBody(String body) {
		Body = body;
	}

	public String getMethod() {
		return new String(Method);
	}

	public void setMethod(String method) {
		Method = method;
	}

	public String getResponse() {
		return new String(Response);
	}

	public String getAuthType() {
		return new String(AuthType);
	}

	public void setAuthType(String Auth, String value) {
		this.AuthType = Auth.toLowerCase();
		this.AuthValue = value;
	}

	public void formURL(String BaseURL, String PathURL) {
		String formedURL = BaseURL + PathURL;
		try {
			new URL(formedURL);
			iURL = formedURL;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setURL(String completeURL) {
		try {
			new URL(completeURL);
			iURL = completeURL;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setHeaders(HashMap<String, String> map) {
		for (Map.Entry<String, String> m : map.entrySet()) {
			connect.setRequestProperty(m.getKey(), m.getValue());
		}
		connect.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	}

	public Object getResponseObject() throws IOException {
		return connect.getContent();
	}

	private String getAPIResponse() throws IOException {
		BufferedReader br = null;
		InputStream is = null;
		StringBuilder build = null;
		try {
			is = connect.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String temp;
			build = new StringBuilder();
			while ((temp = br.readLine()) != null) {
				build.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
			is.close();
		}
		return build.toString();
	}

	public String readResponse() throws IOException {
		Response = getAPIResponse();
		return Response;
	}

	public HttpURLConnection Connect() throws Exception {
		try {
			URL url = new URL(iURL);
			connect = (HttpURLConnection) url.openConnection();
			System.out.println("URL :"+ iURL);
			connect.setRequestMethod(Method);
			System.out.println("Method :"+ Method);
			setHeaders(Headers);
			System.out.println("Added ["+ Headers.size() +"] headers");
			switch (AuthType) {
			case "bearer":
				connect.setRequestProperty("Authorization", "Bearer " + AuthValue);
				break;
			case "noauth":
				break;
			default:
				throw new Exception("Not Handled yet");
			}
			System.out.println("AuthType :"+ AuthType);
			if (!Method.equals("GET")) {
				connect.setDoOutput(true);
				OutputStream os = connect.getOutputStream();
				os.write(Body.toString().getBytes());
				os.flush();
				os.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		return connect;
	}
}
