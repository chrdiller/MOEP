package Moep;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Verwaltet alle Ressourcen, die von MOEP verwendet werden,
 * sodass diese nicht bei jeder Verwendung erst in den Speicher geladen werden müssen.
 * @author Christian Diller
 */
public class Ressourcen
{
    public static final Image mIconKlein = new ImageIcon(MoepClient.GUI.GUI.class.getResource("grafik/mIconKlein.png")).getImage();
    public static final ImageIcon kartenSet = new ImageIcon(MoepClient.GUI.GUI.class.getResource("grafik/kartenSet.png"));
    public static final ImageIcon moepButton = new ImageIcon(MoepClient.GUI.GUI.class.getResource("grafik/moep-button.png"));
    
    public static final AudioClip applauseClip = Applet.newAudioClip(MoepClient.Interface.class.getResource("sound/applause.au"));
    public static final AudioClip beepClip = Applet.newAudioClip(MoepClient.Interface.class.getResource("sound/beep.au"));
    public static final AudioClip clickClip = Applet.newAudioClip(MoepClient.Interface.class.getResource("sound/click.au"));
    public static final AudioClip moepClip = Applet.newAudioClip(MoepClient.Interface.class.getResource("sound/moep.au"));
    public static final AudioClip oohClip = Applet.newAudioClip(MoepClient.Interface.class.getResource("sound/ooh.au"));
    
    public static AudioClip gibClip(String name)
    {
        AudioClip ausgabe = null;
        try {
            ausgabe = (AudioClip)Ressourcen.class.getDeclaredField(name + "Clip").get(ausgabe);
        } catch (Exception ex) { }
        return ausgabe;
    }
}
