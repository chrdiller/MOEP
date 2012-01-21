package moepserver;

import Moep.Karte;
import Moep.Statusmeldung;
import MoepClient.netzwerk.Verbindung;
import java.util.ArrayList;

/**
 * Beschreibt einen Lokal-Spieler, also einen Spieler,
 * der in dem Client spielt, über den der Server läuft
 * @author Christian Diller
 */
public class SpielerLokal extends Spieler
{

    private Verbindung clientVerbindung;

    public SpielerLokal(Verbindung _clientVerbindung, String _spielername, String _loginIP)
    {
        clientVerbindung = _clientVerbindung;
        loginIP = _loginIP;
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
        for (int j = 0; j < kartenanzahl; j++) {
            if ((hand.get(j).gibNummer() == karte.gibNummer()) && (hand.get(j).gibFarbe() == karte.gibFarbe())) {
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
        for (int i = 0; i < kartenanzahl; i++) {
            if ((hand.get(i).gibNummer() == gesucht.gibNummer()) && (hand.get(i).gibFarbe() == gesucht.gibFarbe())) {
                return true;
            }

        }
        return false;
    }

    @Override
    public void fehlerEvent(String beschreibung)
    {
        Statusmeldung.fehlerAnzeigen(beschreibung);
    }

    @Override
    public void verbindungVerlorenEvent()
    {
        Statusmeldung.warnungAnzeigen("Verbindung zu Spieler " + spielername + " verloren");
        server.spielerEntfernen(this);
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
        clientVerbindung.handkarteEmpfangenEvent(karte);
    }

    @Override
    public void neueAblagekarte(Karte k)
    {
        clientVerbindung.ablagestapelkarteEmpfangenEvent(k);
    }

    @Override
    public void amZug(boolean wert)
    {
        clientVerbindung.amZugEvent(wert);
    }

    @Override
    public void ungueltigerZug(int art)
    {
        clientVerbindung.zugLegalEvent(false, art);
    }

    @Override
    public void gueltigerZug()
    {
        clientVerbindung.zugLegalEvent(true, 0);
    }

    @Override
    public void loginAblehnen()
    {
        Statusmeldung.fehlerAnzeigen("Vom Server nicht akzeptiert!");
    }

    @Override
    public void textSenden(String t)
    {
        clientVerbindung.textEmpfangenEvent(t);
    }

    @Override
    public int farbeFragen()
    {
        new Thread()
        {

            public void run()
            {
                clientVerbindung.farbeWuenschenEvent();
            }
        }.start();
        while (clientVerbindung.farbeWuenschenInt == 4) {
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        int farbe = clientVerbindung.farbeWuenschenInt;
        clientVerbindung.farbeWuenschenInt = 4;
        return farbe;
    }

    @Override
    public void loginAkzeptieren()
    {
        //Nichts
    }

    @Override
    public void spielerServerAktion(String sn, int wert, int kartenzahl, int position)
    {
        if (wert == 0) {
            clientVerbindung.spielerLoginEvent(sn, kartenzahl, position);
        } else if (wert == 1) {
            clientVerbindung.spielerLogoutEvent(sn);
        } else if (wert == 2) {
            clientVerbindung.spielerAmZugEvent(sn);
        } else if (wert == 3) {
            clientVerbindung.spielerKartenzahlUpdate(sn, kartenzahl);
        }
    }

    @Override
    public void spielEnde(boolean gewonnen)
    {
        hand = new ArrayList<Karte>();
        this.kartenanzahl = this.gibKartenanzahl();
        clientVerbindung.spielEnde(gewonnen);
    }

    @Override
    public String gibIP()
    {
        return loginIP;
    }

    @Override
    public void warteAufMoep()
    {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void kick(String grund)
    {
        clientVerbindung.kickEvent(grund);
    }
}
