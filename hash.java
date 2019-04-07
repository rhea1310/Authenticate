
/**
 * @author Rhea 
 * Interface for getting hash value of a string
 */
public interface hash {

	/**
	 * @param pass The string to be hashed
	 * @param algo The algorithm to use in hashing
	 * @return The hashed value
	 * Calculates hash value of the password
	 */
	public String gethash(String pass, String algo);
}