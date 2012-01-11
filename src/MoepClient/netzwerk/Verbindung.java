package MoepClient.netzwerk;

import java.net.Socket;
import Moep.Karte;
import Moep.Statusmeldung;
import MoepClient.Interface;
import MoepClient.Spielerverwaltung;
import moepserver.Server;
import moepserver.SpielerKI;
import moepserver.SpielerLokal;

/**
 * Die Verbindung zwischen Server und Client per Netzwerk oder lokal
 * @author Christian Diller
 */

public class Verbindung
{
    private static final int PROTOKOLLVERSION = 2;
    private int protokollversionBestaetigt; //-1: Nicht empfangen; 0: Keine Übereinstimmung; 1: Übereinstimmung
    
    private String adresse;     //Die Adresse des Servers

    private boolean angemeldet; //Bereits beim Server angemeldet?
    private boolean letzterLoginFehlgeschlagen;
    
    private VerbindungReader reader;
    private VerbindungWriter writer;
    
    private boolean lokal;
    public int farbeWuenschenInt;
    
    private Interface client;
    private SpielerLokal spieler;
    private Server server;
    private Spielerverwaltung spVerwaltung;
    
    public Verbindung(String _adresse, Interface _client)
    {
        protokollversionBestaetigt = -1;
        farbeWuenschenInt = 4;
        
        client = _client;
        lokal = false;
        
        adresse = _adresse;
        angemeldet = false;
        letzterLoginFehlgeschlagen = false;
                
        try 
        {
            Socket clientSocket = new Socket(adresse, 11111);
            reader = new VerbindungReader(clientSocket);
            writer = new VerbindungWriter(clientSocket);
            reader.verbindung = this;
            reader.start();
        } 
        catch (Exception ex) 
        {
            Statusmeldung.fehlerAnzeigen("Verbinden zum Server fehlgeschlagen");
        }
    }
    
    public Verbindung(Spielerverwaltung _spVerwaltung, String servername, Interface _client)
    {
        client = _client;
        server = new Server(servername);
        spVerwaltung = _spVerwaltung;
        lokal = true;
        
        adresse = "";
        angemeldet = false;
        letzterLoginFehlgeschlagen = false;
                
        try 
        {
            Socket clientSocket = new Socket(adresse, 11111);
            reader = new VerbindungReader(clientSocket);
            writer = new VerbindungWriter(clientSocket);
            reader.verbindung = this;
            reader.start();
        } 
        catch (Exception ex) 
        {
            Statusmeldung.fehlerAnzeigen("Verbinden zum Server fehlgeschlagen");
        }
    }
    
    public boolean anmelden(String name)
    {
        if(lokal) {
            spieler = new SpielerLokal(this, name, "localhost");
            server.spielerHinzufuegen(spieler, 0);     
            for(int i = 0; i < spVerwaltung.gibKISpielerAnzahl(); i++)
                server.spielerHinzufuegen(new SpielerKI(spVerwaltung.gibKINamen()[i][0]), Integer.parseInt(spVerwaltung.gibKINamen()[i][1]));
            return true;
        }
        else {
            return sendeLogin(name);
        }
    }

