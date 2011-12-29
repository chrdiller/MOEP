
package moepserver;

import Moep.Karte;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

/**
 * Beschreibt einen KI-Spieler, also einen Spieler,
 * der vom Computer gesteuert wird
 * @author Christian Diller
 */

public class SpielerKI extends Spieler
{
    public SpielerKI(String _spielername)
    {
        spielername = _spielername;
        hand = new ArrayList<Karte>();
    }

    @Override
    public void handReset()
    {
        hand = new ArrayList();
        kartenanzahl = 0;
    }

    @Override
    public void karteHinzufuegen(Karte neu)
    {
        hand.add(neu);
        kartenanzahl++;
    }
    
    @Override
    public void karteEntfernen(Karte karte)
    {
        for(int j = 0; j < kartenanzahl; j++)
        {
            if((hand.get(j).gibNummer()==karte.gibNummer())&&(hand.get(j).gibFarbe()==karte.gibFarbe()))
            {
                hand.remove(j);
                kartenanzahl--;  
                return;
            }
        }    
    }
    
    @Override
    public int gibKartenanzahl()
    {
        return kartenanzahl;
    }
    
    @Override
    public ArrayList<Karte> gibHand()
    {
        return hand;
    }

    @Override
    public boolean istInHand(Karte gesucht)
    {
        //return hand.contains(searched);
        for(int i=0;i<kartenanzahl;i++)
        {
            if((hand.get(i).gibNummer()==gesucht.gibNummer())&&(hand.get(i).gibFarbe()==gesucht.gibFarbe()))
            {
                return true;
            }
            
        }
        return false;
    }

    @Override
    public void fehlerEvent(String beschreibung) 
    {
        log.log(Level.SEVERE, "Fehler: " + beschreibung);
    }

    @Override
    public void verbindungVerlorenEvent() 
    {
        //Nichts
    }

    @Override
    public void karteLegenEvent(Karte karte) 
    {
        server.spielerzugEvent(karte);
    }

    @Override
    public void karteZiehenEvent() 
    {
        server.karteZiehenEvent();
    }

    @Override
    public void moepButtonEvent() 
    {
        server.moep(this);
    }

    @Override
    public void neueHandkarte(Karte karte) 
    {
        //Nichts
    }

    @Override
    public void neueAblagekarte(Karte k) 
    {
        //Nichts
    }

    @Override
    public void amZug(boolean wert) 
    {   
        if(wert)
        {
            server.broadcast(spielername + " ist am Zug");     
            log.log(Level.INFO, "Spieler " + spielername + " ist am Zug");
            
            //KI-Code
            final ArrayList<Karte> legbar = new ArrayList<Karte>();
            for(Karte legen : hand)
                if(((server.neueFarbe != 4) && (legen.gibFarbe() == server.neueFarbe)) ||((legen.gibFarbe() == server.gibOffen().gibFarbe()) || legen.gibNummer() == server.gibOffen().gibNummer()) || (legen.gibFarbe() == 4))
                    legbar.add(legen);
            if(legbar.isEmpty())
                new Thread(){public void run(){karteZiehenEvent();}}.start();
            else if(legbar.size() == 1)
                new Thread(){public void run(){karteLegenEvent(legbar.get(0));}}.start();
            else if(legbar.size() > 1)
            {
                try
                {
                Thread.currentThread().sleep(1500 + new Random().nextInt(1501)); 
                }catch (InterruptedException ex) {}   
                new Thread(){public void run(){karteLegenEvent(legbar.get(new Random().nextInt(legbar.size())));}}.start();
            }
            
            if(hand.size() == 1)
                new Thread(){public void run(){moepButtonEvent();}}.start();
        }      
    }

    @Override
    public void ungueltigerZug(int art) 
    {
        //Nichts
    }
    
    @Override
    public void gueltigerZug()
    {
        //Nichts
    }

    @Override
    public void loginAblehnen() 
    {
        //Nichts
    }

    @Override
    public void textSenden(String t) 
    {
       //Nichts
    }

    @Override
    public int farbeFragen()
    {
        Random r = new Random();
        return r.nextInt(4) + 1;
    }
    
    @Override
    public void loginAkzeptieren()
    {
        //Nichts
    }
    
    @Override
    public void spielerServerAktion(String sn, int wert)
    {
        //Nichts
    }
    
    @Override
    public void spielEnde(boolean gewonnen)
    {
        hand = new ArrayList<Karte>();
        this.kartenanzahl = this.gibKartenanzahl();
    }

    @Override
    public String gibIP()
    {
        return " - ";
    }

    @Override
    public void warteAufMoep() {      
        try
        {
            Thread.currentThread().sleep(2000); 
        }        
        catch (InterruptedException ex) {}     
    }
    
    @Override
    public void kick(String grund)
    {
        //Nichts
    }
    
    
}
