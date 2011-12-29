
package MoepClient;

import java.util.ArrayList;

/**
 *
 * @author Christian Diller
 */
public class Spielerverwaltung 
{
    String eigenerName;
    ArrayList<String> kiSpieler;
    
    public Spielerverwaltung(String _eigenerName, ArrayList<String> _kiSpieler)
    {
        eigenerName = _eigenerName;
        kiSpieler = _kiSpieler;
    }
    
    public int gibKISpielerAnzahl()
    {
        return kiSpieler.size();
    }
    
    public String[] gibKINamen()
    {
        String[] a = new String[kiSpieler.size()];
        return kiSpieler.toArray(a);
    }
    
    public String gibEigenenNamen()
    {
        return eigenerName;
    }
    
    public boolean istGueltig()
    {
        for(String s : kiSpieler)
            if(s.equals(""))
                return false;
        if(eigenerName.equals(""))
            return false;
        return true;
    }
}
