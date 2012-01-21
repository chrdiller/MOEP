package MoepClient.GUI;

import Moep.Ressourcen;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Beschreibt die Schnittstelle zwischen gespeichertem und gezeichneten Bild
 * @author Philipp Herrle
 */
public class KarteGrafik extends JLabel
{

    private ImageIcon icon;

    public KarteGrafik(ImageIcon iconH, Dimension pref, MouseAdapter listener)
    {
        super();

        if (pref == null) {
            pref = new Dimension(200, 300);
        }

        this.setPreferredSize(pref);

        this.addMouseListener(listener);

        if (iconH == null) {
            icon = this.getDefaultIcon();
        } else {
            icon = iconH;
        }
    }

    public void changeIcon(ImageIcon iconH)
    {
        if (iconH != null) {
            icon = iconH;
        }

        this.repaint();
    }

    public final ImageIcon getDefaultIcon()
    {
        return new ImageIcon(new JPanel().createImage(new FilteredImageSource(Ressourcen.kartenSet.getImage().getSource(), new CropImageFilter(12 * 166, 4 * 250, 166, 250))));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(this.getImageIcon().getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public ImageIcon getImageIcon()
    {
        return icon;
    }
}
