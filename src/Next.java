import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Next extends JLabel {
    int pieceType;
    int scale;
    public Next(Font FONT) {
        super();
        setPreferredSize(new Dimension(232, 232));
        Border titled = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 3), "NEXT", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, FONT.deriveFont(16f), Color.WHITE);
        Border compound = new CompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), titled);
        compound = new CompoundBorder(compound, BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBorder(compound);
    }

    public void setNextPiece(int pieceType, int scale) {
        this.pieceType = pieceType;
        this.scale = scale;
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY.darker().darker());
        g.fillRect(0, 0, getWidth(), getHeight());
        int[][] rectanglePoints = new int[4][2];
        if(pieceType == 0) {
            g.setColor(Color.CYAN);
            rectanglePoints[0] = new int[] {0, scale *  3 / 2};
            rectanglePoints[1] = new int[] {scale, scale * 3 / 2};
            rectanglePoints[2] = new int[] {scale * 2, scale * 3 / 2};
            rectanglePoints[3] = new int[] {scale * 3, scale * 3 / 2};
        }
        if(pieceType == 1) {
            g.setColor(Color.YELLOW);
            rectanglePoints[0] = new int[] {scale, scale};
            rectanglePoints[1] = new int[] {2 * scale, scale};
            rectanglePoints[2] = new int[] {scale, 2 * scale};
            rectanglePoints[3] = new int[] {2 * scale, 2 * scale};
        }
        if(pieceType == 2) {
            g.setColor(Color.ORANGE);
            rectanglePoints[0] = new int[] {scale * 3 / 2, scale};
            rectanglePoints[1] = new int[] {scale / 2, scale};
            rectanglePoints[2] = new int[] {scale * 5 / 2, scale};
            rectanglePoints[3] = new int[] {scale * 5 / 2, scale * 2};
        }
        if(pieceType == 3) {
            g.setColor(Color.BLUE);
            rectanglePoints[0] = new int[] {scale * 3 / 2, scale};
            rectanglePoints[1] = new int[] {scale / 2, scale};
            rectanglePoints[2] = new int[] {scale * 5 / 2, scale};
            rectanglePoints[3] = new int[] {scale / 2, scale * 2};
        }
        if(pieceType == 4) {
            g.setColor(Color.GREEN);
            rectanglePoints[0] = new int[] {scale * 3 / 2, scale};
            rectanglePoints[1] = new int[] {scale / 2, scale * 2};
            rectanglePoints[2] = new int[] {scale * 5 / 2, scale};
            rectanglePoints[3] = new int[] {scale * 3 / 2, scale * 2};
        }
        if(pieceType == 5) {
            g.setColor(Color.RED);
            rectanglePoints[0] = new int[] {scale * 3 / 2, scale};
            rectanglePoints[1] = new int[] {scale / 2, scale};
            rectanglePoints[2] = new int[] {scale * 5 / 2, scale * 2};
            rectanglePoints[3] = new int[] {scale * 3 / 2, scale * 2};
        }
        if(pieceType == 6) {
            g.setColor(new Color(169, 76, 220));
            rectanglePoints[0] = new int[] {scale * 3 / 2, scale};
            rectanglePoints[1] = new int[] {scale / 2, scale};
            rectanglePoints[2] = new int[] {scale * 5 / 2, scale};
            rectanglePoints[3] = new int[] {scale * 3 / 2, scale * 2};
        }
        for(int[] point : rectanglePoints) {
            g.fillRect(point[0] + 16, point[1] + 16, scale, scale);
        }
        g.setColor(g.getColor().darker());
        for(int[] point : rectanglePoints) {
            g.drawRect(point[0] + 16, point[1] + 16, scale, scale);
        }
    }
}
