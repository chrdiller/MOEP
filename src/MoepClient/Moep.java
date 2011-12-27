
package MoepClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Die Spielverwaltung auf Clientseite, vor allem Basis für die GUI
 * @author Markus Klar & Christian Diller

 */
public class Moep
{
    private List<Karte> hand;
    private ArrayList<String> spieler;
    private String spielerAmZug;
    private ArrayList<String> nachrichten;
    private int zuLegenIndex;
    
    public Moep()
    {
        hand = Collections.synchronizedList(new ArrayList<Karte>());
        spieler = new ArrayList<String>();
        nachrichten = new ArrayList<String>();
    }
    
    public Moep(ArrayList<Karte> h)
    {
        hand = Collections.synchronizedList(h);;
        spieler = new ArrayList<String>();
        nachrichten = new ArrayList<String>();
        Collections.sort(hand);
    }
    
    public void ziehen(Karte karte)
    {
        hand.add(karte);
        Collections.sort(hand);
    }
    
    public void legen()
    {
        hand.remove(zuLegenIndex);
        Collections.sort(hand);   
    }
        
    public void zuLegen(int index)
    {
        zuLegenIndex = index;
    }
    
    public List<Karte> gibHand()
    {
        return hand;
    }
    
    public void setzeHand(ArrayList<Karte> h)
    {
        hand = h;
    }
    
    public Karte gibKarteAt (int indexKarte){
        return hand.get(indexKarte);
    }
    
    public void mitspielerLogin(String name)
    {
        spieler.add(name);
    }
    
    public void mitspielerLogout(String name)
    {
        spieler.remove(name);
    }
    
    public String gibSpielerliste()
    {
        if(spieler.isEmpty())
            return "";
        String ausgabe = "<html><body><center><h1>Spielerliste</h1><br/><h2>";
        for(String s : spieler)
        {
            if(s.equals(spielerAmZug))
                ausgabe += ("<i>" + s  + "</i>" + "<br/><br/>");
            else
                ausgabe += (s + "<br/><br/>");
        }
        ausgabe = ausgabe + "<h2></center></body></html>";
        return umlautFix(ausgabe);
    }
    
    public void addNachricht(String status)
    {
        nachrichten.add(status);
        if(nachrichten.size() > 5)
            nachrichten.remove(0);
    }
    
    public String gibStatus()
    {
        if(nachrichten.isEmpty())
            return "";
        String ausgabe = "<html><body>";
        for(String n : nachrichten)
        {
            ausgabe += (n + "<br/>");
        }
        ausgabe = ausgabe + "</body></html>";
        return umlautFix(ausgabe);
    }
    
    private String umlautFix(String input)
    {
        input.replaceAll("ä", "&auml;");
        input.replaceAll("ö", "&ouml;");
        input.replaceAll("ü", "&uuml;");
        input.replaceAll("ß", "&szlig;");
        return input;
    }

    public void statusReset() {
        nachrichten.clear();
        spieler.clear();
    }

    public void mitspielerAmZug(String spielername) {
        spielerAmZug = spielername;
    }
}
