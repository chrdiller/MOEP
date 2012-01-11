package MoepClient.GUI;

import MoepClient.Interface;
import Moep.Karte;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.PopupMenuListener;

/**
 * Beschreibt die GUI, ueber die der User mit dem Programm interagiert
 * @author Philipp Herrle & Christian Diller
 */

public class GUI extends JFrame
{
    private Interface interfaceI;
    
    private Tisch tisch;
    private Hand hand;
    private Status status;
    private InitPanel initP;
    
    private StatusBar bar = new StatusBar();
    
    public GUI (Interface i, MouseAdapter [] adapter, PopupMenuListener popupListener) {
        super();
        this.setTitle("MOEP");
        this.setIconImage(new ImageIcon(this.getClass().getResource("../grafik/mIconKlein.png")).getImage());
        this.setLocation(new Point(Toolkit.getDefaultToolkit().getScreenSize().width / 8 ,Toolkit.getDefaultToolkit().getScreenSize().height / 8));
        interfaceI = i;
        
        //<editor-fold defaultstate="collapsed" desc="Layout">
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        initP = new InitPanel(adapter[2], adapter[3], adapter[4], popupListener);
        gbl.setConstraints(initP, gbc);
        this.add(initP);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        JPanel container = this.erzeugeFenster(adapter);
        gbl.setConstraints(container, gbc);
        this.add(container);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(bar, gbc);
        this.add(bar);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        status = new Status(adapter[5]);
        gbl.setConstraints(status, gbc);
        this.add(status);
        //</editor-fold>
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                interfaceI.beenden();
            }
        });
        
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 5));
        this.setResizable(false);
        
        this.pack();
        this.setVisible(true);
    }
    
    private JPanel erzeugeFenster (MouseAdapter[] adapter)
    {
        tisch = new Tisch(adapter[0]);
        hand = new Hand(15, 20,adapter[1]);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        
        JScrollPane scrollpane = new JScrollPane();
        scrollpane.setLayout(new ScrollPaneLayout());
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollpane.setPreferredSize(new Dimension(800,250));
        scrollpane.setViewportView(hand);
        
        container.add(tisch, BorderLayout.CENTER);
        container.add(scrollpane, BorderLayout.SOUTH);
        
        return container;
    }
    
    public void ablageAktualisieren (Karte karte)
    {
        tisch.ablageAktualisieren(karte);
    }
    
    public void ablageReset()
    {
        tisch.ablageReset();
    }
    
    public void setStatus(String stat)
    {
        status.setStatus(stat);
    }
    
    public void setSpielStatus(String stat)
    {
        status.setSpielStatus(stat);
    }
    
    public void handAktualisieren(List<Karte> karten)
    {
        hand.kartenAktualisieren(karten);
    }

    public void serverGefunden(String serverName)
    {
        initP.serverGefunden(serverName);
    }
}
