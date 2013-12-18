package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import calculate.Calculator;
import calculate.Tournament;

@SuppressWarnings("serial")
public class ExecutionPanel extends JPanel implements Standard {

	private static JButton clean = new JButton("Clean");
	private static JButton calculate = new JButton("Calculate");
	private static JButton exit = new JButton("Exit");

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
			public void actionPerformed(ActionEvent e) {
				int userRating = SelfPanel.getUserRating();
				double score = SelfPanel.getScore();
				int kfactor = Integer.parseInt(KFACTOR_VALUES[KFactorPanel.kFactorSelected()]);
				int[] players = OpponentsPanel.getPlayers();

				Tournament event = new Tournament(userRating, score, kfactor, players);
				new Calculator(event);

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

}
