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

	private static JLabel[] opponentnumber = new JLabel[15];
	private static JTextField[] opponentfield = new JTextField[15];

	final static int NUM_FIELDS = 15; // number of opponents user may enter

	public OpponentsPanel() {
		// setting panel's properties
		setLayout(new GridLayout(5, 6));
		setPreferredSize(new Dimension(STD_WIDTH, STDH_OPP));
		// bottom, left, right, top
		setBorder(new EmptyBorder(20, 10, 10, 30));

		build();
	}

	// builds the panel's contents
	private void build() {

		for (int i = 0; i < NUM_FIELDS; i++) {

			// Label Creation and Implementation
			String numberString;
			int use = i + 1;

			if (use <= 9) {
				numberString = "      " + use + ".";
			} else {
				numberString = "     " + use + ".";
			}

			opponentnumber[i] = new JLabel();
			opponentnumber[i].setText(numberString);
			add(opponentnumber[i]);

			// Field Creation and Implementation
			opponentfield[i] = new JTextField();
			add(opponentfield[i]);
		}
	}

	// gets all players from the text fields and adds them to an int[]
	public static int[] getPlayers() {
		int count = 0;

		try {
			for (int i = 0; i < NUM_FIELDS; i++) {
				// ensuring that the user has entered valid opponent ratings
				if (!opponentfield[i].getText().isEmpty() && isInteger(opponentfield[i].getText())) {
					if (Integer.parseInt(opponentfield[i].getText()) < 1000) {
						count = 0;
						JOptionPane.showMessageDialog(null, "Please enter ratings of at least 1000.");
						cleanup();
					}
					else {
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
			if (!opponentfield[i].getText().isEmpty()) {
				players[count] = Integer.parseInt(opponentfield[i].getText());
				count++;
			}
		}

		return players;
	}

	// resets the opponent rating fields to empty
	public static void cleanup() {
		for (int i = 0; i < NUM_FIELDS; i++) {
			opponentfield[i].setText("");
		}
	}

	// helper to check if the rating entered is an integer
	public static boolean isInteger(String user) {
		int value = Integer.parseInt(user);
		return value == (int) value;
	}

}
