
package MoepClient.GUI;

import Moep.Karte;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jdesktop.layout.GroupLayout;

/**
 * Beschreibt den Tisch, auf dem die Karten liegen
 * @author Philipp Herrle

 */
public class Tisch extends JPanel
{    
    private IconGraphik jLabel1;
    private javax.swing.JLabel jLabel2;
    
    public Tisch(MouseAdapter adapter){
        initComponents(adapter);
    }
    
    private void initComponents(MouseAdapter adapter) {
        
        jLabel1 = new IconGraphik(null,null,null);
        jLabel2 = new IconGraphik(null,null,adapter);
        
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        
        this.setBackground(Color.green);
        this.setOpaque(true);
        
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 166, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 108, Short.MAX_VALUE)
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 166, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }
    // Variables declaration - do not modify
    
    public void ablageAktualisieren (Karte karte){
        jLabel1.changeIcon(karte.gibBild());
        
        this.repaint();
    }
    
    public void ablageReset()
    {
        jLabel1.changeIcon(jLabel1.getDefaultIcon());
    }
}
