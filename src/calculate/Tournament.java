package calculate;

public class Tournament {

	private int player1; // your own rating
	private double score; // your score in tournament
	private int kFactor; // your own kFactor
	private int[] players; // your opponents

	// constructor to assign the parameters to the private properties
	public Tournament(int player1, double score, int kFactor, int[] players) {
		this.player1 = player1;
		this.score = score;
		this.kFactor = kFactor;
		this.players = players;
	}

	// getters
	public int getPlayer1() {
		return player1;
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