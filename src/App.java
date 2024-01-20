import javax.swing.*;
import java.awt.*;


public class App {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(Color.DARK_GRAY.darker().darker());
        addComponents(f);
        f.setResizable(false);
        f.setVisible(true);
    }
    public static void addComponents(JFrame f) {
        Game g = new Game(18);
        SideScreen ss = g.getSideScreen();
        f.add(g);
        f.add(ss, BorderLayout.EAST);
        f.pack();

    }
}
