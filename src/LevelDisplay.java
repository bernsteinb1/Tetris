import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class LevelDisplay extends JTextPane{
    Style style;
    StyledDocument doc;
    public LevelDisplay(Font FONT) {
        super();
        setFocusable(false);
        setEditable(false);
        setBackground(Color.DARK_GRAY.darker().darker());
        doc = getStyledDocument();
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(FONT);

        style = doc.addStyle("main", null);

        StyleConstants.setForeground(style, Color.WHITE);
        StyleConstants.setBackground(style, Color.DARK_GRAY.darker().darker());
        StyleConstants.setFontFamily(style, FONT.getFamily());
        StyleConstants.setFontSize(style, 15);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), BorderFactory.createLineBorder(Color.WHITE, 3)));
        setBorder(new CompoundBorder(getBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public void setInfo(int level, int lines) {
        try {
            doc.remove(0, doc.getLength());
            doc.insertString(0, String.format("LEVEL\n%02d\n\nLINES\n%03d", level, lines), style);
        } catch(Exception ignored) {}
        doc.setParagraphAttributes(0, doc.getLength(), style, false);
    }
}
