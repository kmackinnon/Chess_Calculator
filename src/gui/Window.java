package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame implements Standard {

	// Containers and JPanels
	private static Container main;
	private static Container sub;

	private static SelfPanel selfPanel = new SelfPanel();
	private static KFactorPanel kFactorPanel = new KFactorPanel();
	private static OpponentsPanel opponentsPanel = new OpponentsPanel();
	private static ExecutionPanel executionPanel = new ExecutionPanel();

	public Window() {
		frameMake();
		panelMake();
	}

	private void frameMake() {

		// Setters for the window
		setTitle(TITLE);
		setPreferredSize(new Dimension(STD_WIDTH, STDH_TOP + STDH_OPP + STDH_EXE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void panelMake() {

		// setting up the X_AXIS Container for the first two panels
		sub = new Container();
		sub.setLayout(new BoxLayout(sub, BoxLayout.X_AXIS));
		sub.add(selfPanel);
		sub.add(kFactorPanel);

		// setting up the JFrame's container
		main = getContentPane();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(sub);
		main.add(opponentsPanel);
		main.add(executionPanel);

	}

}
