package clarity.client.sw.uploading;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import clarity.client.sw.values.projectValues;

public class compressFiles {	
	
	public void compressfiles(String excludePath) throws IOException {		
		projectValues filevalues = new projectValues();
		deleteCompressedFile dvalue = new deleteCompressedFile(); 
		
		try {
			
			List<String> exclude = Arrays.asList(excludePath.split(","));
			
			String command = "zip -r " + filevalues.getCompressedFile() + " " + filevalues.getFilePath();
			
			if(0 < excludePath.length()) {
				for(int i = 0; i < exclude.size(); i++) {
					//this is to exclude path
					command = command + " -x " + exclude.get(i);
				}
			}
			
			System.out.println();
			System.out.println("zip command: " + command);
			
			shellCmd(command);	   
			 
			 //set FileSize
			 File oFile = new File(filevalues.getCompressedFile());			 
			  
			 if (oFile.exists()) {		     
				  filevalues.setFilesize(oFile.length());
				  double mega = (filevalues.getFilesize()/1024)/1024;
				  double giga = ((filevalues.getFilesize()/1024)/1024)/1024;
				  
			 	  System.out.println();
			 	  if(mega > 1024) {
			 		 System.out.println("Compressed File Size: " + filevalues.getFilesize()/1024 + " KiB / " + mega + " MiB / "
	    		   				+ giga + " GiB");
			 	  } else if (mega < 1024) {
			 		 System.out.println("Compressed File Size: " + filevalues.getFilesize()/1024 + " KiB / " + mega + " MiB");
			 	  }
			 	  
			 }  else {
				System.err.println("Please, check compressed file path and name");
				filevalues.setSuccess(0);
				System.exit(1);
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			filevalues.setSuccess(0);
			dvalue.deletecomparessedfile();
			System.exit(1);
		}
	}
	
	private static void shellCmd(String command) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        
        while((line = br.readLine()) != null) {
                       System.out.println(line);
        }
	}

	
}
