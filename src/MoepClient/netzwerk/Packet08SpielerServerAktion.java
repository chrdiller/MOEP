
package MoepClient.netzwerk;

/**
 * Beschreibt das Packet, mit dem der Server dem Client mitteilt, dass sich ein Spieler angemeldet/abgemeldet hat
 * Der Client aktualisiert daraufhin seine Spielerliste
 * Zudem wird der Spieler uebermittelt, der aktuell am Zug ist
 * Client <- Server
 * @author Christian Diller

 */
public class Packet08SpielerServerAktion extends Packet{
    
    private String spielername;
    private int art; //0: Login; 1: Logout
    
    public Packet08SpielerServerAktion(String _spielername, int _art)
    {
        spielername = _spielername;
        art = _art;
    }
    
    @Override
    public String gibData()
    {
        return "08" + seperator + spielername + seperator + art;
    }
    
    @Override
    public void clientEventAufrufen(Netz netz)
    {
        if(art == 0)
        {
            netz.spielerLoginEvent(spielername);
        }
        else if(art == 1)
        {
            netz.spielerLogoutEvent(spielername);
        }
        else if(art == 2)
        {
            netz.spielerAmZugEvent(spielername);
        }
    }    
}
