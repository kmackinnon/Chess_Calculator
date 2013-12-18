package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SelfPanel extends JPanel implements Standard {

	private static JLabel ratinglabel = new JLabel("Rating:");
	private static JTextField ratingfield = new JTextField();
	private static JLabel scorelabel = new JLabel("Score:");
	private static JTextField scorefield = new JTextField();

	// setting panel's properties
	public SelfPanel() {
		setLayout(new GridLayout(2, 2));
		setPreferredSize(new Dimension(2 * STD_WIDTH / 3, STDH_TOP));
		setBorder(new EmptyBorder(30, 30, 30, 30)); // bottom, left, right, top

		build();
	}

	private void build() {
		add(ratinglabel);
		add(ratingfield);
		add(scorelabel);
		add(scorefield);
	}

	// getters
	public static int getUserRating() {
		try {
			return Integer.parseInt(ratingfield.getText());
		} catch (NumberFormatException e) {
			return 999; // this will signal an invalid rating to the Calculator class
		}
	}

	public static double getScore() {
		try {
			return Double.parseDouble(scorefield.getText());
		} catch (NumberFormatException e) {
			return -1; // this will signal an invalid score to the Calculator class
		}
	}

	// resets the panel to original state
	public static void cleanup() {
		ratingfield.setText("");
		scorefield.setText("");
	}

}
