package clarity.client.sw.values;

import java.util.ArrayList;

public class projectValues {	
	
		private static projectValues values = new projectValues();

		public projectValues() {
		}

		public static projectValues getInstance() {
			return values;
		}

		private static String projectid;	
		private static String projectname;
		private static String versionid;
		private static String version;
		private static String scanid;
		private static String filePath;
		private static String fileName;
		private static String compressedFile;
		private static String binaryid;
		private static long fileSize;
		private static int Success = 1;
		private static ArrayList<String> scanOption = new ArrayList<String>();

		public String getBinaryid() {
			return binaryid;
		}
		public void setBinaryid(String binaryid) {
			this.binaryid = binaryid;
		}
		
		public String getProjectid() {
			return projectid;
		}
		public void setProjectid(String projectid) {
			this.projectid = projectid;
		}

		public String getProjectName() {
			return projectname;
		}
		public void setProjectName(String projectname) {
			this.projectname = projectname;
		}
		
		public String getVersionid() {
			return versionid;
		}
		public void setVersionid(String versionid) {
			this.versionid = versionid;
		}

		public void setVersion(String version) {
			this.version = version;
		}		
		public String getVersion() {
			return version;
		}
		
		public void setScanId(String scannid) {
			this.scanid = scannid;
		}		
		public String getScanId() {
			return scanid;
		}
		
		public void setFilePath(String filePath) {
			this.filePath = filePath;			
		}
		public String getFilePath() {
			return filePath;
		}
		
		public void setFileName(String fileName) {
			this.fileName = fileName;			
		}
		public String getFileName() {
			return fileName;
		}
		
		public void setCompressedFile(String compressedFile) {
			this.compressedFile = compressedFile;			
		}
		public String getCompressedFile() {
			return compressedFile;
		}
		
		public void setScanOption(String scanOption) {
			this.scanOption.add(scanOption);			
		}
		public ArrayList getScanOption() {
			return scanOption;
		}
		
		public void setFilesize(long fileSize) {
			this.fileSize = fileSize;
		}
		public long getFilesize() {
			return this.fileSize;
		}
		
		public void setSuccess(int success) {
			this.Success = success;
		}
		public int getSuccess(){
			return this.Success;
		}
}
