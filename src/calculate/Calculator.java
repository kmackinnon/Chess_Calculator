package calculate;

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

	final static int kNew = 30; // for players with less than 30 games
	final static int kReg = 15; // for players who have never reached 2400
	final static int kMaster = 10; // for players who have been over 2400

	private Tournament event;

	public Calculator(Tournament event) {
		this.event = event;

		// opponent (each player in arraylist)
		int player2;

		// result of a single game arbitrarily assigned
		double result;

		double totalRatingChange = 0;
		double newRating;

		int[] players = event.getPlayers();

		// to loop though the players array
		int playercount = players.length;

		while (playercount > 0) {

			// setting each opponent in the array to be player2
			player2 = players[playercount - 1];

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
			totalRatingChange += ratingChange(event.getKFactor(), result,
					expected(ratingDiff(event.getPlayer1(), player2)));

			playercount--;
		}

		newRating = newRating(event.getPlayer1(), totalRatingChange);

		JOptionPane.showMessageDialog(null, "Total Change: " + round(totalRatingChange, 2) + "\nNew Rating: "
				+ newRating);
	}

	// determine players' rating difference
	public static int ratingDiff(int myRating, int opRating) {

		if ((myRating - opRating) > 400) {
			return 400;
		} else if ((myRating - opRating) < -400) {
			return -400;
		}
		return myRating - opRating;
	}

	// calculates your expected score against one opponent
	public static double expected(int ratingDif) {

		// we will simulate a map by using an array where the index is the key and the value is the expected score
		// these elements were retrieved by parsing an HTML table with Python and then printing them out
		double[] fide = { 0.5, 0.5, 0.5, 0.5, 0.51, 0.51, 0.51, 0.51, 0.51, 0.51, 0.51, 0.52, 0.52, 0.52, 0.52, 0.52,
				0.52, 0.52, 0.53, 0.53, 0.53, 0.53, 0.53, 0.53, 0.53, 0.53, 0.54, 0.54, 0.54, 0.54, 0.54, 0.54, 0.54,
				0.55, 0.55, 0.55, 0.55, 0.55, 0.55, 0.55, 0.56, 0.56, 0.56, 0.56, 0.56, 0.56, 0.56, 0.57, 0.57, 0.57,
				0.57, 0.57, 0.57, 0.57, 0.58, 0.58, 0.58, 0.58, 0.58, 0.58, 0.58, 0.58, 0.59, 0.59, 0.59, 0.59, 0.59,
				0.59, 0.59, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.61, 0.61, 0.61, 0.61, 0.61, 0.61, 0.61, 0.62,
				0.62, 0.62, 0.62, 0.62, 0.62, 0.62, 0.62, 0.63, 0.63, 0.63, 0.63, 0.63, 0.63, 0.63, 0.64, 0.64, 0.64,
				0.64, 0.64, 0.64, 0.64, 0.64, 0.65, 0.65, 0.65, 0.65, 0.65, 0.65, 0.65, 0.66, 0.66, 0.66, 0.66, 0.66,
				0.66, 0.66, 0.66, 0.67, 0.67, 0.67, 0.67, 0.67, 0.67, 0.67, 0.67, 0.68, 0.68, 0.68, 0.68, 0.68, 0.68,
				0.68, 0.68, 0.69, 0.69, 0.69, 0.69, 0.69, 0.69, 0.69, 0.69, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7,
				0.71, 0.71, 0.71, 0.71, 0.71, 0.71, 0.71, 0.71, 0.71, 0.72, 0.72, 0.72, 0.72, 0.72, 0.72, 0.72, 0.72,
				0.73, 0.73, 0.73, 0.73, 0.73, 0.73, 0.73, 0.73, 0.73, 0.74, 0.74, 0.74, 0.74, 0.74, 0.74, 0.74, 0.74,
				0.74, 0.75, 0.75, 0.75, 0.75, 0.75, 0.75, 0.75, 0.75, 0.75, 0.76, 0.76, 0.76, 0.76, 0.76, 0.76, 0.76,
				0.76, 0.76, 0.77, 0.77, 0.77, 0.77, 0.77, 0.77, 0.77, 0.77, 0.77, 0.78, 0.78, 0.78, 0.78, 0.78, 0.78,
				0.78, 0.78, 0.78, 0.78, 0.79, 0.79, 0.79, 0.79, 0.79, 0.79, 0.79, 0.79, 0.79, 0.79, 0.8, 0.8, 0.8, 0.8,
				0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.81, 0.81, 0.81, 0.81, 0.81, 0.81, 0.81, 0.81, 0.81, 0.81, 0.81, 0.82,
				0.82, 0.82, 0.82, 0.82, 0.82, 0.82, 0.82, 0.82, 0.82, 0.82, 0.83, 0.83, 0.83, 0.83, 0.83, 0.83, 0.83,
				0.83, 0.83, 0.83, 0.83, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.84, 0.85,
				0.85, 0.85, 0.85, 0.85, 0.85, 0.85, 0.85, 0.85, 0.85, 0.85, 0.85, 0.86, 0.86, 0.86, 0.86, 0.86, 0.86,
				0.86, 0.86, 0.86, 0.86, 0.86, 0.86, 0.86, 0.87, 0.87, 0.87, 0.87, 0.87, 0.87, 0.87, 0.87, 0.87, 0.87,
				0.87, 0.87, 0.87, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88, 0.88,
				0.88, 0.88, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.89, 0.9, 0.9,
				0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.91, 0.91, 0.91, 0.91,
				0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.91, 0.92, 0.92, 0.92, 0.92,
				0.92, 0.92, 0.92, 0.92, 0.92 };

		if (ratingDif < 0) {
			return 1 - fide[Math.abs(ratingDif)]; // if player1 is lower rated
		}

		return fide[ratingDif]; // if player1 is higher rated than his opponents
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
