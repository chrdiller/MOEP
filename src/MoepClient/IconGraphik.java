
package MoepClient;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Beschreibt die Schnittstelle zwischen gespeichertem und gezeichneten Bild
 * @author Philipp Herrle
 * @version BETA 1.1
 */
public class IconGraphik extends JLabel{
    
    private ImageIcon icon;
    
    public IconGraphik (ImageIcon iconH,Dimension pref, MouseAdapter listener){
        super();
        
        if (pref==null)pref = new Dimension(200, 300);

        this.setPreferredSize(pref);
        
        this.addMouseListener(listener);
        
        if (iconH == null){
            icon = this.getDefaultIcon();
        }
        else{
            icon = iconH;
        }
        
    }
    
    public void changeIcon(ImageIcon iconH){
        if(iconH != null) icon = iconH;
        
        this.repaint();
    }

    
    public final ImageIcon getDefaultIcon(){
        ImageIcon icon = new ImageIcon(this.getClass().getResource("grafik/kartenSet.png"));
        icon.setImage(new JPanel().createImage(new FilteredImageSource(icon.getImage().getSource(), new CropImageFilter(12 * 166, 4 * 250, 166, 250))));
        return icon;
    }
    
    
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;

        
        g2.drawImage(this.getImageIcon().getImage(), 0,0,this.getWidth(),this.getHeight(), null);
    }

    public ImageIcon getImageIcon() {
        return icon;
    }
    
}
