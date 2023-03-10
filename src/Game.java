import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Game extends JLabel implements KeyListener {
    /**
     * This is the grid for the board. The bottom-left corner is [0][0].
     * Like most 2D-Arrays it is [row][col]. It uses int to represent color.
     * Since there are 7 pieces, there are 7 colors each corresponds to a piece.
     */
    private final int SCALE = 50;
    private final int[][] grid = new int[23][10];
    private int clearedLines = 0;
    private final int startLevel;
    private int level;
    private Piece currentPiece;
    int nextPiece = -1;
    private int softDropPoints;
    private final SideScreen ss;
    private static final HashMap<Integer, Color> NUM_TO_COLOR = new HashMap<>();
    private static final int[] SPEEDS = {48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1};
    Timer dropPiece, softDrop;

    static {
        NUM_TO_COLOR.put(0, Color.DARK_GRAY.darker().darker());
        NUM_TO_COLOR.put(1, Color.CYAN);
        NUM_TO_COLOR.put(2, Color.YELLOW);
        NUM_TO_COLOR.put(3, Color.ORANGE);
        NUM_TO_COLOR.put(4, Color.BLUE);
        NUM_TO_COLOR.put(5, Color.GREEN);
        NUM_TO_COLOR.put(6, Color.RED);
        NUM_TO_COLOR.put(7, new Color(169, 76, 220));
    }

    public Game(int startLevel) {
        super();

        this.startLevel = startLevel;
        level = startLevel;

        setPreferredSize(new Dimension(SCALE * 10, SCALE * 20));
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        softDrop = new Timer(33, e -> {
            moveDown();
            repaint();
            dropPiece.restart();
        });
        softDrop.setInitialDelay(0);
        dropPiece = new Timer(0, e -> {
            if (!softDrop.isRunning()) {
                moveDown();
                repaint();
            }
        });

        ss = new SideScreen();
        currentPiece = new Piece((int) (Math.random() * 7));
        while (nextPiece == currentPiece.type || nextPiece == -1) nextPiece = (int) (Math.random() * 7);
        ss.setNextPiece(SCALE, nextPiece);

        calcDelay();
        dropPiece.setInitialDelay(2000);
        dropPiece.restart();
    }

    public SideScreen getSideScreen() {
        return ss;
    }

    private synchronized void rotateClockwise() {
        currentPiece.rotateClockwise();
        if (invalid()) rotateCounterclockwise();
    }

    private synchronized void rotateCounterclockwise() {
        currentPiece.rotateCounterclockwise();
        if (invalid()) rotateCounterclockwise();
    }

    private synchronized void moveLeft() {
        currentPiece.moveLeft();
        if (invalid()) moveRight();
    }

    private synchronized void moveRight() {
        currentPiece.moveRight();
        if (invalid()) moveLeft();
    }

    private synchronized void moveDown() {
        currentPiece.moveDown();
        if (softDrop.isRunning()) softDropPoints++;
        if (invalid()) moveUp();
    }

    private synchronized void moveUp() {
        currentPiece.moveUp();
        solidify();
    }

    private synchronized void solidify() {
        int[][] coords = currentPiece.getCoords();
        for (int[] coord : coords) {
            grid[coord[1]][coord[0]] = currentPiece.type + 1;
        }
        clearLines();
        ss.sd.incrementScore(softDropPoints);
        softDropPoints = 0;
        currentPiece = new Piece(nextPiece);
        nextPiece = (int) (Math.random() * 7);
        ss.setNextPiece(SCALE, nextPiece);
        if (invalid()) setVisible(false);
    }

    private void clearLines() {
        int lines = 0;
        outer:
        for (int i = 0; i < grid.length; i++) {
            for (int cell : grid[i]) {
                if (cell == 0) continue outer;
            }
            for (int j = i; j < grid.length - 1; j++) {
                grid[j] = grid[j + 1];
            }
            grid[grid.length - 1] = new int[10];
            i--;
            lines++;
        }
        clearedLines += lines;
        ss.sd.incrementScore((level + 1) * (lines == 1 ? 40 : lines == 2 ? 100 : lines == 3 ? 300 : lines == 4 ? 1200 : 0));
        calcDelay();
    }

    private void calcDelay() {
        int threshold = Math.min(startLevel * 10 + 10, Math.max(100, startLevel * 10 - 50));
        level = startLevel + Math.max((clearedLines - threshold + 10) / 10, 0);
        int delay = (int) (((double) SPEEDS[Math.min(level, 29)] / 60) * 1000);
        dropPiece.setDelay(delay);
        dropPiece.setInitialDelay(delay);
        dropPiece.restart();
        ss.updateLevelDisplay(level, clearedLines);
    }

    private boolean invalid() {
        try {
            int[][] coords = currentPiece.getCoords();
            for (int[] coord : coords) {
                if (grid[coord[1]][coord[0]] != 0) return true;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                g.setColor(NUM_TO_COLOR.get(grid[i][j]));
                g.fillRect(j * SCALE, getHeight() - (i + 1) * SCALE, SCALE, SCALE);
                g.setColor(NUM_TO_COLOR.get(grid[i][j]).darker());
                g.drawRect(j * SCALE, getHeight() - (i + 1) * SCALE, SCALE, SCALE);
            }
        }
        for (int[] loc : currentPiece.getCoords()) {
            g.setColor(NUM_TO_COLOR.get(currentPiece.type + 1));
            g.fillRect(loc[0] * SCALE, getHeight() - (loc[1] + 1) * SCALE, SCALE, SCALE);
            g.setColor(NUM_TO_COLOR.get(currentPiece.type + 1).darker());
            g.drawRect(loc[0] * SCALE, getHeight() - (loc[1] + 1) * SCALE, SCALE, SCALE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_X) rotateClockwise();
        if (e.getKeyCode() == KeyEvent.VK_Z) rotateCounterclockwise();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight();
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            softDrop.start();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            softDrop.stop();
            softDropPoints = 0;
        }
    }
}
