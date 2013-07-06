package calculate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

/*
 Rn = Ro + K (W - We)
 Rn	New rating
 Ro	Old rating
 K	Value of a single game
 W	Score; 1.0 for a win, 0.5 for a draw
 We	Expected score based on Ro

 Refer to http://www.fide.com/fide/handbook.html?id=161&view=article
 */
public class Calculator {

	final static double win = 1.0;
	final static double draw = 0.5;
	final static double loss = 0.0;

	final static int kNew = 30; // for players with fewer than 30 games
	final static int kReg = 15; // for players who have never reached 2400
	final static int kMaster = 10; // for players who have been over 2400

	public Calculator(Tournament event) {

		int oldRating = event.getPlayer1(); // gets the user's initial rating
		int[] players = event.getPlayers(); // gets the opponents in an int array

		int playerCount = players.length; // to loop though the players array

		// to ensure the user enters a valid rating and tournament score
		boolean validRating = oldRating >= 1000;
		boolean validScore = (event.getScore() >= 0) && (event.getScore() % 0.5 == 0)
				&& (event.getScore() <= playerCount);

		double ratingChange = 0; // how much user's rating will change in tournament

		while (playerCount > 0) {

			// if the user's rating is not valid, display dialog message
			if (!validRating) {
				JOptionPane.showMessageDialog(null, "User's rating must be an integer of at least 1000.");
			}

			// if the user's score is not valid, display dialog message
			if (!validScore) {
				JOptionPane.showMessageDialog(null, "Score is the number of points achieved.\n"
						+ "It must be a multiple of 0.5 and not exceed the number of games played");
				break;
			}

			int player2 = players[playerCount - 1]; // setting each opponent in the array to be player2
			double result; // result of a single game arbitrarily assigned

			if (event.getScore() > 0.5) {
				result = win;
				event.setScore(win);
			} else if (event.getScore() > 0.0) {
				result = draw;
				event.setScore(draw);
			} else {
				result = loss;
				event.setScore(loss);
			}

			// summing the rating change from each game
			ratingChange += ratingChange(event.getKFactor(), result, expected(ratingDiff(oldRating, player2)));

			playerCount--;
		}

		// the minimum possible fide rating is 1000;
		if (oldRating + ratingChange < 1000) {
			ratingChange = -(oldRating - 1000);
		}
		double newRating = newRating(oldRating, ratingChange);

		// if statement ensures that the players array is populated
		if (players.length > 0 && validScore && validRating) {
			JOptionPane.showMessageDialog(null, "Total Change: " + round(ratingChange, 2) + "\nNew Rating: "
					+ newRating);
		}
	}

	// determines rating difference between user and an opponent
	public static int ratingDiff(int myRating, int opRating) {

		if ((myRating - opRating) > 400) {
			return 400;
		} else if ((myRating - opRating) < -400) {
			return -400;
		} else {
			return myRating - opRating;
		}
	}

	// calculates user's expected score against an opponent
	public static double expected(int ratingDif) {

		// map keys to values using an array
		// index i is the key and the value is the expected score
		double[] fide = new double[401]; // length 401 since rating will be 0-400

		// retrieve text file which contains all expected values for rating differences
		try {
			@SuppressWarnings("resource")
			BufferedReader expected = new BufferedReader(new FileReader("expected.txt"));
			for (int i = 0; i < fide.length; i++) {
				try {
					fide[i] = Double.parseDouble(expected.readLine());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// if player1 is lower rated
		if (ratingDif < 0) {
			return 1 - fide[Math.abs(ratingDif)];
		}
		return fide[ratingDif]; // if player1 is higher rated than his opponent
	}

	// calculates a player's rating change
	public static double ratingChange(int kFactor, double result, double expResult) {
		return kFactor * (result - expResult);
	}

	// computes a player's new rating
	public static double newRating(int oldRating, double change) {
		return oldRating + change;
	}

	// helper to round doubles to specified number of decimals
	public static double round(double value, int decimals) {
		long factor = (long) Math.pow(10, decimals);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
