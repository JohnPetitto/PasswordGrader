package program;

/*
 * The score class interacts with both the view and the Criteria class.
 * The view calls the calcStrength method, passing in the password that
 * needs to be scored. Each criteria has a different score (LOW, MED, HIGH)
 * and the max score is provided as a public constant.
 */

public class Score {
	public static final int MAX_SCORE = 100;
	public static final int LOW_SCORE = 10;
	public static final int MED_SCORE = 15;
	public static final int HIGH_SCORE = 20;
	
	// calculates the strength based on criteria and weights
	public static int calcStrength(String password) {
		int score = 0;
		
		if (Criteria.minLength(password))
			score += HIGH_SCORE;
		else
			// scale score if password length is < 8 characters
			score += (int) (((double) password.length() / 8) * HIGH_SCORE);
		
		if (Criteria.multiCase(password))
			score += LOW_SCORE;
		
		if (Criteria.containsDigit(password))
			score += LOW_SCORE;
		
		if (Criteria.containsSymbol(password))
			score += LOW_SCORE;
		
		if (Criteria.noRepeat(password))
			score += LOW_SCORE;
		
		if (Criteria.noDuplicate(password))
			score += LOW_SCORE;
		
		if (Criteria.notSequential(password))
			score += LOW_SCORE;
		
		if (Criteria.notCommon(password))
			score += MED_SCORE;
		
		return score;
	}
}
