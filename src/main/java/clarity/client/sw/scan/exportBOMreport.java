package clarity.client.sw.scan;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import clarity.client.sw.values.loginValues;
import clarity.client.sw.values.projectValues;

public class exportBOMreport {
	
	public static void exportReport() {
		
		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();
		
		// Wait for the scan process finish
		try {
		    System.out.println();
	        System.out.print("Clarity Scan is processing.");
	        String checkstatus = "";
            JSONParser jsonParser = new JSONParser();
            
	        JSONObject scanprocess = null;
	        while(!checkstatus.equals("Scan Completed")) {
	        	scanprocess = checkScanstatus(lvalues, pvalues);
	        	
	            JSONObject jsonObj = (JSONObject) jsonParser.parse(scanprocess.get("response").toString());           
	            checkstatus = jsonObj.get("detail_status").toString();	            
	        	System.out.print(".");	    
				Thread.sleep(1000);
	        }
	        System.out.println("\n" + "Clarity Scan process is completed ");
	        //System.out.println(scanprocess.toString());
	        System.out.println();
	        JSONObject jsonObj1 = (JSONObject) jsonParser.parse(scanprocess.get("response").toString());
	        JSONObject jsonObj2 = (JSONObject) jsonParser.parse(jsonObj1.get("security_issues").toString());
	        System.out.println("Security Count - Total: " + jsonObj2.get("total") + " | critical: " + jsonObj2.get("critical")
	        	+ " | high: " + jsonObj2.get("high") + " | medium: " + jsonObj2.get("medium") + " | low: " + jsonObj2.get("low"));
	        JSONObject jsonObj3 = (JSONObject) jsonParser.parse(jsonObj1.get("licenses_count").toString());
	        System.out.println("License Count - Total: " + jsonObj3.get("total") + " | protective: " + jsonObj3.get("protective")
        	+ " | permissive: " + jsonObj3.get("permissive") + " | others: " + jsonObj3.get("others"));        
	        System.out.println();
	        
	        exportSBOM(lvalues, pvalues);
	        
		} catch (InterruptedException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private static JSONObject checkScanstatus(loginValues lvalues, projectValues pvalues) {
		JSONObject checkstatus = null;
		String requestURL = lvalues.getServerApiUri() + "binary/" + pvalues.getBinaryid();
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestURL);	
		
		try {
			getRequest.addHeader("Content-Type", "application/json");
			getRequest.addHeader("Authorization", "Bearer " + lvalues.getaccessToken());	
			
			HttpResponse httpClientResponse = httpClient.execute(getRequest);
		
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {								
				pvalues.setSuccess(0);
				System.out.println();
				System.out.println("FAILED: HTTP Error code: " + httpClientResponse.getStatusLine().getStatusCode());
				System.out.println();
				System.exit(1);	
			}	
								
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();
			
            JSONParser jsonParser = new JSONParser();
            checkstatus = (JSONObject) jsonParser.parse(result.toString());
               
		} catch (Exception e) {
			pvalues.setSuccess(0);
			System.out.println();
			System.out.println("FAILED: Please check Scan progress.");
			System.out.println();
			System.exit(1);
			e.printStackTrace();
		}	
		
		return checkstatus;
	}
	
	private static void exportSBOM(loginValues lvalues, projectValues pvalues) {

		String requestURL = lvalues.getServerApiUri() + "export/" + pvalues.getBinaryid();
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestURL);	
		
		try {
			getRequest.addHeader("Content-Type", "application/json");
			getRequest.addHeader("Authorization", "Bearer " + lvalues.getaccessToken());	
			
			HttpResponse httpClientResponse = httpClient.execute(getRequest);
		
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {								
				pvalues.setSuccess(0);
				System.out.println();
				System.out.println("FAILED: HTTP Error code: " + httpClientResponse.getStatusLine().getStatusCode());
				System.out.println();
				System.exit(1);	
			}	
			
	        LocalTime now = LocalTime.now();	        
			String excelname = pvalues.getProjectName() + "_" + pvalues.getVersion() + "_" + now.getHour()+now.getMinute()+now.getSecond() + ".xlsx"; 
			
            HttpEntity entity = httpClientResponse.getEntity();
            if (entity != null) {
                // try-with-resources 구문을 사용하여 InputStream과 FileOutputStream 자동 종료 보장
                try (InputStream inputStream = entity.getContent();
                     FileOutputStream fileOutputStream = new FileOutputStream(excelname)) {

                    byte[] buffer = new byte[8192]; // 8KB 버퍼
                    int bytesRead;
                    long totalBytesRead = 0;

                    // InputStream에서 읽어 FileOutputStream으로 쓰기
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                    }
                    System.out.println("The SBOM report [File Size: " + totalBytesRead + " / File Name: " + excelname + "] is created");
                }
            } else {
                System.err.println("There is no `Entity` response");
            }
		       
		} catch (Exception e) {
			pvalues.setSuccess(0);
			System.out.println();
			System.out.println("FAILED: Please check SBOM Export.");
			System.out.println();
			System.exit(1);
			e.printStackTrace();
		}	
	}

}
