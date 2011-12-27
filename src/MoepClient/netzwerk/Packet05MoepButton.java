package moepclient.netzwerk;

/**
 * Beschreibt das Packet, mit dem der Client dem Server mitteilt, dass der MoepButton gedrückt wurde
 * Außerdem wird vom Server zurückgeschickt, ob der Button rechtzeitig gedrückt wurde
 * @author Christian Diller

 */
public class Packet05MoepButton extends Packet{
    
    private boolean rechtzeitig;
    
    public Packet05MoepButton(boolean _rechtzeitig)
    {
        rechtzeitig = _rechtzeitig;
    }
    
    @Override
    public String gibData()
    {
        return "05" + seperator + (rechtzeitig ? "Y":"N");
    }
    
    @Override
    public void eventAufrufen(Netz netz)
    {
        netz.moepButtonAntwortEvent(rechtzeitig);
    }
}
