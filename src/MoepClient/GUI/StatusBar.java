package MoepClient.GUI;

import MoepClient.Statusmeldung;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 * Beschreibt die Statusbar, über die dem Benutzer Statusmeldungen angezeigt werden
 * @author Christian Diller
 */

public class StatusBar extends JLabel {
    
    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        Statusmeldung.statusbar = this;
    }
    
    public void setzeText(String meldung) {
        setText(meldung);        
    }        
}
