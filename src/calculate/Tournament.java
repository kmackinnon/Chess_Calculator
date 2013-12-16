package calculate;

public class Tournament {

	private int userRating;
	private double score;
	private int kFactor;
	private int[] players;

	public Tournament(int userRating, double score, int kFactor, int[] players) {
		this.userRating = userRating;
		this.score = score;
		this.kFactor = kFactor;
		this.players = players;
	}

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

	/**
	 * Reduce the total score in the tournament by the result of a single game.
	 * 
	 * @param result
	 * @return the new score
	 */
	public double setScore(double result) {
		return score -= result;
	}

}