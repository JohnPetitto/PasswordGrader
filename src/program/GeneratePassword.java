package program;

import java.util.Random;

/*
 * The GeneratePassword class will randomly generate a password that
 * will pass all of the methods listed in the Criteria class. All
 * passwords will consist of random letters for the first N-2 characters
 * and end with a randomly generated digit and symbol.
 */

public class GeneratePassword {
	private static final int MIN_LEN = 8;
	private static final int RANGE_LEN = 4;
	private static final String alpha = "abcdefghijklmnopqrstuvwxyz";
	private static final String numeric = "0123456789";
	private static final String symbolic = "!@#$%^&*()-_=+[]{}\\|;:'\",<.>/?";
	
	// generates a random password with N-2 characters plus a digit and symbol
	public static String generate() {
		String randomPassword = "";
		Random r = new Random();
		
		int passwordLength = r.nextInt(RANGE_LEN + 1) + MIN_LEN;
		
		// generate the first N-2 characters as unique random letters
		for (int i = 0; i < passwordLength - 2; i++) {
			int letterValue = r.nextInt(alpha.length());
			char letter = alpha.charAt(letterValue);
			
			// alternate between cases (avoids sequential order too)
			if (i % 2 != 0)
				letter = Character.toUpperCase(letter);
			
			// ensure letter has not been used previously
			if (randomPassword.contains(letter + "")) {
				i--;
				continue;
			}
			
			randomPassword += letter; // add letter
		}
		
		randomPassword += numeric.charAt(r.nextInt(numeric.length())); // add random digit
		randomPassword += symbolic.charAt(r.nextInt(symbolic.length())); // add random symbol
		
		return randomPassword;
	}
}
