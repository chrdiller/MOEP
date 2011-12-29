package MoepClient.netzwerk;

import Moep.Karte;
/**
 * Beschreibt das Packet, mit dem der Server dem Client eine Ablagestapelkarte übermittelt
 * @author Christian Diller

 */
public class Packet12Ablagestapelkarte extends Packet{
    
    private Karte karte;
    
    public Packet12Ablagestapelkarte(Karte _karte)
    {
        karte = _karte;
    }
    
    @Override
    public String gibData()
    {
        return "12" + seperator + karte.gibDaten();
    }
    
    @Override
    public void clientEventAufrufen(Netz netz)
    {
        netz.ablagestapelkarteEmpfangenEvent(karte);
    }
}
