package moepserver.netzwerk;

import Moep.Statusmeldung;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Wird pro Verbindung erzeugt
 * Arbeitet eine Liste von zu sendenden Strings ab
 * @author Christian Diller
 */

public class VerbindungWriter
{
    private Socket clientSocket;
    private PrintWriter output;
    
    public VerbindungWriter(Socket _clientSocket)
    {
        clientSocket = _clientSocket;
        try
        {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        }
        catch(Exception ex)
        {
            Statusmeldung.fehlerAnzeigen("Initialisieren von VerbindungWriter fehlgeschlagen");
        }
    }
    
    public void senden(String data)
    {
        output.println(data);
    }
}
