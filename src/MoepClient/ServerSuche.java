/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MoepClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author Christian
 */
public class ServerSuche extends Thread {

    public ServerSuche() {
    }

    @Override
    public void run() {
        while (true) {
            String servername;
            DatagramSocket udpSocket = null;
            try {
                udpSocket = new DatagramSocket(111111);
                udpSocket.setBroadcast(true);
                byte[] buffer = new String("Ist da jemand ?").getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.0.255"), 111111);
                System.out.println("Sende Nachricht.");
                udpSocket.send(packet);

                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.setSoTimeout(5000);
                udpSocket.receive(packet);

                System.out.print("Antwort von " + packet.getAddress().getHostAddress() + ":");
                System.out.println(new String(packet.getData(), 0, packet.getLength()));
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
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
