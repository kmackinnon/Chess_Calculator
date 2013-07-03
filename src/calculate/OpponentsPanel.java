
package calculate;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class OpponentsPanel extends JPanel implements Standard{
    
    private static JLabel[] opponentnumber = new JLabel[15];
    private static JTextField[] opponentfield = new JTextField[15];
    
    public OpponentsPanel(){
        //setting panel's properties
        setLayout(new GridLayout(5,6));
        setPreferredSize(new Dimension(STD_WIDTH, STDH_OPP));
            //bottom, left, right, top
        setBorder(new EmptyBorder(20, 10, 10, 30) );
        
        build();
    
    }
    
    // builds the panel's contents
    private void build(){
        
        for (int i = 0; i<15; i++){
            
            //Label Creation and Implementation
            String numberstring;
            int use = i+1;
            if (use<=9){
                numberstring = "      " + use + ".";
            } else {
                numberstring = "     " + use + ".";
            }
            opponentnumber[i] = new JLabel();
            opponentnumber[i].setText(numberstring);
            add(opponentnumber[i]);
        
            //Field Creation and Implementation
            opponentfield[i] = new JTextField();
            add(opponentfield[i]);
        }
    }
    
    //gets all the players from the fields
    public static int[] getPlayers(){
        int[] players;
        int count = 0;
        for(int i = 0; i < 15; i++){
            if(!opponentfield[i].getText().isEmpty()){
                count++;
            }
        }
        players = new int[count];
        count = 0;
        for(int i = 0; i < 15; i++){
            if(!opponentfield[i].getText().isEmpty()){
                players[count] = Integer.parseInt(opponentfield[i].getText());
                count++;
            }
        }
        
        return players;
    }
    
    //sets all the fields to empty
    public static void cleanup(){
        for(int i = 0; i < 15; i++){
            opponentfield[i].setText("");
        }
    }
    
}
