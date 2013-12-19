package calculate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used to calculate a user's rating change and new rating following a tournament
 * 
 * To find the formula used, refer to http://www.fide.com/fide/handbook.html?id=161&view=article
 * 
 * @author Keith MacKinnon
 * @date Dec 18, 2013
 * 
 */
public class Calculator {

	private static Calculator firstInstance = null;

	private static final int MIN_RATING = 1000;
	private static final int MAX_RATING_DIFF = 400;

	/**
	 * Using a Singleton Design Pattern to ensure only one instance of Calculator
	 * 
	 */
	private Calculator() {
	}

	/**
	 * 
	 * @return the only instance of Calculator
	 */
	public static Calculator getInstance() {
		if (firstInstance == null) {
			firstInstance = new Calculator();
		}

		return firstInstance;
	}

	/**
	 * Determines rating difference between user and opponent
	 * 
	 * @param myRating
	 * @param opRating
	 * @return user's rating minus the opponent's rating
	 */
	public int computeRatingDiff(int myRating, int opRating) {

		if ((myRating - opRating) > MAX_RATING_DIFF) {
			return MAX_RATING_DIFF;
		} else if ((myRating - opRating) < -MAX_RATING_DIFF) {
			return -MAX_RATING_DIFF;
		} else {
			return myRating - opRating;
		}
	}

	/**
	 * Calculates user's expected score based on rating difference
	 * 
	 * @param ratingDif
	 * @return the expected score (out of 1)
	 */
	public double computeExpectedScore(int ratingDif) {

		double[] fide = new double[401]; // length 401 since rating will be 0-400

		// retrieve text file which contains all expected values for rating differences
		try {
			BufferedReader in = new BufferedReader(new FileReader("res/expected.txt"));
			String str = in.readLine();
			for (int i = 0; i < fide.length; i++) {
				try {
					String[] ar = str.split(",");
					fide[i] = Double.parseDouble(ar[i]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (ratingDif < 0) ? round(1 - fide[Math.abs(ratingDif)], 2) : round(fide[ratingDif], 2);
	}

	/**
	 * 
	 * Calculates one game rating change.
	 * 
	 * @param kFactor
	 * @param result
	 * @param expResult
	 * @return the rating change from one game
	 */
	public double computeRatingChange(int oldRating, int kFactor, double result, double expResult) {

		double change = kFactor * (result - expResult);

		// method requires old rating to ensure change will not take user below minimum possible rating
		if ((oldRating + change) < MIN_RATING) {
			return oldRating - computeNewRating(oldRating, change);
		}
		return change;
	}

	/**
	 * 
	 * Calculates a player's new rating after a tournament.
	 * 
	 * @param oldRating
	 * @param change
	 * @return the new rating from the old + the change
	 */
	public double computeNewRating(int oldRating, double change) {

		if ((oldRating + change) < MIN_RATING) {
			return MIN_RATING;
		}
		return oldRating + change;
	}

	/**
	 * 
	 * Helper method to round double values.
	 * 
	 * @param value
	 * @param decimals
	 * @return the value rounded to specified number of decimals
	 */
	public double round(double value, int decimals) {
		long factor = (long) Math.pow(10, decimals);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