    private boolean packetBearbeiten(String str)
    {
        Packet packet = Packet.erstellePacket(str);
        
        if(packet == null)
            return false;
        packet.clientEventAufrufen(this);
        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Sende-Methoden">
    public boolean sendeHandshake()
    {
        packetSenden(new Packet00Handshake(PROTOKOLLVERSION, false));
        int i = 0;
        while(protokollversionBestaetigt == -1) {
            try{Thread.currentThread().sleep(500);}catch(Exception ex){}
            i++;
            if(i > 20)
                return false;
        }
        return true;
    }
    
    public boolean sendeLogin(String name)
    {
        if(!sendeHandshake())
            return false;
        letzterLoginFehlgeschlagen = false;
        if(!packetSenden(new Packet01Login(name, false)))
            return false;
        int i = 0;
        while (!angemeldet)
        {
            if(letzterLoginFehlgeschlagen)
            {
                letzterLoginFehlgeschlagen = false;
                return false;
            }
            try{Thread.currentThread().sleep(500);}catch(Exception ex){}
            i++;
            if(i > 20)
                return false;
        }
        return true;
    }
    
    public boolean sendeKarteLegen(final Karte karte)
    {
        if(lokal) {
            spieler.karteLegenEvent(karte);
            return true;
        }
        else
            return packetSenden(new Packet13KarteLegen(karte));
    }
    
    public boolean sendeKarteZiehen()
    {
        if(lokal) {
            spieler.karteZiehenEvent();
            return true;
        }
        else
            return packetSenden(new Packet14KarteZiehen());
    }
    
    public boolean sendeMoepButton()
    {
        if(lokal) {
            new Thread(){public void run(){spieler.moepButtonEvent();}}.start();
            return true;
        }
        else
            return packetSenden(new Packet05MoepButton());
    }
    
    public boolean sendeFarbeWuenschenAntwort(int farbe)
    {
        farbeWuenschenInt = farbe;
        if(!lokal)
            return packetSenden(new Packet06FarbeWuenschen(farbe));
        return true;
    }
    
    private boolean packetSenden(Packet packet)
    {
        try
        {
            writer.senden(packet.gibData());
            return true;
        }
        catch(Exception ex){return false;}
    }
    //</editor-fold>
    
    public void schliessen()
    {
        angemeldet = false;
        reader.interrupt();
    }
    
    protected void angemeldetSetzen(boolean wert)
    {
        if(wert == false && angemeldet == false)
        {
            letzterLoginFehlgeschlagen = true;
            Statusmeldung.fehlerAnzeigen("Anmeldung vom Server abgewiesen");
        }
        angemeldet = wert;
    }

    protected void verbindungVerlorenEvent() {
        client.verbindungVerloren();
    }

    protected void neuesPacket(String data)
    {
        if(!packetBearbeiten(data))
        {
            Statusmeldung.fehlerAnzeigen("Ungültiges Protokoll (Falscher Server?) Data:" + data);
        }
    }
    
    public void serverBeenden()
    {
        server.beenden();
    }

    //<editor-fold defaultstate="collapsed" desc="Event-Methoden">
    public void kickEvent(String grund)
    {
        client.kick(grund);
        schliessen();
    }
    
    public void zugLegalEvent(boolean legal, int illegalArt)
    {
        client.zugLegal(legal, illegalArt);
    }
    
    public void amZugEvent(boolean wert)
    {
        client.dranSetzen(wert);
    }
    
    public void farbeWuenschenEvent()
    {
        client.farbeWuenschenAnfrage();
    }
    
    public void textEmpfangenEvent(String text)
    {
        client.status(text);
    }
    
    public void spielerLoginEvent(String spielername, int kartenzahl)
    {
        client.mitspielerLogin(spielername);
        client.spielerKartenzahlUpdate(spielername, kartenzahl);
    }
    
    public void spielerLogoutEvent(String spielername)
    {
        client.mitspielerLogout(spielername);
    }
    
    public void spielerAmZugEvent(String spielername)
    {
        client.mitspielerAmZug(spielername);
    }
    
    public void spielerKartenzahlUpdate(String spielername, int kartenzahl)
    {
        System.out.println("V_KU: " + spielername + kartenzahl);
        client.spielerKartenzahlUpdate(spielername, kartenzahl);
    }
    
    public void spielEnde(boolean gewonnen)
    {
        client.spielEnde(gewonnen);
    }
    
    public void handkarteEmpfangenEvent(Karte karte)
    {
        client.karteEmpfangen(karte);
    }
    
    public void ablagestapelkarteEmpfangenEvent(Karte karte)
    {
        client.ablageAkt(karte);
    }
    
    public void protokollversionErgebnis(boolean gleicheVersion) {
        if(!gleicheVersion) {
            Statusmeldung.fehlerAnzeigen("Unterschiedliche Protokollversionen");
            protokollversionBestaetigt = 0;
        }
        else
            protokollversionBestaetigt = 1;
    }
    //</editor-fold>
    
}
