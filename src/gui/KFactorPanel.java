package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class KFactorPanel extends JPanel implements Standard {

	private static JRadioButton[] kFactorButton = new JRadioButton[KFACTOR_VALUES.length];
	private static ButtonGroup kFactorGroup = new ButtonGroup();
	private static JLabel name = new JLabel("K Factor");

	// setting panel's properties
	public KFactorPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(STD_WIDTH / 3, STDH_TOP));
		setBorder(new EmptyBorder(5, 15, 5, 5)); // bottom, left, right, top

		build();
	}

	// builds panel's contents
	private void build() {

		add(name);

		for (int i = 0; i < KFACTOR_VALUES.length; i++) {
			kFactorButton[i] = new JRadioButton();
			kFactorButton[i].setText(KFACTOR_VALUES[i]);
			kFactorGroup.add(kFactorButton[i]);
			add(kFactorButton[i]);
		}

		kFactorButton[1].setSelected(true); // defaults to most common
	}

	// returns selected radio button
	public static int kFactorSelected() {
		int k = 0;

		for (int i = 0; i < KFACTOR_VALUES.length; i++) {
			if (kFactorButton[i].isSelected()) {
				k = i;
			}
		}

		return k;
	}

	// resets the panel to original state
	public static void cleanup() {
		kFactorButton[1].setSelected(true);
	}

}
