package moepclient.netzwerk;

import MoepClient.Karte;
import MoepClient.Interface;
import moepserver.Server;
import moepserver.SpielerKI;
import moepserver.SpielerRemote;

/**
 * Schnittstelle, über die Moep mit dem Netzwerk kommuniziert und umgekehrt
 * @author Christian Diller

 */
public class Netz
{
    protected Verbindung verbindung;
    private Interface client;
    private Server server;

    public Netz(Interface _client, int kis)
    {
        client = _client;
        if(kis > 0)
            serverStarten(kis);
    }

    /**
     * Methode, die zum anmelden an einen Server verwendet wird
     * @param adresse Die IP-Adresse des Servers (ohne Port!)
     * @param name Der Spielername, mit dem die Anmeldung erfolgen soll
     * @return Anmeldung erfolgreich ja/nein
     */
    public boolean anmelden(String adresse, String name)
    {
        verbindung = new Verbindung(this, adresse);
        verbindung.start();
        try{Thread.currentThread().sleep(500);}catch(Exception ex){};
        
        return verbindung.sendeLogin(name);
    }
    
    public boolean anmelden(String name)
    {
        
        return true;
    }

    //Sende-Methoden
    /**
     * Sendet einen Spielerzug
     * @param karte Das Kartenobjekt, das diesen Spielerzug beschreibt
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeKarteLegen(Karte karte)
    {
        return verbindung.sendeKarteLegen(karte);
    }
    
    /**
     * Sendet die Anfrage, eine Karte zu ziehen
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeKarteZiehen()
    {
        return verbindung.sendeKarteZiehen();
    }
    
    /**
     * Teilt dem Server mit, dass der MoepButton gedrückt wurde
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeMoepButtonDruecken()
    {
        return verbindung.sendeMoepButton();
    }
    
    /**
     * Teilt dem Server nach dessen Anfrage mit, welche Farbe sich der Spieler
     * gewünscht hat
     * @param farbe Die ID der gewünschten Farbe
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeFarbeWuenschenAntwort(int farbe)
    {
        return verbindung.sendeFarbeWuenschenAntwort(farbe);
    }
    
    /**
     * Schliesst die Verbindung zu einem Server
     */
    public void verbindungSchliessen()
    {
        try{
            verbindung.verbindungSchliessen();
        }catch(NullPointerException exc){}
    }
    //Ende Sende-Methoden
    

    //Event-Methoden  
    protected void kickEvent(String grund)
    {
        client.kick(grund);
        
        verbindung.verbindungSchliessen();
    }

    protected void amZugEvent(boolean wert, String text)
    {
        client.status(text);
        client.dranSetzen(wert);
    }

    protected void zugLegalEvent(boolean wert, int illegalArt)
    {
        client.zugLegal(wert, illegalArt);
    }

    protected void handkarteEmpfangenEvent(Karte karte)
    {
        client.karteEmpfangen(karte);
    }

    protected void ablagestapelkarteEmpfangenEvent(Karte karte)
    {
        client.ablageAkt(karte);
    }

    protected void verbindungVerlorenEvent()
    {
        client.verbindungVerloren();
    }


    protected void moepButtonAntwortEvent(boolean rechtzeitig) {
        //client.moepButtonAntwort(rechtzeitig);
    }

    protected void farbeWuenschenEvent() {
        client.farbeWuenschenAnfrage();
    }
    
    protected void fehlerEvent(String meldung)
    {
        client.meldung(meldung);
    }
    //Ende Event-Methoden

    protected void textEmpfangenEvent(String text) {
        client.status(text);
    }

    protected void amZugEvent(boolean wert) {
        client.dranSetzen(wert);
    }

    protected void spielerLoginEvent(String spielername) {
        client.mitspielerLogin(spielername);
    }

    protected void spielerLogoutEvent(String spielername) {
        client.mitspielerLogout(spielername);
    }

    protected void spielEnde(boolean wert) {
        client.spielEnde(wert);
    }

    void spielerAmZugEvent(String spielername) {
        client.mitspielerAmZug(spielername);
    }

    private void serverStarten(int kis) {
        server = new Server();
        for(int i = 0; i < kis; i++)
            server.spielerHinzufuegen(new SpielerKI("Thorstn_" + (i+1), "KI_" + (i+1)));
    }
}
