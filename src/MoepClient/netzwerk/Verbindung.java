package moepclient.netzwerk;

import java.net.Socket;
import MoepClient.Karte;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Verbindung, über die der gesamte Netzwerkverkehr läuft
 * @author Christian Diller

 */
public class Verbindung extends Thread
{
    private String adresse;     //Die Adresse des Servers
    private String name;       //Der zu verwendende Spielername

    private boolean angemeldet; //Bereits beim Server angemeldet?
    private boolean letzterLoginFehlgeschlagen;
    

    private Netz netz;
    
    private VerbindungReaderThread reader;
    private VerbindungWriterThread writer;
    
    private static final Logger logger = Logger.getLogger("MoepClientNetz");
    
    public Verbindung(Netz _netz, String _adresse)
    {
        netz = _netz;
        adresse = _adresse;
        angemeldet = false;
        letzterLoginFehlgeschlagen = false;
        int port = 11111;
        
        try {
            port = Integer.valueOf(adresse.substring(adresse.indexOf(":") + 1)).intValue();
            adresse = adresse.split(":")[0];
        }
        catch(Exception ex){}
        
        try 
        {
            Socket clientSocket = new Socket(adresse, port);
            reader = new VerbindungReaderThread(clientSocket);
            writer = new VerbindungWriterThread(clientSocket);
        } 
        catch (Exception ex) 
        {
            netz.fehlerEvent("Fehler beim Verbinden zum Server");
        }
    }

    @Override
    public void run()
    {
        reader.verbindung = this;
        reader.start();
        writer.start();
        while(true)
        {
            synchronized(this)
            {
                try
                {
                    this.wait();
                }
                catch(InterruptedException ex)
                {
                    logger.log(Level.WARNING, "Verbindung wurde beim Warten unterbrochen");
                }
            }
            
            while(!reader.istLeer())
            {
                String data = reader.pop();
                if(!packetBearbeiten(data))
                {
                    logger.log(Level.WARNING, "Fehler im Protokoll (Falscher Server?) Data: {0}", data);
                }
            }
        }
    }

    private boolean packetBearbeiten(String str)
    {
        Packet packet = Packet.erstellePacket(str);
        
        if(packet == null)
            return false;
        packet.eventAufrufen(netz);
        return true;
    }

    protected boolean sendeLogin(String _name)
    {
        letzterLoginFehlgeschlagen = false;
        name = _name;
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
            try{this.sleep(1000);}catch(Exception ex){}
            i++;
            if(i > 10)
                return false;
        }
        return true;
    }
    
    protected boolean sendeKarteLegen(Karte karte) {
        return packetSenden(new Packet13KarteLegen(karte));
    }

    protected boolean sendeKarteZiehen() {
        return packetSenden(new Packet14KarteZiehen());
    }
    
    protected boolean sendeMoepButton()
    {
        return packetSenden(new Packet05MoepButton(false));
    }
    
    protected boolean sendeFarbeWuenschenAntwort(int farbe)
    {
        return packetSenden(new Packet06FarbeWuenschen(farbe));
    }
    
    private boolean packetSenden(Packet packet)
    {
        try
        {
            writer.push(packet.gibData());
            synchronized(writer)
            {
                writer.notify();
            }
            return true;
        }
        catch(Exception ex){return false;}
    }
    
    protected void verbindungSchliessen()
    {
        angemeldetSetzen(false);
        reader.interrupt();
        writer.interrupt();
    }
    
    protected void angemeldetSetzen(boolean wert)
    {
        if(wert == false && angemeldet == false)
        {
            letzterLoginFehlgeschlagen = true;
            netz.fehlerEvent("Anmeldung vom Server abgewiesen :(");
        }
        angemeldet = wert;
    }

    protected void verbindungVerlorenEvent() {
        netz.verbindungVerlorenEvent();
    }


}
