package MoepClient.GUI;

import Moep.Karte;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Beschreibt die (grafische) Hand eines Spielers
 * @author Philipp Herrle
 */
public class Hand extends JLabel
{

    private final int hGap;
    private final int vBorder;
    private Rectangle[] kartenPos;

    public Hand(int hGapH, int vBorderH, MouseAdapter karteLegen)
    {
        super();

        hGap = hGapH;
        vBorder = vBorderH;

        this.setBackground(Color.DARK_GRAY);
        this.setOpaque(true);

        this.addMouseListener(karteLegen);

        this.setPreferredSize(new Dimension(0, 240));
    }

    public void kartenAktualisieren(List<Karte> karten)
    {
        kartenPos = new Rectangle[karten.size()];

        ImageIcon[] bilder = new ImageIcon[karten.size()];

        for (int i = 0; i < bilder.length; i++) {
            bilder[i] = karten.get(i).gibBild();
        }

        int handHeight = this.getParent().getHeight();

        int bildHeight = handHeight - (2 * vBorder);
        int bildWidth = (int) bildHeight * 2 / 3;

        int handWidth = (bildWidth * bilder.length + (2 * hGap));

        if (bilder.length > 0) {
            handWidth += (hGap * (bilder.length - 1));
        }
        if (handWidth < this.getParent().getWidth()) {
            handWidth = this.getParent().getWidth();
        }

        BufferedImage handNeu = new BufferedImage(handWidth, handHeight, BufferedImage.TYPE_INT_ARGB_PRE);

        Graphics2D graph = handNeu.createGraphics();

        for (int i = 0; i < bilder.length; i++) {

            Rectangle rectangleBild = new Rectangle((i * bildWidth) + ((i + 1) * hGap), vBorder, bildWidth, bildHeight);

            kartenPos[i] = rectangleBild;
            graph.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graph.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graph.drawImage(bilder[i].getImage(), rectangleBild.x, rectangleBild.y, rectangleBild.width, rectangleBild.height, null);
        }

        this.setPreferredSize(new Dimension(handNeu.getWidth(), handNeu.getHeight()));

        this.setIcon(new ImageIcon(handNeu));

        this.repaint();
    }

    public int gibIndexKarte(Point MouseKlick)
    {
        int res = -1;

        stop:
        for (int i = 0; i < kartenPos.length; i++) {

            if (kartenPos[i].contains(MouseKlick) == true) {
                res = i;
                break stop;
            }
        }
        return res;
    }
}
