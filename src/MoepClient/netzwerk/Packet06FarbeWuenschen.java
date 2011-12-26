
package moepclient.netzwerk;

/**
 * Beschreibt das Packet, mit dem der Server dem Client mitteilt, dass dieser dem Server
 * eine gewünschte Farbe schicken soll; der Client schickt die Farbe an den Server
 * @author Christian Diller
 * @version BETA 1.1
 */
public class Packet06FarbeWuenschen extends Packet{
    
    private int farbe;
    
    public Packet06FarbeWuenschen(int _farbe)
    {
        farbe = _farbe;
    }
    
    @Override
    public String gibData()
    {
        return "06" + seperator + farbe;
    }
    
    @Override
    public void eventAufrufen(Netz netz)
    {
        netz.farbeWuenschenEvent();
    }
}
