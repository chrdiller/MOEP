
package moepserver;

import java.util.ArrayList;

/**
 * Beschreibt einen KI-Spieler, also einen Spieler,
 * der vom Computer gesteuert wird
 * @author Christian Diller
 */

public class SpielerKI extends Spieler
{
    public SpielerKI(String _spielername, String _loginIP)
    {

    }

    @Override
    public void handReset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void karteHinzufuegen(Karte neu) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void karteEntfernen(Karte karte) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int gibKartenanzahl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Karte> gibHand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean istInHand(Karte gesucht) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fehlerEvent(String beschreibung) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void verbindungVerlorenEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void karteLegenEvent(Karte karte) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void karteZiehenEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moepButtonEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void karteBekommen(Karte karte) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void neueAblagekarte(Karte k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void amZug(boolean wert) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ungueltigerZug(int art) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void gueltigerZug() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loginAblehnen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void textSenden(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int farbeFragen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loginAkzeptieren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void spielerServerAktion(String sn, int wert) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void spielEnde(boolean gewonnen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String gibIP() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void warteAufMoep() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void kick(String grund) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
