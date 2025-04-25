package clarity.client.sw.values;

public class loginValues {

	private static loginValues values = new loginValues();

	public loginValues() {
	}

	public static loginValues getInstance() {
		return values;
	}

	private String serveruri;
	private static String serverApiuri;
	private static String serverUploaduri;
	private static String email;
	private static String accesstoken;	
	
	public String getServerUri() {
		return serveruri;
	}
	public void setServerUri(String serveruri) {
		this.serveruri = serveruri;
	}

	public String getServerUploadUri() {
		return serverUploaduri;
	}
	public void setServerUploadUri(String serverUploaduri) {
		this.serverUploaduri = serverUploaduri;
	}
	
	public String getServerApiUri() {
		return serverApiuri;
	}
	public void setServerApiUri(String serverApiuri) {
		this.serverApiuri = serverApiuri;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getaccessToken() {
		return accesstoken;
	}
	public void setaccessToken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
}
