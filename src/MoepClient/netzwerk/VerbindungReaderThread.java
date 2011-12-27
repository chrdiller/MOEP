
package moepclient.netzwerk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wird pro Verbindung erzeugt
 * Wartet auf eingehende Packets und setzt diese auf eine Liste, die dann von der Verbindung abgearbeitet wird
 * @author Christian Diller

 */
public class VerbindungReaderThread extends Thread{
    
    private Socket clientSocket;
    private BufferedReader input;
    private List<String> inputListe;
    protected Verbindung verbindung;
    
    private static final Logger logger = Logger.getLogger("MoepClientNetz");
    
    public VerbindungReaderThread(Socket _clientSocket) {
        
        clientSocket = _clientSocket;
        try
        {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(Exception ex)
        {
            logger.log(Level.SEVERE, "Fehler beim Initialisieren von VerbindungReaderThread");
        }
        inputListe = new ArrayList<String>();
    }
    
    @Override
    public void run()
    {
        String inputLine;
        try
        {
            while ((inputLine = input.readLine()) != null) 
            {   
                inputListe.add(inputLine);
                synchronized(verbindung)
                {
                    verbindung.notify();                    
                }

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
    
    protected String pop()
    {
        String ausgabe = inputListe.get(0);
        inputListe.remove(0);
        return ausgabe;
    }
    
    protected boolean istLeer()
    {
        return inputListe.isEmpty();
    }
    
}
