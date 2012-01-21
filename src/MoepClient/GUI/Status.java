package MoepClient.GUI;

import Moep.Ressourcen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * Beschreibt die Statusleiste am rechten Rand mit Spielerliste, 
 * Meldungen und dem Moep-Button
 * @author Philipp Herrle
 */
public class Status extends javax.swing.JPanel
{

    public Status(MouseAdapter moep)
    {
        initComponents(moep);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents(MouseAdapter moep)
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel3 = new KarteGrafik(Ressourcen.moepButton, null, moep);

        setBounds(new java.awt.Rectangle(0, 0, 260, 500));
        setPreferredSize(new java.awt.Dimension(260, 500));

        jLabel1.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabel2.setBorder(BorderFactory.createLoweredBevelBorder());

        jLabel1.setBackground(Color.WHITE);
        jLabel1.setOpaque(true);

        jLabel2.setBackground(Color.WHITE);
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(238, 238, 238));
        jLabel3.setOpaque(true);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(layout.createSequentialGroup().addContainerGap().add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup() //.addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()));

    }// </editor-fold>
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;

    public void setStatus(String status)
    {
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText(status);
    }

    public void setSpielStatus(String status)
    {
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText(status);
        jLabel1.setSize(new Dimension(236, 272));
    }
}
