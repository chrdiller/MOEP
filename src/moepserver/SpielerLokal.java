
package moepserver;

import Moep.Karte;
import MoepClient.netzwerk.Netz;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Beschreibt einen Lokal-Spieler, also einen Spieler,
 * der in dem Client spielt, über den der Server läuft
 * @author Christian Diller
 */

public class SpielerLokal extends Spieler
{
    private Netz clientNetz;
    
    public SpielerLokal(Netz _clientNetz, String _spielername, String _loginIP)
    {
        clientNetz = _clientNetz;
        loginIP = _loginIP;
        spielername = _spielername;
        hand = new ArrayList<Karte>();
        clientNetz.spielerKartenzahlUpdate(spielername, gibKartenanzahl());
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
    public void fehlerEvent(String beschreibung) {
        log.log(Level.SEVERE, "Fehler: " + beschreibung);
    }

    @Override
    public void verbindungVerlorenEvent() {
        log.log(Level.INFO, "Verbindung zu Spieler " + spielername + " verloren");
        server.spielerEntfernen(this);
    }

    @Override
    public void karteLegenEvent(Karte karte) {
        server.spielerzugEvent(karte);
    }

    @Override
    public void karteZiehenEvent() {
        server.karteZiehenEvent();
    }

    @Override
    public void moepButtonEvent() {
        server.moep(this);
    }

    @Override
    public void neueHandkarte(Karte karte) {
        clientNetz.handkarteEmpfangenEvent(karte);
    }

    @Override
    public void neueAblagekarte(Karte k) {
        clientNetz.ablagestapelkarteEmpfangenEvent(k);
    }

    @Override
    public void amZug(boolean wert) {
        clientNetz.amZugEvent(wert);        
        if(wert)
        {
            server.broadcast(spielername + " ist am Zug");     
            log.log(Level.INFO, "Spieler " + spielername + " ist am Zug");
        }
    }

    @Override
    public void ungueltigerZug(int art) {
        clientNetz.zugLegalEvent(false, art);
    }
    
    @Override
    public void gueltigerZug()
    {
        clientNetz.zugLegalEvent(true, 0);
    }

    @Override
    public void loginAblehnen() {
        clientNetz.fehlerEvent("Vom Server nicht akzeptiert!");
    }

    @Override
    public void textSenden(String t) {
       clientNetz.textEmpfangenEvent(t);
    }

    @Override
    public int farbeFragen() {
        new Thread(){public void run(){clientNetz.farbeWuenschenEvent();}}.start();
        while(clientNetz.farbeWuenschenInt == 4){try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException ex) {}}
        int farbe = clientNetz.farbeWuenschenInt;
        clientNetz.farbeWuenschenInt = 0;
        return farbe;
    }
    
    @Override
    public void loginAkzeptieren() {
        //Nichts
    }
    
    @Override
    public void spielerServerAktion(String sn, int wert, int kartenzahl)
    {
        if(wert == 0)
            clientNetz.spielerLoginEvent(sn);
        else if(wert == 1)
            clientNetz.spielerLogoutEvent(sn);
        else if(wert == 2)
            clientNetz.spielerAmZugEvent(sn);
        else if(wert == 3)
            clientNetz.spielerKartenzahlUpdate(spielername, gibKartenanzahl());
    }
    
    @Override
    public void spielEnde(boolean gewonnen)
    {
        hand = new ArrayList<Karte>();
        this.kartenanzahl = this.gibKartenanzahl();
        clientNetz.spielEnde(gewonnen);
    }

    @Override
    public String gibIP()
    {
        return loginIP;
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
        clientNetz.kickEvent(grund);
    }
}

