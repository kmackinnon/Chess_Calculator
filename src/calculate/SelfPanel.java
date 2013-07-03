
package calculate;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SelfPanel extends JPanel implements Standard{
    //Fields and labels
    private static JLabel ratinglabel = new JLabel("Rating:");
    private static JTextField ratingfield = new JTextField();
    private static JLabel scorelabel = new JLabel("Score:");
    private static JTextField scorefield = new JTextField();
    
    public SelfPanel(){
        
        //setting panel's properties
        setLayout(new GridLayout(2,2));
        //setBackground(Color.blue);
        setPreferredSize(new Dimension(2*STD_WIDTH/3, STDH_TOP));
            //bottom, left, right, top
        setBorder(new EmptyBorder(30, 30, 30, 30) );
        
        build();
        
    }
    
    private void build(){
        
        add(ratinglabel);
        add(ratingfield);
        add(scorelabel);
        add(scorefield);
        
    }
    
    public static int getPlayer1(){
        return Integer.parseInt(ratingfield.getText());
    }
    public static double getScore(){
        return Double.parseDouble(scorefield.getText());
    }
    
    public static void cleanup(){
        ratingfield.setText("");
        scorefield.setText("");
    }
    
}
