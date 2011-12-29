
package moepserver.netzwerk;

/**
 * Beschreibt das Packet, mit dem der Server dem Client mitteilt, ob er am Zug ist
 * @author Christian Diller

 */
public class Packet03AmZug extends Packet{
    
    private boolean wert;
    private String text;
    
    public Packet03AmZug(boolean _wert)
    {
        wert = _wert;
    }
    
    @Override
    public String gibData()
    {
        return "03" + seperator + (wert ? "Y" : "N");
    }
    
    @Override
    public void serverEventAufrufen(Verbindung verbindung)
    {
        //Kein Serverevent!
    }
}

