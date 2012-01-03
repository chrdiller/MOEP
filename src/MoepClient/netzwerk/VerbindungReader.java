
package MoepClient.netzwerk;

import Moep.Statusmeldung;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Wird pro Verbindung erzeugt
 * Wartet auf eingehende Packets und setzt diese auf eine Liste, die dann von der Verbindung abgearbeitet wird
 * @author Christian Diller
 */

public class VerbindungReader extends Thread
{
    private Socket clientSocket;
    private BufferedReader input;
    protected Verbindung verbindung;
    
    public VerbindungReader(Socket _clientSocket) {
        
        clientSocket = _clientSocket;
        try
        {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(Exception ex)
        {
            Statusmeldung.fehlerAnzeigen("Initialisieren von VerbindungReader fehlgeschlagen");
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            while(true) {
                final String inputLine = input.readLine();
                if(inputLine == null)
                    break;
                new Thread(){public void run(){verbindung.neuesPacket(inputLine);}};
            }
            verbindung.verbindungVerlorenEvent(); //Hier kommt die Ausführung nur hin, wenn der Server die Verbindung geschlossen hat

            input.close();
            clientSocket.close();
        }
        catch(Exception ex)
        {
            verbindung.verbindungVerlorenEvent();
        }
    }
}
