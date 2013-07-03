
package calculate;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class KFactorPanel extends JPanel implements Standard{
    // Fields and buttons
    private static JRadioButton[] kfactorbutton = new JRadioButton[KFACTOR_VALUES.length];
    private static ButtonGroup kfb_group = new ButtonGroup();
    private static JLabel name = new JLabel("K Factor");
    
    public KFactorPanel(){
        
        //setting panel's properties
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(STD_WIDTH/3, STDH_TOP));
            //bottom, left, right, top
        setBorder(new EmptyBorder(15, 20, 5, 5) );  
        
        build();
        
    }
    
    //builds panel's contents
    private void build(){
        
        add(name);
        
        for(int i = 0; i < KFACTOR_VALUES.length; i++){
            kfactorbutton[i] = new JRadioButton();
            kfactorbutton[i].setText(KFACTOR_VALUES[i]);
            kfb_group.add(kfactorbutton[i]);
            add(kfactorbutton[i]);
        }
        
        kfactorbutton[0].setSelected(true);
        
    }
    
    //returns selected radiobutton
    public static int kfactorselected(){
        int k = 0;
        
        for(int i = 0; i < KFACTOR_VALUES.length; i++){
            if(kfactorbutton[i].isSelected()){
                k = i;
            }
        }

        return k;
    }
    
    //cleans up the panel (resets it to startup)
    public static void cleanup(){
        kfactorbutton[0].setSelected(true);
    }
    
}
