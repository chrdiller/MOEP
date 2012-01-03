package moepserver.netzwerk;

/**
 * Beschreibt das Packet, mit dem der Server dem Client mitteilt, dass sich ein Spieler angemeldet/abgemeldet hat
 * Der Client aktualisiert daraufhin seine Spielerliste
 * Zudem wird der Spieler uebermittelt, der aktuell am Zug ist sowie die Spielkartenzahl jedes Spielers
 * Client <- Server
 * @author Christian Diller
 */

public class Packet08SpielerServerAktion extends Packet
{    
    private String spielername;
    private int art; //0: Login; 1: Logout; 2: AmZug; 3: KartenzahlUpdate
    private int kartenzahl;
    
    public Packet08SpielerServerAktion(String _spielername, int _art, int _kartenzahl)
    {
        spielername = _spielername;
        art = _art;
        kartenzahl = _kartenzahl;
    }
    
    @Override
    public String gibData()
    {
        return "08" + seperator + spielername + seperator + art + seperator + kartenzahl;
    }
    
    @Override
    public void serverEventAufrufen(Verbindung verbindung)
    {
        if(art == 0)
        {
            verbindung.spielerLoginEvent(spielername);
        }
        else if(art == 1)
        {
            verbindung.spielerLogoutEvent(spielername);
        }
        else if(art == 2)
        {
            verbindung.spielerAmZugEvent(spielername);
        }
        else if(art == 3)
            verbindung.spielerKartenzahlUpdate(spielername, kartenzahl);
    }    
}
