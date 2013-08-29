package program;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

/* 
 * The Criteria class contains a list of methods that are used to 
 * determine password strength. The Score class invokes the methods in
 * this class for a specific password. Each method determines whether 
 * the password passes (true) or fails (false) that specific criteria.
 */

public class Criteria {
	
	// password should be >= 8 characters
	public static boolean minLength(String password) {
		return password.length() >= 8;
	}
	
	// password should contain both upper and lower case characters
	public static boolean multiCase(String password) {
		if (password.length() == 1)
			return false;
		
		// check if password is comprised entirely of lower case or upper case letters
		if (password.equals(password.toLowerCase()) || password.equals(password.toUpperCase()))
			return false;
		
		return true;
	}
	
	// password should contain at least one digit
	public static boolean containsDigit(String password) {
		if (password.length() == 1)
			return false;
		
		for (int i = 0; i < password.length(); i++)
			if (Character.isDigit(password.charAt(i)))
				return true;
		
		return false;
	}
	
	// password should contain at least one symbol (non-alphanumeric)
	public static boolean containsSymbol(String password) {
		if (password.length() == 1)
			return false;
		
		for (int i = 0; i < password.length(); i++)
			if (!Character.isLetterOrDigit(password.charAt(i)))
				return true;
		
		return false;
	}
	
	// password should not contain repeated characters (e.g. aa)
	public static boolean noRepeat(String password) {
		if (password.length() == 1)
			return false;
		
		for (int i = 0; i < password.length() - 1; i++)
			if (password.charAt(i) == password.charAt(i + 1))
				return false;
		
		return true;
	}
	
	// password should not contain duplicate characters
	public static boolean noDuplicate(String password) {
		if (password.length() == 1)
			return false;
		
		HashSet<Character> uniqueChars = new HashSet<Character>();
		for (int i = 0; i < password.length(); i++)
			uniqueChars.add(password.charAt(i));
		
		return password.length() == uniqueChars.size();
	}
	
	// password should not contain sequential characters (e.g. 1,2,3 or a,b,c)
	public static boolean notSequential(String password) {
		if (password.length() == 1)
			return false;
		
		for (int i = 0; i < password.length() - 1; i++) {
			char curChar = password.charAt(i);
			char nextChar = password.charAt(i + 1);
			
			// only check letters and numbers for sequential order
			if (Character.isLetterOrDigit(curChar) && Character.isLetterOrDigit(nextChar))
				// check both forward and reverse sequential order
				if (curChar + 1 == nextChar || nextChar + 1 == curChar)
					return false;
		}
		
		return true;
	}
	
	// password should not be one of the 500 most common passwords
	public static boolean notCommon(String password) {
		if (password.length() == 1)
			return false;
		
		InputStream is = Criteria.class.getResourceAsStream("common_passwords.txt");
		Scanner commonPasswords = new Scanner(is);
		
		while (commonPasswords.hasNext())
			if (password.equalsIgnoreCase(commonPasswords.next()))
				return false;
		
		return true;
	}
}
