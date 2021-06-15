package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class API {

	HttpURLConnection connect;

	private String iURL;
	private String AuthType;
	private String Method;
	private String Body;
	private String Response;
	private HashMap<String, String> Headers;

	public void setHeader(HashMap<String, String> Headers) {
		this.Headers = Headers;
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

	public void setAuthType(String Auth) {
		this.AuthType = Auth.toLowerCase();
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

	private void setHeaders(HashMap<String, String> map) {
		for (Map.Entry<String, String> m : map.entrySet()) {
			connect.setRequestProperty(m.getKey(), m.getValue());
		}
	}
	
	public Object getResponseObject() throws IOException {
		return connect.getContent();
	}
	
	public String readStream() throws IOException {
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

		} finally {
			br.close();
			is.close();
		}
		return build.toString();
	}
	

	public HttpURLConnection Connect() throws Exception {
		try {
			URL url = new URL(iURL);
			connect = (HttpURLConnection) url.openConnection();

			connect.setRequestMethod(Method);
			setHeaders(Headers);

			switch (AuthType) {
			case "bearer":
				connect.setRequestProperty("Authorization", "");
				break;
			case "noauth":
				break;
			default:
				throw new Exception("Not Handled yet");
			}

			if (Method.contentEquals("GET")) {
				if(connect.getResponseCode() == 200) {
					Response = readStream();
				}
			} else {
				connect.setDoOutput(true);
				OutputStream os = connect.getOutputStream();
				os.write(Body.toString().getBytes());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connect.disconnect();
		}
		return connect;
	}
}
