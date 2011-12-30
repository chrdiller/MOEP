
package MoepClient;

import Moep.Karte;
import MoepClient.GUI.GUI;
import MoepClient.GUI.FarbeWuenschenDialog;
import MoepClient.GUI.Hand;
import MoepClient.GUI.InitPanel;
import MoepClient.GUI.SpielerDialog;
import java.awt.event.WindowEvent;
import MoepClient.netzwerk.Netz;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Die Interface-Klasse, der Knotenpunkt zwischen Netzwerk, GUI und Moep
 * @author Philipp Herrle & Christian Diller
 */

public class Interface
{

    private boolean dran;
    private boolean eingeloggt;
    
    private GUI g;
    private Moep m;
    private Netz netz;
    
    private Map<String, String> server;
    private Spielerverwaltung spieler;
    
    private ServerSuche serversuche;
    private String servername;
    
    public Interface ()
    {
        server = new HashMap<String, String>();
        //<editor-fold defaultstate="collapsed" desc="Mouse-Adapter">
        MouseAdapter[] adapter = new MouseAdapter[] {
            
            new MouseAdapter() { //KarteZiehen
                @Override
                public void mousePressed(MouseEvent me){
                    zieheKarte();
                }
            },
            
            new MouseAdapter() { //KarteLegen
                @Override
                public void mousePressed(MouseEvent me) {
                    Hand hand = (Hand)me.getComponent();
                    int index = hand.gibIndexKarte(me.getPoint());
                    
                    if (index != -1){
                        karteLegen(index);
                    }
                }
            },
            
            new MouseAdapter() { //spielerDialog
                @Override
                public void mousePressed(final MouseEvent me) {
                    System.out.println("spielerDialog");
                    SpielerDialog spDialog = new SpielerDialog(spieler);
                    spDialog.addWindowListener(new WindowListener(){
                    @Override
                    public void windowClosing(WindowEvent e) {
                        spieler = ((SpielerDialog)e.getSource()).gibSpielerverwaltung();
                        g.setEnabled(true);
                    }
                    @Override
                    public void windowOpened(WindowEvent e) { }
                    @Override
                    public void windowClosed(WindowEvent e) { }
                    @Override
                    public void windowIconified(WindowEvent e) { }
                    @Override
                    public void windowDeiconified(WindowEvent e) { }
                    @Override
                    public void windowActivated(WindowEvent e) { }
                    @Override
                    public void windowDeactivated(WindowEvent e) { }
                    });
                    spDialog.gibFertigButton().addMouseListener(            
                        new MouseAdapter() { //SpielerDialog-Fertig
                            @Override
                            public void mousePressed(MouseEvent me) {
                                spieler = ((SpielerDialog)((JButton)me.getSource()).getParent().getParent().getParent().getParent()).gibSpielerverwaltung();
                                ((SpielerDialog)((JButton)me.getSource()).getParent().getParent().getParent().getParent()).dispose();
                                g.setEnabled(true);
                            }
                        });
                    g.setEnabled(false);
                    /*if(!eingeloggt)
                    {
                        g.LoginOut(!netz.anmelden(information[0], information[1]));
                        eingeloggt = true; 
                    }*/
                }
            },
            
            new MouseAdapter() { //Erstellen
                @Override
                public void mousePressed(MouseEvent me) {
                    System.out.println("erstellen");
                    serverErstellen(serversuche.istEinzigerServer());
                    servername = spieler.gibEigenenNamen() + "_Server";
                    }
            },
            
            new MouseAdapter() { //Beitreten
                @Override
                public void mousePressed(MouseEvent me) {
                    System.out.println("beitreten");
                    InitPanel ip = (InitPanel)me.getComponent().getParent();
                    netz = new Netz(Interface.this);
                    netz.anmelden(server.get(ip.gibServername()), ip.gibName());
                }
            },
            
            new MouseAdapter (){ //MoepButton
                @Override
                public void mousePressed(MouseEvent e) {
                    if(dran)
                    {
                        netz.sendeMoepButtonDruecken();
                        playSound("moep");
                    }
                    else
                        playSound("beep");
                }
            }
                
        };
        //</editor-fold>
        
        dran = false;
        eingeloggt = false;
      
        m = new Moep();        
        
        g = new GUI(this, adapter);
        
        serversuche = new ServerSuche(Interface.this);
        serversuche.start();
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Karten: Ziehen, Legen, Empfangen">
    private void zieheKarte (){
        if (!dran){
            playSound("beep");
        }
        else{
            playSound("click");
            netz.sendeKarteZiehen();
            g.handAktualisieren(m.gibHand());
        }
    }
    
    private void karteLegen (int index){
        if (!dran){
            playSound("beep");
            return;
        }
        m.zuLegen(index);
        if (!netz.sendeKarteLegen(m.gibKarteAt(index))){
            meldung("Fehler beim Senden :(");
        }
        else{
            playSound("click");
        }
    }
    
    public void karteEmpfangen (Karte karte){
        m.ziehen(karte);
        g.handAktualisieren(m.gibHand());
    }
    
    public void ablageAkt (Karte k){
        g.ablageAktualisieren(k);
    }
    //</editor-fold>
    
     
    //<editor-fold defaultstate="collapsed" desc="Serversteuerung: zugLegal, dranSetzen, status, kick">
    public void zugLegal(boolean legal, int illegalArt)
    {
        if (legal)
        {
            m.legen();
            g.handAktualisieren(m.gibHand());
        }
        else
        {
            playSound("beep");  
            if(illegalArt == 1)
                m.legen();
                g.handAktualisieren(m.gibHand());
        }

    }
    
    public void dranSetzen(boolean dranH)
    {
        dran = dranH;
    }
    
    public void status (String statusH)
    {
        m.addNachricht(statusH);
        g.setStatus(m.gibStatus());
    }
    
    public void kick(String Grund)
    {
        if(eingeloggt)
        {
            meldung("Vom Server gekickt: " + Grund);
            logout();               
        }

    }
    //</editor-fold>
   
    
    //<editor-fold defaultstate="collapsed" desc="FarbeWuenschen">
    public void farbeWuenschenAnfrage()
    {
        FarbeWuenschenDialog farbeWuenschenDialog = new FarbeWuenschenDialog(this);
        g.setEnabled(false);
    }
    
    public void sendeFarbeWuenschenAntwort(int farbe)
    {
        netz.sendeFarbeWuenschenAntwort(farbe);
        g.setEnabled(true);
    }
    //</editor-fold>
    
        
    //<editor-fold defaultstate="collapsed" desc="Meldung & Logout-Dialog">
    public void meldung(String meldung)
    {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("grafik/dialogSet.png"));
        icon.setImage(new JPanel().createImage(new FilteredImageSource(icon.getImage().getSource(), new CropImageFilter(3 * 32, 0 * 32, 32, 32))));

        JOptionPane.showMessageDialog(
                g,
                meldung,
                "Fehler",
                JOptionPane.ERROR_MESSAGE,
                icon);
    }
    
