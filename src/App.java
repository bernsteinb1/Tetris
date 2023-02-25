import javax.swing.JFrame;


public class App {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Game());
        f.pack();
        f.setResizable(false);
        f.setVisible(true);
    }
}
