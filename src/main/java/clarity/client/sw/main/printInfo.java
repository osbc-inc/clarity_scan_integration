package clarity.client.sw.main;

import clarity.client.sw.values.loginValues;
import clarity.client.sw.values.projectValues;

public class printInfo {
	
	static void usage() {		   
		System.out.println("Usage:");
		System.out.println("$ java -jar class [args....]");
		System.out.println();
		System.out.println("e.g:");
		System.out.println("$ java -jar clarity_scan_integration.jar --protocol http --address clarity.osbc.co.kr --email user@osbc.co.kr --password userpassword "
							+ "--project project_name --version version_name --targetpath /set/a/target_path --filename filename_to_be.zip "
							+ "--excludepath /exclude/path1/*,/exclude/path2/*,*.txt");
		System.out.println();
		System.out.println();
	}
	
	public static void startClarity() {		
		System.out.println();
		System.out.println("   *****  *	            ***     *******   ********** ********* *       *");
		System.out.println("  *	  *	           *   *    *      *      *          *     *       *");
		System.out.println(" *        *	          *     *   *       *     *          *      *     *");
		System.out.println("*         *             *        *  *       *     *          *       *   *");
		System.out.println("*         *	        *********** ********      *          *        * *");
		System.out.println("*         *	        *         * *     *       *          *         *");
		System.out.println(" *        *	        *         * *      *      *          *         *");
		System.out.println("  *	  *             *         * *       *     *          *	       *");		
		System.out.println("   ****** *********	*         * *       * **********     *         *");
		System.out.println();
	}
	
	public static void printinfo() {
		loginValues lvalues = new loginValues();
		projectValues pvalues = new projectValues();
		
		System.out.println();
		System.out.println("Server URL: " + lvalues.getServerApiUri());
		System.out.println("UserName: " + lvalues.getEmail());
		System.out.println("Password: " + "*******");
		System.out.println();
	}
	

}