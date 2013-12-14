package calculate;

public class Tournament {

	private int userRating; // your own rating
	private double score; // your score in tournament
	private int kFactor; // your own kFactor
	private int[] players; // your opponents

	// constructor to assign the parameters to the private properties
	public Tournament(int userRating, double score, int kFactor, int[] players) {
		this.userRating = userRating;
		this.score = score;
		this.kFactor = kFactor;
		this.players = players;
	}

	// getters
	public int getUserRating() {
		return userRating;
	}

	public double getScore() {
		return score;
	}

	public int getKFactor() {
		return kFactor;
	}

	public int[] getPlayers() {
		return players;
	}

	// setter
	public double setScore(double result) {
		return score -= result;
	}

}