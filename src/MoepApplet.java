
import MoepClient.Interface;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.AllPermission;
import javax.swing.JApplet;

/**
 * Bietet die Möglichkeit, MOEP als Applet zu implementieren
 * @author Christian Diller
 */

public class MoepApplet extends JApplet {

    @Override
    public void init() {
     
    }
    @Override
    public void start() {
        AllPermission perm = new AllPermission();
        try{ AccessController.checkPermission(perm); }
        catch (AccessControlException ex) {return; }
        
        //Startet MOEP
        Interface i = new Interface();   
    }
        
    @Override
    public void stop() {

    }
            
    @Override
    public void destroy() {

    }
}
