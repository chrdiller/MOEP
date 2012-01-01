package MoepClient;

/**
 * Hier werden alle Mitspieler verwaltet, die über den Spieler-Dialog
 * eingetragen werden
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
        for(int i = 0; i < 4; i++)
            if(spieler[i].equals(""))
                return false;
        if(spieler[0][1].equals(""))
            return false;
        return true;
    }
}
