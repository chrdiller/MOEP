package MoepClient.netzwerk;

import MoepClient.Interface;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Hier findet die Suche nach Servern im gesamten Netzwerk
 * per UDP-Broadcast statt
 * @author Christian Diller
 */

public class ServerSuche
{
    Interface iF;
            
    public ServerSuche(Interface _iF) 
    {
        iF = _iF;
    }

    public void suchen() 
    {
        String serverName = "", serverAdresse = "";
        DatagramSocket udpSocket = null;
        
        int zaehler = 0;
        while (udpSocket == null)
        {
            try{udpSocket = new DatagramSocket(11112 + zaehler);}catch(Exception ex){ }
            zaehler++;
        }       
        
        for(int i = 0; i < 5; i++)
        {
            try 
            {
                udpSocket.setBroadcast(true);
                byte[] buffer = new String("MOEP").getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 10001);
                udpSocket.send(packet);

                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.setSoTimeout(100);
                udpSocket.receive(packet);

                serverName = new String(packet.getData(), 0, packet.getLength());
                serverAdresse = packet.getAddress().getHostAddress();
                iF.serverGefunden(serverName, serverAdresse);
            } 
            catch (NullPointerException npEx) { continue; }
            catch(IOException ioEx){ continue; }
        }
    }

    public boolean istEinzigerServer() {
        try { new DatagramSocket(10001).close(); return true; } catch(Exception ex){ return false; }
    }
}
