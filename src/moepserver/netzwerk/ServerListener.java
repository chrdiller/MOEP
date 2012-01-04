
package moepserver.netzwerk;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import moepserver.MoepLogger;
import moepserver.Server;
import moepserver.SpielerRemote;

/**
 * Wartet auf neue Verbindungen
 * @author Christian Diller

 */
public class ServerListener extends Thread
{
    private ServerSocket serverSocket;
    private Server server; //Netz muss referenziert werden, um Logins behandeln zu können
    private int listenPort;
    
    private static final MoepLogger log = new MoepLogger();

    public ServerListener(Server _server, int _port)
    {
        listenPort = _port;
        if(listenPort < 0)
            listenPort = 11111;
        server = _server;
        this.setName("ServerListenerThread");
    }

    @Override
    public void run()
    {
        Socket clientSocket = null;

        try             
        {                       
            serverSocket = new ServerSocket(listenPort);
        } 
        catch (Exception ex) 
        {
            log.log(Level.SEVERE, "Fehler beim Starten des Servers auf Port " + listenPort);
            return;
        }

        while(true)
        {
            try 
            {
                clientSocket = serverSocket.accept();
            } 
            catch (Exception ex) 
            {
                log.log(Level.WARNING, "Akzeptieren einer neuen Verbindung fehlgeschlagen");
            }

            final Verbindung verbindung = new Verbindung(new VerbindungReader(clientSocket), new VerbindungWriter(clientSocket)); 
            new Thread(){public void run(){warteAufLogin(verbindung);}}.start();

            clientSocket = null;
        }
    }
    
    private void warteAufLogin(final Verbindung verbindung)
    {
        while(!verbindung.istAktiv)
        {
            //Warten...
            try{sleep(500);}catch(Exception ex){}
        }
        server.spielerHinzufuegen(new SpielerRemote(verbindung, verbindung.loginName, verbindung.gibIP()), -1);
    }
}
