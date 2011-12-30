package MoepClient.netzwerk;

import MoepClient.netzwerk.Packet03AmZug;
import MoepClient.netzwerk.Packet01Login;
import MoepClient.netzwerk.Packet02Kick;
import MoepClient.netzwerk.Packet06FarbeWuenschen;
import MoepClient.netzwerk.Packet09Spielende;
import MoepClient.netzwerk.Packet12Ablagestapelkarte;
import MoepClient.netzwerk.Packet07Text;
import MoepClient.netzwerk.Packet04ZugLegal;
import MoepClient.netzwerk.Packet05MoepButton;
import MoepClient.netzwerk.Packet11Handkarte;
import MoepClient.netzwerk.Packet08SpielerServerAktion;
import Moep.Karte;
/**
 * Beschreibt die abstrakte Oberklasse der Packetklassen;
 * enthält die statische Methode zum Parsen von Strings in Packes
 * @author Christian Diller

 */
public abstract class Packet {
    
    /**
     * Parst einen String in ein Packet
     * @param data Der String, der geparst werden soll
     * @return Wenn gültiger String: Passendes Packet mit entsprechenden Werten; Wenn ungültiger String: null
     */
    public static Packet erstellePacket(String data)
    {
       String[] dataArray = data.split(seperator);

        if(dataArray[0].equals("01") && dataArray.length == 3)
        {
            return new Packet01Login(dataArray[1], dataArray[2].equals("Y") ? true : false);
        }
        else if(dataArray[0].equals("02") && dataArray.length == 2)
        {
            return new Packet02Kick(dataArray[1]);
        }
        else if(dataArray[0].equals("03") && dataArray.length == 2)
        {
            return new Packet03AmZug(dataArray[1].equals("Y") ? true : false);
        }
        else if(dataArray[0].equals("04") && dataArray.length == 3)
        {
            return new Packet04ZugLegal(dataArray[1].equals("Y") ? true : false, Integer.parseInt(dataArray[2]));
        }
        else if(dataArray[0].equals("05") && dataArray.length == 2)
        {
            return new Packet05MoepButton(dataArray[1].equals("Y") ? true : false);
        }
        else if(dataArray[0].equals("06") && dataArray.length == 2)
        {
            return new Packet06FarbeWuenschen(Integer.parseInt(dataArray[1]));
        }
        else if(dataArray[0].equals("07") && dataArray.length == 2)
        {
            return new Packet07Text(dataArray[1]);
        }
        else if(dataArray[0].equals("08") && dataArray.length == 4)
        {
            return new Packet08SpielerServerAktion(dataArray[1], Integer.parseInt(dataArray[2]), Integer.parseInt(dataArray[3]));
        }
        else if(dataArray[0].equals("09") && dataArray.length == 2)
        {
            return new Packet09Spielende(dataArray[1].equals("Y") ? true : false);
        }
        else if(dataArray[0].equals("11") && dataArray.length == 2)
        {
            return new Packet11Handkarte(new Karte(dataArray[1]));
        }
        else if(dataArray[0].equals("12") && dataArray.length == 2)
        {
            return new Packet12Ablagestapelkarte(new Karte(dataArray[1]));
        }
        else
            return null;
    }
    
    /**
     * Gibt den Datenstring dieses Packets zurück
     * @return Der weiterverwendbare Datenstring (z.B. fürs Senden)
     */
    public abstract String gibData();
    
    /**
     * Ruft packetspezifische Events in netz auf
     * @param netz Das Netzobjekt, in dem die Events aufgerufen werden sollen
     */
    public abstract void clientEventAufrufen(Netz netz);
    
    protected static String seperator = "#";
}
