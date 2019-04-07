import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.text.*;

/**
 * @author Rhea
 * Class for different users
 */
public class User implements hash{

	private String username;
	private String hashp;
	private String name;
	private String emailid;
	private String phoneno;
	int faillogc;
	String date;
	boolean locked;
	
	/**
	 * @return Returns Last Login Date
	 */
	String getDate() {
		return date;
	}
	
	/**
	 * Constructor for User object
	 */
	User()
	{
		faillogc=0;
		date="";
		locked=false;
	}
	
	/**
	 * @return Returns Username
	 */
	String getUsername() {
		return username;
	}
	/**
	 * @return Returns hashed password
	 */
	String getHashp() {
		return hashp;
	}
	/** Returns full name of user
	 */
	String getName() {
		return name;
	}
	/**
	 * @return Returns email id of user
	 */
	String getEmailid() {
		return emailid;
	}
	/**
	 * @return Returns phone number of user
	 */
	String getPhoneno() {
		return phoneno;
	}
	/**
	 * @return returns the fail login count
	 */
	int getFaillogc() {
		return faillogc;
	}
	/**
	 * @return return true if user account is locked and false if not locked
	 */
	boolean isLocked() {
		return locked;
	}
	/**
	 * @param phoneno Phone Number of user
	 */
	void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	
	/**
	 * @param username Username of the user
	 */
	void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @param hashp The hashed password of user account
	 */
	void setHashp(String hashp) {
		this.hashp = hashp;
	}
	/**
	 * @param name Full Name of user
	 */
	void setName(String name) {
		this.name = name;
	}
	/**
	 * @param emailid Email id of User
	 */
	void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	/**
	 * @param faillogc The Fail login count of user
	 */
	void setFaillogc(int faillogc) {
		this.faillogc = faillogc;
	}
	/**
	 * @param d The last login date of user
	 */
	void setDate(String d) {
		
		if(d==null)
			
			this.date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		else
			this.date=d;
			//this.date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		
	}
	/**
	 * @param locked Variable which stores whether user account is locked or not
	 */
	void setLocked(boolean locked) {
		this.locked = locked;
	}	

	/**
	 * @param pass The string to be hashed
	 * @param algo The algorithm to use in hashing
	 * @return The hashed value
	 * Calculates hash value of the password
	 */
	public String gethash(String pass, String algo)
	{
		String generatedpass= null;
		try {
		MessageDigest md= MessageDigest.getInstance(algo);
		md.update(pass.getBytes());
		byte[] bytes=md.digest();
		StringBuilder sb=new StringBuilder();
		for( int i=0; i<bytes.length;i++)
		{
			sb.append(String.format("%02x", bytes[i]));
		}
		generatedpass=sb.toString();
		return generatedpass;
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