    public boolean yesNoDialog()
    {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("grafik/dialogSet.png"));
        icon.setImage(new JPanel().createImage(new FilteredImageSource(icon.getImage().getSource(), new CropImageFilter(1 * 32, 0 * 32, 32, 32))));
        
        return JOptionPane.showConfirmDialog(
                g,
                "Wirklich aus dem Spiel ausloggen?",
                "Logout", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon
                ) == JOptionPane.YES_OPTION;
    }
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Mitspieler-Aktionen">
    public void mitspielerLogin(String name)
    {
        m.mitspielerLogin(name);
        g.setSpielStatus(m.gibSpielerliste());
    }
    
    public void mitspielerLogout(String name)
    {
        m.mitspielerLogout(name);
        g.setSpielStatus(m.gibSpielerliste());
    }
    
    public void mitspielerAmZug(String spielername) {
        m.mitspielerAmZug(spielername);
        g.setSpielStatus(m.gibSpielerliste());
    }
    
    public void spielerKartenzahlUpdate(String spielername, int kartenzahl) {
        m.mitspielerKartenzahlUpdate(spielername, kartenzahl);
    }
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Spielende: reset, logout, beenden">
    public void spielEnde(boolean gewonnen)
    {
        m.setzeHand(new ArrayList<Karte>());
        g.handAktualisieren(m.gibHand());
        g.ablageReset();
        if(gewonnen)
            playSound("applause");
        else
            playSound("ooh");
    }
    
    private void logout()
    {
        eingeloggt = false;
        g.LoginOut(true);
        dran = false;
        netz.verbindungSchliessen();
        spielEnde(false);
        m.statusReset();
        g.setSpielStatus(m.gibSpielerliste());
        g.setStatus(m.gibStatus());
    }
    
    public void beenden()
    {
        g.dispose();
        System.exit(0);
    }
    //</editor-fold>

    
    public void verbindungVerloren() 
    {
        if(eingeloggt)
        {
            meldung("Verbindung verloren! :(");
            logout();
        }
    }
       
    private void playSound(String soundName)
    {
        AudioClip clip = Applet.newAudioClip(this.getClass().getResource("sound/" + soundName + ".au"));
        clip.play();
    }

    public void serverGefunden(String serverName, String serverAdresse)
    {
        server.put(serverName, serverAdresse);
        g.serverGefunden(serverName);
    }

    public void serverErstellen(boolean einzigerServer) {
        if(einzigerServer){
            if(spieler.istGueltig())
            {
                netz = new Netz(Interface.this, spieler, servername);
                netz.anmelden("", spieler.gibEigenenNamen());
            }
        }
        else
        {
            //Meldung!
        }
    }


}
