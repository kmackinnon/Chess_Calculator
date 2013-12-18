package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class OpponentsPanel extends JPanel implements Standard {

	private static JLabel[] opponentNumber = new JLabel[15];
	private static JTextField[] opponentField = new JTextField[15];

	final static int NUM_FIELDS = 15; // number of opponents user may enter

	// setting panel's properties
	public OpponentsPanel() {
		setLayout(new GridLayout(5, 6));
		setPreferredSize(new Dimension(STD_WIDTH, STDH_OPP));
		setBorder(new EmptyBorder(20, 10, 10, 30)); // bottom, left, right, top

		build();
	}

	// builds the panel's contents
	private void build() {

		for (int i = 0; i < NUM_FIELDS; i++) {
			String opponent = createStringFromNumber(i + 1);

			opponentNumber[i] = new JLabel();
			opponentNumber[i].setText(opponent);
			add(opponentNumber[i]);

			opponentField[i] = new JTextField();
			add(opponentField[i]);
		}
	}

	// helper method for label creation and correct spacing
	private String createStringFromNumber(int i) {
		return (i <= 9) ? ("      " + i + ".") : ("     " + i + ".");
	}

	// gets all players from the text fields and adds them to an int[]
	public static int[] getPlayers() {
		int count = 0;

		try {
			for (int i = 0; i < NUM_FIELDS; i++) {
				// ensuring that the user has entered valid opponent ratings
				if (!opponentField[i].getText().isEmpty() && isInteger(opponentField[i].getText())) {
					if (Integer.parseInt(opponentField[i].getText()) < 1000) {
						count = 0;
						JOptionPane.showMessageDialog(null, "Please enter ratings of at least 1000.");
						cleanup();
					} else {
						count++;
					}
				}
			}

			// if the user enters anything other than an integer, execute catch block
		} catch (NumberFormatException e) {
			count = 0;
			JOptionPane.showMessageDialog(null, "Please enter opponent ratings.\n"
					+ "As per FIDE rules, the minimum rating is 1000.");
			cleanup();
		}

		// creates new players array the length of the number of text fields the user entered
		int[] players = new int[count];

		count = 0;

		// populates the players array
		for (int i = 0; i < NUM_FIELDS; i++) {
			if (!opponentField[i].getText().isEmpty()) {
				players[count] = Integer.parseInt(opponentField[i].getText());
				count++;
			}
		}

		return players;
	}

	// resets the opponent rating fields to empty
	public static void cleanup() {
		for (int i = 0; i < NUM_FIELDS; i++) {
			opponentField[i].setText("");
		}
	}

	// helper to check if the rating entered is an integer
	public static boolean isInteger(String user) {
		int value = Integer.parseInt(user);
		return value == (int) value;
	}

}
