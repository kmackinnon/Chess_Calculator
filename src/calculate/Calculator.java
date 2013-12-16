package calculate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Used to calculate a user's rating change and new rating following a tournament
 * 
 * To find the formula used, refer to http://www.fide.com/fide/handbook.html?id=161&view=article
 * 
 * @author Keith MacKinnon
 * @date Dec 14, 2013
 * 
 */
public class Calculator {

	private static final double WIN = 1.0;
	private static final double DRAW = 0.5;
	private static final double LOSS = 0.0;

	private static final int MIN_RATING = 1000;
	private static final int MAX_RATING_DIFF = 400;

	/**
	 * Creates the calculator which takes a Tournament event and then displays a user's new rating
	 * 
	 * @param event
	 */
	public Calculator(Tournament event) {

		int oldRating = event.getUserRating();
		int[] players = event.getPlayers();
		int playerCount = players.length;

		// to ensure the user enters a valid rating and tournament score
		boolean isRatingValid = oldRating >= MIN_RATING;
		boolean isScoreValid = (event.getScore() >= 0) && (event.getScore() % 0.5 == 0)
				&& (event.getScore() <= playerCount);

		double computeRatingChange = 0;

		while (playerCount > 0) {

			if (!checkValidity(isRatingValid, isScoreValid)) {
				break;
			}

			// setting each player in the array to be the opponent
			int opponentRating = players[playerCount - 1];
			double result;

			// result of a single game arbitrarily assigned
			if (event.getScore() > 0.5) {
				result = WIN;
				event.setScore(WIN);
			} else if (event.getScore() > 0.0) {
				result = DRAW;
				event.setScore(DRAW);
			} else {
				result = LOSS;
				event.setScore(LOSS);
			}

			// summing the rating change from each game
			computeRatingChange += computeRatingChange(event.getUserRating(), event.getKFactor(), result,
					computeExpectedScore(computeRatingDiff(oldRating, opponentRating)));

			playerCount--;
		}

		// ensures that the players array is populated
		if (players.length > 0 && isScoreValid && isRatingValid) {
			JOptionPane.showMessageDialog(null, "Total Change: " + round(computeRatingChange, 2) + "\nNew Rating: "
					+ computeNewRating(oldRating, computeRatingChange));
		}
	}

	/**
	 * Determines rating difference between user and opponent
	 * 
	 * @param myRating
	 * @param opRating
	 * @return user's rating minus the opponent's rating
	 */
	public static int computeRatingDiff(int myRating, int opRating) {

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
	public static double computeExpectedScore(int ratingDif) {

		double[] fide = new double[401]; // length 401 since rating will be 0-400

		// retrieve text file which contains all expected values for rating differences
		try {
			BufferedReader in = new BufferedReader(new FileReader("expected.txt"));
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
	public static double computeRatingChange(int oldRating, int kFactor, double result, double expResult) {

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
	public static double computeNewRating(int oldRating, double change) {

		if ((oldRating + change) < MIN_RATING) {
			return MIN_RATING;
		}
		return oldRating + change;
	}

	/**
	 * 
	 * Checks to ensure that the user correctly entered valid rating and score fields.
	 * 
	 * @param isRatingValid
	 * @param isScoreValid
	 * @return whether the parameters are valid
	 */
	public static boolean checkValidity(boolean isRatingValid, boolean isScoreValid) {
		boolean isValid = true;

		if (!isRatingValid) {
			JOptionPane.showMessageDialog(null, "User's rating must be an integer of at least " + MIN_RATING + ".");
			isValid = false;
		}

		// if the user's score is not valid, display dialog message
		if (!isScoreValid) {
			JOptionPane.showMessageDialog(null, "Score is the number of points achieved.\n"
					+ "It must be a multiple of 0.5 and not exceed the number of games played");
			isValid = false;
		}
		return isValid;
	}

	/**
	 * 
	 * Helper method to round double values.
	 * 
	 * @param value
	 * @param decimals
	 * @return the value rounded to specified number of decimals
	 */
	public static double round(double value, int decimals) {
		long factor = (long) Math.pow(10, decimals);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
