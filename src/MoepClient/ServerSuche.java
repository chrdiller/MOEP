
package MoepClient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Christian Diller
 */
public class ServerSuche extends Thread {

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
            udpSocket = new DatagramSocket(111111);
            udpSocket.setBroadcast(true);
            byte[] buffer = new String("Suche MoepServer").getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.0.255"), 111111);
            System.out.println("Sende Broadcast");
            udpSocket.send(packet);

            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);

            System.out.print("Antwort von " + packet.getAddress().getHostAddress() + ":");
            System.out.println(new String(packet.getData(), 0, packet.getLength()));

            serverName = new String(packet.getData(), 0, packet.getLength());
            serverAdresse = packet.getAddress().getHostAddress();

        } catch (Exception ex) { }
        finally {
            udpSocket.close(); 
        }
        
        i.serverGefunden(serverName, serverAdresse);
    }
}
