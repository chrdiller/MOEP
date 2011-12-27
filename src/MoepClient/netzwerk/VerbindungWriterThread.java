
package moepclient.netzwerk;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wird pro Verbindung erzeugt
 * Arbeitet eine Liste von zu sendenden Strings ab
 * @author Christian Diller

 */
public class VerbindungWriterThread extends Thread{

    private Socket clientSocket;
    private PrintWriter output;
    
    private ArrayList<String> outputListe = new ArrayList<String>();
    
    private static final Logger logger = Logger.getLogger("MoepClientNetz");
    
    public VerbindungWriterThread(Socket _clientSocket) {
        
        clientSocket = _clientSocket;
        try
        {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        }
        catch(Exception ex)
        {
            logger.log(Level.SEVERE, "Fehler beim Initialisieren von VerbindungWriterThread");
        }
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            synchronized(this)
            {
                try
                {
                    this.wait();
                }
                catch(InterruptedException ex)
                {
                    output.close();
                    try{
                    clientSocket.close();}catch(Exception exep){}
                }
            }
            
            for (int i = 0; i < outputListe.size(); i++)
            {
                output.println(outputListe.get(i));
                outputListe.remove(i);
            }
        }
    }
    
    public void push(String data)
    {
        outputListe.add(data);
    }
    
}
