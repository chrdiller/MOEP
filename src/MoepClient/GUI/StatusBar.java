
package MoepClient.GUI;

import java.awt.Dimension;
import javax.swing.JLabel;

/**
 *
 * @author Christian Diller
 */
public class StatusBar extends JLabel {
    
    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("Ready");
    }
    
    public void setMessage(String meldung) {
        setText(meldung);        
    }        
}
