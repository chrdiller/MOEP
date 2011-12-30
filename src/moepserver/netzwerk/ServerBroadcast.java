
package moepserver.netzwerk;

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
    
    public ServerBroadcast(String _servername)
    {
        servername = _servername;
    }
    
    public void run()
    {
        DatagramSocket udpSocket = null;
        while(true)
        {
            try {
                int i = 0;
                udpSocket = new DatagramSocket(10001);
                udpSocket.setBroadcast(true);
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);
                    InetAddress sendeAdresse = packet.getAddress();
                    System.out.print("Nachricht von " + sendeAdresse.getHostAddress() + ":");
                    System.out.println(new String(packet.getData(), 0, packet.getLength()));
                    System.out.println("Sende Antwort.. ");
                    packet = new DatagramPacket(servername.getBytes(), servername.length(), sendeAdresse, packet.getPort());
                    udpSocket.send(packet);
                    System.out.println("Antwort gesendet!");
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                udpSocket.close();
            }
        }
    }
}
