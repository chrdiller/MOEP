
package MoepClient;

import MoepClient.GUI.GUI;
import MoepClient.GUI.FarbeWuenschenDialog;
import MoepClient.GUI.LoginPanel;
import MoepClient.GUI.Hand;
import moepclient.netzwerk.Netz;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import javax.swing.ImageIcon;
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
    
    
    public Interface (){
        
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
            
            new MouseAdapter() { //Login
                @Override
                public void mousePressed(MouseEvent me) {
                    LoginPanel lP = (LoginPanel) me.getComponent().getParent();
                    String[] information = lP.getInformation();
                    
                    if (information[0].isEmpty()){
                        meldung("Bitte eine IP-Adresse angeben!");
                    }
                    else if(information[1].isEmpty()){
                        meldung("Bitte einen Namen angeben!");
                    }
                    else{
                        if(!eingeloggt)
                            g.LoginOut(!netz.anmelden(information[0], information[1]));
                        eingeloggt = true;
                    }
                    
                }
            },
            
            new MouseAdapter() { //Logout
                @Override
                public void mousePressed(MouseEvent me) {
                    if(eingeloggt)
                        if(yesNoDialog() == true){
                            logout();
                        }
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
        
        
        netz = new Netz(this, 0); //TODO: Zahl von UI holen!!
        
        
        g = new GUI(this, adapter);
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

}
