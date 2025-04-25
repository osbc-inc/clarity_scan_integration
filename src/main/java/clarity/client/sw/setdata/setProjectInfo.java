package clarity.client.sw.setdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import clarity.client.sw.values.loginValues;
import clarity.client.sw.values.projectValues;;

public class setProjectInfo {
	
	public void setInfo(String project, String version) {
		
		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();		
		
		pvalues.setProjectName(project);
		pvalues.setVersion(version);				
        setProjectVersionId(project, version, lvalues, pvalues);
        
	}
	
	
	/** 
	 * This method is to get the project and version list and check if the project and version are already created
	 * If the project and version is not create, call the version and project create method.
	 */
	private void setProjectVersionId(String project, String version, loginValues lvalues, projectValues pvalues) {		
		
		String requestURL = lvalues.getServerApiUri() + "project";
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestURL);	
		
		String projectStatus = "f";
        String versionStatus = "f";
		
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
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());
            JSONArray jsonArray = (JSONArray) jsonObj.get("response");
            
            
            for(int i = 0; jsonArray.size() > i; i++) {
                JSONObject tempObj = (JSONObject) jsonParser.parse(jsonArray.get(i).toString());
                if(tempObj.get("project_name").toString().equals(project)) {
                	projectStatus = "t";
                	pvalues.setProjectid(tempObj.get("project_id").toString());
                	System.out.println("PROJECT: " + project + " / PROJECT_ID: '" + tempObj.get("project_id").toString() + "' is already created");  
                }
                
                JSONArray tempArray = (JSONArray) tempObj.get("versions");
                
                for(int j = 0; tempArray.size() > j; j++) {
                	JSONObject tempVersionObj = (JSONObject) jsonParser.parse(tempArray.get(j).toString());
                    if(tempVersionObj.get("version_name").toString().equals(version)) {
                    	versionStatus = "t";
                    	pvalues.setVersionid(tempVersionObj.get("version_id").toString());
                    	System.out.println("VERSION: " + version + " / VERSIONID_ID: '" + tempVersionObj.get("version_id").toString() + "' is already created");
                    }
                }
                
            }
            
            // if version is not exist
            if(versionStatus.equals("f") && projectStatus.equals("t")) {            	
             	createVersion(version, lvalues, pvalues);            	
            }

            // if project is not exist
            if(projectStatus.equals("f")) {            	
             	createProject(project, version, lvalues, pvalues);            	
            }
            
		} catch (Exception e) {
			pvalues.setSuccess(0);
			System.out.println();
			System.out.println("FAILED: Please, check assigning project code");
			System.out.println();
			System.exit(1);
			e.printStackTrace();
		}	
	}
	
	private void createVersion(String version, loginValues lvalues, projectValues pvalues) {
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("name", version);        
		
		String requestURL = lvalues.getServerApiUri() + "project/" + pvalues.getProjectid() + "/create_version";
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(requestURL);				
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
			httpPost.addHeader("content-type", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + lvalues.getaccessToken());
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
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
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());            
	        JSONObject getResponse = (JSONObject) jsonObj.get("response");
			
            //set versionid
            pvalues.setVersionid(getResponse.get("version_id").toString());
            
            System.out.println("VERSION: " + pvalues.getVersion() + " / VERSION_ID: " + pvalues.getVersionid() + " is created in PROJECT: " + pvalues.getProjectName());
            System.out.println();
			
		} catch (Exception e) {
			pvalues.setSuccess(0);
			System.out.println();
			System.out.println("ERROR: Please, check creating project");
			System.out.println();
			System.exit(1);
			e.printStackTrace();

		}
	}
	
	
	private void createProject(String project, String version, loginValues lvalues, projectValues pvalues) {
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(version);
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("name", project);
        rootObject.put("versions", jsonArray);
        
		String requestURL = lvalues.getServerApiUri() + "project/create";
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(requestURL);				
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + lvalues.getaccessToken());
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
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
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());            
	        JSONObject getResponse = (JSONObject) jsonObj.get("response");
			
            //set project/versionid
            pvalues.setProjectid(getResponse.get("project_id").toString());
            
            JSONArray tempArray = (JSONArray) getResponse.get("versions");            
            for(int j = 0; tempArray.size() > j; j++) {
            	JSONObject tempVersionObj = (JSONObject) jsonParser.parse(tempArray.get(j).toString());
            	pvalues.setVersionid(tempVersionObj.get("version_id").toString());
            }
    		            
            
            System.out.println("The Project/Version is now created:");
            System.out.println("PROJECT: " + pvalues.getProjectName() + " / PROJECT_ID:" + pvalues.getProjectid() + 
            		" / VERSION: " + pvalues.getVersion() + " / VERSION_ID: " + pvalues.getVersionid());
			
		} catch (Exception e) {
			pvalues.setSuccess(0);
			System.out.println();
			System.out.println("ERROR: Please, check creating project");
			System.out.println();
			System.exit(1);
			e.printStackTrace();

		}
	}

	
	
}
