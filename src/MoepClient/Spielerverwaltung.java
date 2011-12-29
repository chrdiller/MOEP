
package MoepClient;

/**
 *
 * @author Christian Diller
 */
public class Spielerverwaltung 
{
    String[][] spieler;
    
    public Spielerverwaltung(String[][] _spieler)
    {
        spieler = _spieler;
    }
    
    public int gibKISpielerAnzahl()
    {
        int anzahl = 0;
        for(int i = 0; i < spieler.length; i++)
            if(spieler[i][0] == "KI")
                anzahl++;
        return anzahl;
    }
    
    public String[][] gibKINamen()
    {
        String[][] ausgabe = new String[gibKISpielerAnzahl()][2];
        int pos = 0;
        for(int i = 0; i < spieler.length; i++)
            if(spieler[i][0] == "KI"){
                ausgabe[pos][0] = spieler[i][1];
                ausgabe[pos][1] = i+"";
                pos++;
            }
        return ausgabe;
    }
    
    public String[][] gibSpielerListe()
    {
        return spieler;
    }
    
    public String gibEigenenNamen()
    {
        return spieler[0][1];
    }
    
    public boolean istGueltig()
    {
        for(String s : spieler[1])
            if(s.equals(""))
                return false;
        if(spieler[0][1].equals(""))
            return false;
        return true;
    }
}
