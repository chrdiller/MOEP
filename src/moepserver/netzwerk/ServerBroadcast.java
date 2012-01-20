
package moepserver.netzwerk;

import Moep.Statusmeldung;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Christian Diller
 */

public class ServerBroadcast extends Thread
{
    private String servername;
    private DatagramSocket udpSocket = null;
    private boolean beendet = false;
    
    public ServerBroadcast(String _servername)
    {
        servername = _servername;
    }
    
    public void run()
    {
        while(true)
        {
            try {
                int i = 0;
                udpSocket = new DatagramSocket(10001);
                udpSocket.setBroadcast(true);
                while (true) {
                    byte[] buffer = new byte[4];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);
                    InetAddress sendeAdresse = packet.getAddress();
                    packet = new DatagramPacket(servername.getBytes(), servername.length(), sendeAdresse, packet.getPort());
                    udpSocket.send(packet);
                }
            } catch (Exception ex) {
                if(!beendet)
                    Statusmeldung.fehlerAnzeigen("ServerBroadcast wurde unterbrochen");
            } finally {
                udpSocket.close();
            }
            break;
        }
    }

    public void beenden()
    {
        beendet = true;
        udpSocket.close();
    }
}
