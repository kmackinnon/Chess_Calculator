package calculate;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SelfPanel extends JPanel implements Standard {
	// Fields and labels
	private static JLabel ratinglabel = new JLabel("Rating:");
	private static JTextField ratingfield = new JTextField();
	private static JLabel scorelabel = new JLabel("Score:");
	private static JTextField scorefield = new JTextField();

	public SelfPanel() {

		// setting panel's properties
		setLayout(new GridLayout(2, 2));
		setPreferredSize(new Dimension(2 * STD_WIDTH / 3, STDH_TOP));
		// bottom, left, right, top
		setBorder(new EmptyBorder(30, 30, 30, 30));

		build();
	}

	private void build() {
		add(ratinglabel);
		add(ratingfield);
		add(scorelabel);
		add(scorefield);
	}

	// getters
	public static int getPlayer1() {
		try {
			return Integer.parseInt(ratingfield.getText());
		}
		catch (NumberFormatException e){
			return 999; // this will signal an invalid rating to the Calculator class
		}
	}

	public static double getScore() {
		try {
			return Double.parseDouble(scorefield.getText());
		} catch (NumberFormatException e){
			return -1; // this will signal an invalid score to the Calculator class
		}
	}

	// resets the panel to original state
	public static void cleanup() {
		ratingfield.setText("");
		scorefield.setText("");
	}

}
