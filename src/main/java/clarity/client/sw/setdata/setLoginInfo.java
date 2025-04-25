package clarity.client.sw.setdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import clarity.client.sw.values.loginValues;
import clarity.client.sw.values.projectValues;

public class setLoginInfo {
	
	public void setLogininfo(String protocol, String address, String email, String password) {
		
		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();
		
		// Set the uri with http or https according to fossid.dprotocol
		if(protocol.equals("http")) {
			lvalues.setServerUri("http://" + address);
			
			//check "fossid.domain" to add / in front of api.php
			String temp = lvalues.getServerUri();
			temp = temp.substring(temp .length() - 1, temp.length());
			
			if(temp.equals("/")) {
				lvalues.setServerApiUri("http://" + address + "/2/apis/");
				lvalues.setServerUploadUri("http://" + address + "/2/apis/");
			} else {
				lvalues.setServerApiUri("http://" + address + "/2/apis/");
				lvalues.setServerUploadUri("http://" + address + "/2/apis/");
			}				
		} else if(protocol.equals("https")) {
			lvalues.setServerUri("https://" + address);
			
			//check "fossid.domain" to add / in front of api.php
			String temp = lvalues.getServerUri();				
			temp = temp.substring(temp .length() - 1, temp.length());
			
			if(temp.equals("/")) {
				lvalues.setServerApiUri("https://" + address + "/2/apis/");
				lvalues.setServerUploadUri("https://" + address + "/2/apis/");
			} else {
				lvalues.setServerApiUri("https://" + address + "/2/apis/");
				lvalues.setServerUploadUri("https://" + address + "/2/apis/");
			}
		}
		
		lvalues.setEmail(email);
		lvalues.setaccessToken(getToken(email, password, lvalues, pvalues));		
		
	}
	
	private String getToken(String email, String password, loginValues lvalues, projectValues pvalues) {

		String accesstoken = "";
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("email", email);
        rootObject.put("password", password);
        
        HttpPost httpPost = new HttpPost(lvalues.getServerApiUri() + "token");
		HttpClient httpClient = HttpClientBuilder.create().build();		
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);		
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				System.out.println("FAILED: Please, check the Clarity protocol or url values");				
				pvalues.setSuccess(0);
				System.out.println();
				System.out.println("FAILED: HTTP Error code: " + httpClientResponse.getStatusLine().getStatusCode());
	        	System.out.println("FAILED: Please, check the Clarity email or password values");
				System.out.println();
				System.exit(1);		
			}
		
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();
			
			JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());
	        
	        accesstoken = jsonObj.get("access_token").toString();
	        
		} catch (Exception e) {
			e.printStackTrace();
			pvalues.setSuccess(0);
			System.exit(1);
		}	
		
		return accesstoken;
		
	}
	
}
