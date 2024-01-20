import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class SideScreen extends JPanel {
    ScoreDisplay sd;
    LevelDisplay ld;
    Next next;
    private static final Font FONT;
    static {
        InputStream kongtext = SideScreen.class.getClassLoader().getResourceAsStream("kongtext.ttf");
        try {
            FONT = Font.createFont(Font.TRUETYPE_FONT, kongtext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't find font file");
        }
    }
    public SideScreen() {
        super();
        setLayout(new BorderLayout());
        setFocusable(false);
        setBackground(Color.DARK_GRAY.darker().darker());
        sd = new ScoreDisplay(FONT);
        next = new Next(FONT);
        ld = new LevelDisplay(FONT);
        add(sd, BorderLayout.NORTH);
        add(next);
        add(ld, BorderLayout.SOUTH);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

    }

    public void setNextPiece(int scale, int pieceType) {
        next.setNextPiece(pieceType, scale);
    }

    public void updateLevelDisplay(int level, int lines) {
        ld.setInfo(level, lines);
    }
}
