package Moep;

import MoepClient.GUI.StatusBar;

/**
 * Diese Klasse kümmert sich um Benachrichtgungen, indem sie den Text der
 * Statusbar ändert 
 * @author Christian Diller
 */
public class Statusmeldung
{

    public static StatusBar statusbar;

    public static void infoAnzeigen(String meldung)
    {
        if (statusbar != null) {
            statusbar.setzeText(meldung);
        }
    }

    public static void warnungAnzeigen(String meldung)
    {
        if (statusbar != null) {
            statusbar.setzeText("Warnung: " + meldung);
        }
    }

    public static void fehlerAnzeigen(String meldung)
    {
        if (statusbar != null) {
            statusbar.setzeText("FEHLER: " + meldung);
        }
    }
}
