
import MoepClient.Interface;
import MoepClient.UI.MoepWindow;
import MoepClient.UI.MenuPanel;

/**
 * Die Startklasse mit der Main-Methode
 * Hier wird eine neue Interface-Instanz erzeugt
 * @author Philipp Herrle
 * @version BETA 1.1
 */
public class MoepClient
{
    public static void main(String[] args)
    {
        //Interface i = new Interface();
        MoepWindow main = new MoepWindow(1091, 631, "MOEP RELOADED 1.0");
        main.panelEinsetzen(new MenuPanel());
    }
}
