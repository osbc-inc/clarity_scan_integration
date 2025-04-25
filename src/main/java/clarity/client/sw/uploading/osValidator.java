package clarity.client.sw.uploading;

import clarity.client.sw.values.projectValues;

public class osValidator {
	
	  /**
	   * refer to http://blog.devez.net/214
	   */	
	  private static String OS = System.getProperty("os.name").toLowerCase();

      public static boolean isWindows() {
  	        return (OS.indexOf("win") >= 0);
	  }
	  
	  public static boolean isMac() {	  
	        return (OS.indexOf("mac") >= 0);	  
	  }
	  
	  public static boolean isUnix() {
	         return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	  }
	  
	  public static boolean isSolaris() {	  
	        return (OS.indexOf("sunos") >= 0);	  
	  }
	    
	  public static void setFileValues(String targetpath, String fileName) {
		  projectValues filevalues = new projectValues();
		  String compressedFile = "";
		  
		  filevalues.setFilePath(targetpath);
		  filevalues.setFileName(fileName);		  
		  
		  String end = targetpath.substring(targetpath.length() - 1, targetpath.length());
		  
		  if(end.equals("/") || end.equals("\\")) {
			  		compressedFile = targetpath;
		  } else {
				if(isWindows()) {
					compressedFile = targetpath + "\\";
				} else if (isMac() || isSolaris() || isUnix()){					
					compressedFile = targetpath + "/";
				}
		  }
		  
		  compressedFile = compressedFile + fileName;
		  filevalues.setCompressedFile(compressedFile);
	  }
}
