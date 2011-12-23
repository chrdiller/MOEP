
package MoepClient;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Beschreibt den Farbe-Wuenschen-Dialog
 * @author Philipp Herrle
 * @version BETA 1.1
 */
public class FarbeWuenschenDialog extends javax.swing.JFrame {
    
    private Interface i;
    
    /** Erstellt die neue Form farbeWuenschenDialog */
    public FarbeWuenschenDialog(Interface iH) {
        i = iH;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Wunschfarbe");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setSize(new java.awt.Dimension(298, 330));
        setResizable(false);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Point point = new Point((screenSize.width - this.getWidth()) / 2 ,(screenSize.height - this.getHeight()) / 2);
        
        this.setLocation(point);


        jLabel1.setBackground(new java.awt.Color(255, 0, 0));
        jLabel1.setOpaque(true);
        jLabel1.setBorder(BorderFactory.createRaisedBevelBorder());
        jLabel1.addMouseListener(this.getListener());
        jLabel1.setName("1");

        jLabel2.setBackground(new java.awt.Color(255, 255, 0));
        jLabel2.setOpaque(true);
        jLabel2.setBorder(BorderFactory.createRaisedBevelBorder());
        jLabel2.addMouseListener(this.getListener());
        jLabel2.setName("3");

        jLabel3.setBackground(new java.awt.Color(51, 255, 0));
        jLabel3.setOpaque(true);
        jLabel3.setBorder(BorderFactory.createRaisedBevelBorder());
        jLabel3.addMouseListener(this.getListener());
        jLabel3.setName("2");

        jLabel4.setBackground(new java.awt.Color(0, 0, 255));
        jLabel4.setOpaque(true);
        jLabel4.setBorder(BorderFactory.createRaisedBevelBorder());
        jLabel4.addMouseListener(this.getListener());
        jLabel4.setName("0");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        
        setVisible(true);
    }// </editor-fold>
    
    private MouseAdapter getListener(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me){
                JLabel label = (JLabel)me.getComponent();
                
                int farbe = Integer.parseInt(label.getName());
                
                i.sendeFarbeWuenschenAntwort(farbe);
                
                dispose();
            }
        };
    }
        
    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration
}
