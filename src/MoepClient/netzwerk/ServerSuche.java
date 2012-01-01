
package MoepClient.netzwerk;

import MoepClient.Interface;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Christian Diller
 */

public class ServerSuche extends Thread 
{
    Interface i;
            
    public ServerSuche(Interface _i) 
    {
        i = _i;
    }

    @Override
    public void run() {
        String serverName = "", serverAdresse = "";

        DatagramSocket udpSocket = null;
        try {
            int zaehler = 0;
            while (udpSocket == null)
            {
                try{udpSocket = new DatagramSocket(11112 + zaehler);}catch(Exception ex){ }
                zaehler++;
            }
            zaehler = 0;
            udpSocket.setBroadcast(true);
            byte[] buffer = new String("Suche MoepServer").getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 10001);
            new Thread(){public void run(){i.serverErstellen(true);}};
            System.out.println("Sende Broadcast");
            udpSocket.send(packet);

            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);

            System.out.print("Antwort von " + packet.getAddress().getHostAddress() + ":");
            System.out.println(new String(packet.getData(), 0, packet.getLength()));

            serverName = new String(packet.getData(), 0, packet.getLength());
            serverAdresse = packet.getAddress().getHostAddress();
            udpSocket.close(); 
        } catch (NullPointerException npEx) { i.serverErstellen(false); return; }
        catch(IOException ioEx){udpSocket.close(); }
        
        i.serverGefunden(serverName, serverAdresse);
    }

    public boolean istEinzigerServer() {
        try{ new DatagramSocket(10001).close(); return true; }catch(Exception ex){ return false; }
    }
}
