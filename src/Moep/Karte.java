package Moep;

import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Stellt eine einzelne (Client-)Karte dar
 * @author Markus Klar, Michael Stoffels, Christian Diller 
 * @version BETA 1.1 GIT
 */
public class Karte implements Comparable<Karte>
{
    private int farbe;
    private int nummer;
    private ImageIcon bild;
    
    public Karte(int farbeN, int nummerN)
    {
        farbe = farbeN;
        nummer = nummerN;
        holeBild();
    }
    
    public Karte(String dataString)
    {
        String dataStringArray[] = dataString.split("\\|");
        farbe = Integer.valueOf(dataStringArray[0]).intValue();
        nummer = Integer.valueOf(dataStringArray[1]).intValue();
        holeBild();
    }
    
    public int gibFarbe()
    {
        return farbe;
    }
    
    public int gibNummer()
    {
        return nummer;
    }
    
    public ImageIcon gibBild()
    {
        return bild;
    }
    
    public String gibDaten()
    {
        return farbe+"|"+nummer;
    }
    
    private void holeBild()
    {
        bild = new ImageIcon(this.getClass().getResource("../MoepClient/grafik/kartenSet.png"));
        bild.setImage(new JPanel().createImage(new FilteredImageSource(bild.getImage().getSource(), new CropImageFilter((nummer - (farbe == 4 ? 13 : 0))* 166, farbe * 250, 166, 250))));
    }
    
    @Override
    public int compareTo(Karte k)
    {        
        if(farbe>k.gibFarbe())
        {
            return -1;
        }
        else if(farbe<k.gibFarbe())
        {
            return 1;
        }
        else if(farbe==k.gibFarbe() && nummer>k.gibNummer())
        {
            return -1;
        }
        else if(farbe==k.gibFarbe() && nummer<k.gibNummer())
        {
            return 1;
        }
        else if(farbe==k.gibFarbe() && nummer==k.gibNummer())
        {
            return 0;
        }
        return -2;
    }
}
