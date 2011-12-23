
package MoepClient.UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Hauptfenster von Moep
 * @author Christian Diller
 * @version 1.0
 */
public class MoepWindow extends JFrame
{
    
    public MoepWindow(int breite, int hoehe, String titel)
    {
        super();
        try{UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");}catch(Exception ex){}
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setBounds(50, 50, breite, hoehe);
        this.setResizable(false);
        this.setVisible(true);
        
        this.setTitle(titel);
        
        this.setVisible(true);
    }
    
    private void groesseSetzen(int breite, int hoehe)
    {
        this.setBounds(50, 50, breite, hoehe);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public void panelEinsetzen(JPanel panel)
    {
        this.add(panel);
        this.pack();
    }

}
