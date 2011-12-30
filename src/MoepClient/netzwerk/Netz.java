package MoepClient.netzwerk;

import Moep.Karte;
import MoepClient.Interface;
import MoepClient.Spielerverwaltung;
import moepserver.Server;
import moepserver.SpielerKI;
import moepserver.SpielerLokal;

/**
 * Schnittstelle, über die Moep mit dem Netzwerk kommuniziert und umgekehrt
 * @author Christian Diller
 */

public class Netz
{
    public Verbindung verbindung;
    private Interface client;
    private SpielerLokal spieler;
    private Server server;
    private Spielerverwaltung spVerwaltung;
    private boolean lokal;
    public int farbeWuenschenInt;

    public Netz(Interface _client)
    {
        client = _client;
        lokal = false;   
    }
    
    public Netz(Interface _client, Spielerverwaltung _spVerwaltung, String servername)
    {
        server = new Server(servername);
        client = _client;
        spVerwaltung = _spVerwaltung;
        lokal = true;
    }

    /**
     * Methode, die zum anmelden an einen Server verwendet wird
     * @param adresse Die IP-Adresse des Servers (ohne Port!)
     * @param name Der Spielername, mit dem die Anmeldung erfolgen soll
     * @return Anmeldung erfolgreich ja/nein
     */
    public boolean anmelden(String adresse, String name)
    {
        if(lokal) {
            spieler = new SpielerLokal(this, name, "localhost");
            server.spielerHinzufuegen(spieler, 0);
            for(int i = 0; i < spVerwaltung.gibKISpielerAnzahl(); i++)
                server.spielerHinzufuegen(new SpielerKI(spVerwaltung.gibKINamen()[i][0]), Integer.parseInt(spVerwaltung.gibKINamen()[i][1]));
            return true;
        }
        else {
            verbindung = new Verbindung(this, adresse);
            verbindung.start();
            try{Thread.currentThread().sleep(500);}catch(Exception ex){};

            return verbindung.sendeLogin(name);
        }
    }
    

    //Sende-Methoden
    /**
     * Sendet einen Spielerzug
     * @param karte Das Kartenobjekt, das diesen Spielerzug beschreibt
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeKarteLegen(final Karte karte)
    {
        if(lokal) {
            new Thread(){public void run(){spieler.karteLegenEvent(karte);}}.start();
            return true;
        }
        else
            return verbindung.sendeKarteLegen(karte);
    }
    
    /**
     * Sendet die Anfrage, eine Karte zu ziehen
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeKarteZiehen()
    {
        if(lokal) {
            new Thread(){public void run(){spieler.karteZiehenEvent();}}.start();
            return true;
        } 
        else
            return verbindung.sendeKarteZiehen();
    }
    
    /**
     * Teilt dem Server mit, dass der MoepButton gedrückt wurde
     * @return Erfolgreich gesendet ja/nein
     */
    public boolean sendeMoepButtonDruecken()
    {
        if(lokal) {
            new Thread(){public void run(){spieler.moepButtonEvent();}}.start();
            return true;
        }
        else
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
        farbeWuenschenInt = farbe;
        if(!lokal)
            return verbindung.sendeFarbeWuenschenAntwort(farbe);
        return true;
    }
    
    /**
     * Schliesst die Verbindung zu einem Server
     */
    public void verbindungSchliessen()
    {
        try{
            verbindung.verbindungSchliessen();
        }catch(Exception ex){}
    }
    //Ende Sende-Methoden
    

    //Event-Methoden  
    public void kickEvent(String grund)
    {
        client.kick(grund);
        
        try{verbindung.verbindungSchliessen();}catch(Exception ex){}
    }

    public void amZugEvent(boolean wert, String text)
    {
        client.status(text);
        client.dranSetzen(wert);
    }

    public void zugLegalEvent(boolean wert, int illegalArt)
    {
        client.zugLegal(wert, illegalArt);
    }

    public void handkarteEmpfangenEvent(Karte karte)
    {
        client.karteEmpfangen(karte);
    }

    public void ablagestapelkarteEmpfangenEvent(Karte karte)
    {
        client.ablageAkt(karte);
    }

    public void verbindungVerlorenEvent()
    {
        client.verbindungVerloren();
    }


    public void moepButtonAntwortEvent(boolean rechtzeitig) {
        //client.moepButtonAntwort(rechtzeitig);
    }

    public void farbeWuenschenEvent() {
        client.farbeWuenschenAnfrage();
    }
    
    public void fehlerEvent(String meldung)
    {
        client.meldung(meldung);
    }
    //Ende Event-Methoden

    public void textEmpfangenEvent(String text) {
        client.status(text);
    }

    public void amZugEvent(boolean wert) {
        client.dranSetzen(wert);
    }

    public void spielerLoginEvent(String spielername) {
        client.mitspielerLogin(spielername);
    }

    public void spielerLogoutEvent(String spielername) {
        client.mitspielerLogout(spielername);
    }

    public void spielEnde(boolean wert) {
        client.spielEnde(wert);
    }

    public void spielerAmZugEvent(String spielername) {
        client.mitspielerAmZug(spielername);
    }

    public void spielerKartenzahlUpdate(String spielername, int kartenzahl) {
        client.spielerKartenzahlUpdate(spielername, kartenzahl);
    }
}
