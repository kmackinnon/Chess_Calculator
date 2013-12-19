package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import calculate.Calculator;
import calculate.Tournament;

@SuppressWarnings("serial")
public class ExecutionPanel extends JPanel implements Standard {

	private static JButton clean = new JButton("Clean");
	private static JButton calculate = new JButton("Calculate");
	private static JButton exit = new JButton("Exit");

	private static int MIN_RATING = 1000;
	public static double WIN = 1.0;
	public static double DRAW = 0.5;
	public static double LOSS = 0.0;

	// setting panel's properties
	public ExecutionPanel() {
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(STD_WIDTH, STDH_OPP));
		setBorder(new EmptyBorder(15, 10, 10, 10)); // bottom, left, right, top

		build();
	}

	private void build() {
		add(calculate);
		add(clean);
		add(exit);

		events();
	}

	// add action listeners to each button and perform required action
	private static void events() {

		calculate.addActionListener(new ActionListener() {

			/**
			 * Performs calculations to compute user's rating change and new rating
			 * 
			 * @param e
			 *            the action event when user clicks Calculate
			 * 
			 */
			public void actionPerformed(ActionEvent e) {

				int userRating = SelfPanel.getUserRating();
				boolean isRatingValid = isRatingValid(userRating);
				
				int kFactor = Integer.parseInt(KFACTOR_VALUES[KFactorPanel.kFactorSelected()]);
				int[] players = OpponentsPanel.getPlayers();
				int playerCount = players.length;

				double score = SelfPanel.getScore();
				boolean isScoreValid = isScoreValid(score, playerCount);
				
				// only perform calculations if score and rating are valid
				if (isScoreValid && isRatingValid) {
					Calculator calc = Calculator.getInstance();

					double computeRatingChange = 0;

					// loop through the players array and sum the rating change
					while (playerCount > 0) {

						// setting each player in the array to be the opponent
						int opponentRating = players[playerCount - 1];
						double result; // result of a single game arbitrarily assigned

						if (score > 0.5) {
							result = WIN;
							score -= result;
						} else if (score > 0.0) {
							result = DRAW;
							score -= result;
						} else {
							result = LOSS;
						}

						// summing the rating change from each game
						computeRatingChange += calc.computeRatingChange(userRating, kFactor, result,
								calc.computeExpectedScore(calc.computeRatingDiff(userRating, opponentRating)));

						playerCount--;
					}

					JOptionPane.showMessageDialog(null, "Total Change: " + calc.round(computeRatingChange, 2)
							+ "\nNew Rating: " + calc.computeNewRating(userRating, computeRatingChange));
				
				}

			}
		});

		clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpponentsPanel.cleanup();
				SelfPanel.cleanup();
				KFactorPanel.cleanup();
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Helper to validate a user's rating.
	 * @param userRating
	 * @return
	 */
	private static boolean isRatingValid(int userRating){
		if (userRating <= MIN_RATING) {
			JOptionPane.showMessageDialog(null, "User's rating must be an integer of at least " + MIN_RATING
					+ ".");
			return false;
		}
		return true;
	}
	
	/**
	 * Helper to validate a user's score
	 * @param score
	 * @param playerCount
	 * @return
	 */
	private static boolean isScoreValid(double score, int playerCount){
		if (score < 0 || score % 0.5 != 0 || score > playerCount) {
			JOptionPane.showMessageDialog(null, "Score is the number of points achieved.\n"
					+ "It must be a multiple of 0.5 and not exceed the number of games played");
			return false;
		}
		return true;
	}
}
