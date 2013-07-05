package calculate;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame implements Standard {

	// Containers and JPanels
	private static Container main;
	private static Container sub;

	private static SelfPanel selfpanel = new SelfPanel();
	private static KFactorPanel kfactorpanel = new KFactorPanel();
	private static OpponentsPanel opponentspanel = new OpponentsPanel();
	private static ExecutionPanel executionpanel = new ExecutionPanel();

	public Window() {
		framemake();
		panelmake();
	}

	private void framemake() {

		// Setters for the window
		setTitle(TITLE);
		setPreferredSize(new Dimension(STD_WIDTH, STDH_TOP + STDH_OPP + STDH_EXE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void panelmake() {

		// setting up the X_AXIS Container for the first two panels
		sub = new Container();
		sub.setLayout(new BoxLayout(sub, BoxLayout.X_AXIS));
		sub.add(selfpanel);
		sub.add(kfactorpanel);

		// setting up the JFrame's container
		main = getContentPane();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(sub);
		main.add(opponentspanel);
		main.add(executionpanel);

	}

}
