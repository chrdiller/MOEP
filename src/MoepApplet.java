
import MoepClient.Interface;
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
        Interface i = new Interface();   
    }
        
    @Override
    public void stop() {

    }
            
    @Override
    public void destroy() {

    }
}
