package clarity.client.sw.main;

import java.util.ArrayList;
import java.util.Arrays;

import clarity.client.sw.scan.exportBOMreport;
import clarity.client.sw.scan.scanBinary;
import clarity.client.sw.setdata.setLoginInfo;
import clarity.client.sw.setdata.setProjectInfo;
import clarity.client.sw.uploading.compressFiles;
import clarity.client.sw.uploading.deleteCompressedFile;
import clarity.client.sw.uploading.osValidator;
import clarity.client.sw.values.loginValues;
import clarity.client.sw.values.projectValues;;

public class main {
	
	public static void main(String[] args) {				
		
		try {
			
			runwithArgu(args);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}	
				       
	private static void runwithArgu(String[] args){
		
		printInfo printInfo = new printInfo();
		setLoginInfo loginInfo = new setLoginInfo();
		setProjectInfo projectInfo = new setProjectInfo();
		scanBinary scanbinary = new scanBinary();	
		exportBOMreport bomreport = new exportBOMreport();	
		compressFiles compressFiles = new compressFiles();	
		deleteCompressedFile deletefile = new deleteCompressedFile();
		osValidator validator = new osValidator();
		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();

		try {		
			
			
			if(args.length == 0 || args[0].equals("-h") || args[0].equals("--h") || args[0].equals("--help")) {
				printInfo.usage();
				System.exit(1);
			}			
			
			ArrayList<String> param = new ArrayList<String>(Arrays.asList(args));			
		
			if(!param.contains("--protocol") ||  !param.contains("--address") || !param.contains("--email") || !param.contains("--password") 
					|| !param.contains("--project") || !param.contains("--version") || !param.contains("--targetpath") || !param.contains("--filename")){
				System.out.println();
				System.out.println();
				System.err.println("Please, check your parameters");
				System.out.println();
				System.out.println();
				printInfo.usage();
				System.exit(1);
			} 
			
			
			System.out.println("Start Clarity Scan Integration");			
			printInfo.startClarity();
	
			String protocol = "http";
			String address = "";
			String email = "";
			String password = "";
			String project = "";
			String version = "";		
			String targetpath = "";
			String fileName = "";
			String excludePath = "";			
			
			for(int i = 0; i < args.length; i++) {
				if(args[i].equals("--protocol")) {
					protocol = args[i+1]; 
				}
				
				if(args[i].equals("--address")) {
					address = args[i+1];
				}
				
				if(args[i].equals("--email")) {
					email = args[i+1]; 
				}
				
				if(args[i].equals("--password")) {
					password = args[i+1];
				}
				
				if(args[i].equals("--project")) {
					project = args[i+1]; 
				}
				
				if(args[i].equals("--version")) {
					version = args[i+1];
				}
			
				if(args[i].equals("--targetpath")) {
					targetpath = args[i+1];
				}
				
				if(args[i].equals("--filename")) {
					fileName = args[i+1];
				}
				
				if(args[i].equals("--excludepath")) {
					excludePath = args[i+1];
				}				
				
				i++;
			}
			
			loginInfo.setLogininfo(protocol, address, email, password);
			printInfo.printinfo();
			projectInfo.setInfo(project, version);
			
			if(!targetpath.equals("")){
				validator.setFileValues(targetpath, fileName);			
				deletefile.validationFile();
				compressFiles.compressfiles(excludePath);						
				scanbinary.scanBinary();
			}
			
			bomreport.exportReport();;
			
			if(!targetpath.equals("")){
				deletefile.deletecomparessedfile();
			}
			
			System.out.println();
			System.out.println("Please, access the below address to check detailed scan result");
			System.out.println("- " + protocol + "://" + address + "/#/results/overview/binary/" + pvalues.getBinaryid());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}