package clarity.client.sw.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import clarity.client.sw.uploading.deleteCompressedFile;
import clarity.client.sw.uploading.osValidator;
import clarity.client.sw.values.loginValues;
import clarity.client.sw.values.projectValues;

public class scanBinary {

	public void scanBinary() throws IOException {
		
		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();
		deleteCompressedFile dvalue = new deleteCompressedFile();
		osValidator osValidation = new osValidator();	
		
		String requestURL = lvalues.getServerApiUri() + "scan";
        HttpPost httpPost = new HttpPost(requestURL);
        File file = new File(pvalues.getCompressedFile().toString());     
     
        
		try {
			
            HttpEntity multipart = MultipartEntityBuilder.create()
                    .addPart("binary", new FileBody(file, ContentType.DEFAULT_BINARY))
                    .addTextBody("project_id", pvalues.getProjectid(), ContentType.TEXT_PLAIN.withCharset("UTF-8"))
                    .addTextBody("version_id", pvalues.getVersionid(), ContentType.TEXT_PLAIN.withCharset("UTF-8"))
                    .build();

			//httpPost.addHeader("content-type", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + lvalues.getaccessToken());			
			httpPost.setEntity(multipart);
			
			HttpClient httpClient = HttpClientBuilder.create().build();
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
            JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
            
            pvalues.setBinaryid(jsonObj2.get("binary_id").toString());
			
			
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
