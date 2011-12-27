
package MoepClient.GUI;

import MoepClient.Interface;
import MoepClient.Karte;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/**
 * Beschreibt die GUI, ueber die der User mit dem Programm interagiert
 * @author Philipp Herrle

 */
public class GUI extends JFrame{
    
    private Interface interfaceI;
    
    private Tisch tisch;
    private Hand hand;
    private Status status;
    //private LoginPanel loginP;
    private InitPanel initP;
    
    public GUI (Interface i, MouseAdapter [] adapter){
        super();
        
        interfaceI = i;
        
        
        
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        
        
        
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        JPanel container = this.erzeugeFenster(adapter);
        
        gbl.setConstraints(container, gbc);
        this.add(container);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        
        status = new Status(adapter[4]);
        
        gbl.setConstraints(status, gbc);
        this.add(status);
        
        
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                interfaceI.beenden();
            }
        });
        
        this.pack();
        
        this.setVisible(true);
    }
    
    private JPanel erzeugeFenster (MouseAdapter[] adapter){
        
        tisch = new Tisch(adapter[0]);
        
        hand = new Hand(15, 20,adapter[1]);
        
        //loginP = new LoginPanel (adapter[2], adapter[3]);
        initP = new InitPanel();
        
        JPanel container = new JPanel();
        
        container.setLayout(new BorderLayout());
        
        JScrollPane scrollpane = new JScrollPane();
        
        scrollpane.setLayout(new ScrollPaneLayout());
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        scrollpane.setPreferredSize(new Dimension(800,250));
        scrollpane.setViewportView(hand);
        
        //container.add(loginP, BorderLayout.NORTH);
        container.add(initP, BorderLayout.NORTH);
        
        container.add(tisch, BorderLayout.CENTER);
        
        container.add(scrollpane, BorderLayout.SOUTH);
        container.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 0));
        
        return container;
    }
    
    public void ablageAktualisieren (Karte karte){
        tisch.ablageAktualisieren(karte);
    }
    
    public void ablageReset()
    {
        tisch.ablageReset();
    }
    
    public void setStatus(String stat){
        status.setStatus(stat);
    }
    
    public void setSpielStatus(String stat){
        status.setSpielStatus(stat);
    }
    
    public void LoginOut (boolean login){

        //loginP.changeStatus(login);
        
        if (login){
            tisch.ablageAktualisieren(new Karte(0,0));
            
        }
    }
    
    public void handAktualisieren(List<Karte> karten){
        hand.kartenAktualisieren(karten);
    }
}
