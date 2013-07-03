
package calculate;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ExecutionPanel extends JPanel implements Standard{
    
    private static JButton clean = new JButton("Clean");
    private static JButton calculate = new JButton("Calculate");
    private static JButton exit = new JButton("Exit");
    
    public ExecutionPanel(){
         //setting panel's properties
        setLayout(new FlowLayout());
        //setBackground(Color.blue);
        setPreferredSize(new Dimension(STD_WIDTH, STDH_OPP));
            //bottom, left, right, top
        setBorder(new EmptyBorder(15, 10, 10, 10) );
        
        build();
    }
    
    private void build(){
        add(calculate);
        add(clean);
        add(exit);
        
        events();
    }
    
    private static void events(){
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        }); 
        
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int player1 = SelfPanel.getPlayer1();
                double score = SelfPanel.getScore();
                int kfactor = Integer.parseInt(KFACTOR_VALUES[KFactorPanel.kfactorselected()]);
                int[] players = OpponentsPanel.getPlayers();
                
               Tournament event = new Tournament(player1, score, kfactor, players);
               Calculator calc = new Calculator(event);
                
            }
        }); 
        
        clean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                OpponentsPanel.cleanup();
                SelfPanel.cleanup();
                KFactorPanel.cleanup();
            }
        }); 
    }
    
}
