package Moep;

import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Stellt eine einzelne Karte dar
 * @author Markus Klar, Michael Stoffels, Christian Diller 
 */

public class Karte implements Comparable<Karte>
{
    private int farbe;
    private int nummer;
    
    public Karte(int farbeN, int nummerN)
    {
        farbe = farbeN;
        nummer = nummerN;
    }
    
    public Karte(String dataString)
    {
        String dataStringArray[] = dataString.split("\\|");
        farbe = Integer.valueOf(dataStringArray[0]).intValue();
        nummer = Integer.valueOf(dataStringArray[1]).intValue();
    }
    
    public int gibFarbe()
    {
        return farbe;
    }
    
    public int gibNummer()
    {
        return nummer;
    }
    
    public String gibDaten()
    {
        return farbe+"|"+nummer;
    }
    
    public ImageIcon gibBild()
    {
        ImageIcon bild = new ImageIcon(this.getClass().getResource("../MoepClient/grafik/kartenSet.png"));
        bild.setImage(new JPanel().createImage(new FilteredImageSource(bild.getImage().getSource(), new CropImageFilter((nummer - (farbe == 4 ? 13 : 0))* 166, farbe * 250, 166, 250))));
        return bild;
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
