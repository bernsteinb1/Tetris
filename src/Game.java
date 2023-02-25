import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;

public class Game extends JLabel implements KeyListener {
    /**
     * This is the grid for the board. The bottom-left corner is [0][0].
     * Like most 2D-Arrays it is [row][col]. It uses int to represent color.
     * Since there are 7 pieces, there are 7 colors each corresponds to a piece.
     */
    private final int[][] grid = new int[23][10];
    private final int scale = 50;
    private int clearedLines = 50;
    private Piece currentPiece = new Piece(0);
    private static final HashMap<Integer, Color> NUM_TO_COLOR = new HashMap<>();
    private static final int[] SPEEDS = {48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1};
    static {
        NUM_TO_COLOR.put(-1, Color.BLACK);
        NUM_TO_COLOR.put(0, Color.CYAN);
        NUM_TO_COLOR.put(1, Color.YELLOW);
        NUM_TO_COLOR.put(2, Color.ORANGE);
        NUM_TO_COLOR.put(3, Color.BLUE);
        NUM_TO_COLOR.put(4, Color.GREEN);
        NUM_TO_COLOR.put(5, Color.RED);
        NUM_TO_COLOR.put(6, new Color(169, 76, 220));
    }
    Timer dropPiece;
    public Game() {
        super();
        for (int[] row : grid) {
            Arrays.fill(row, -1);
        }
        setPreferredSize(new Dimension(scale * 10, scale * 20));
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        dropPiece = new Timer(800, e -> {
            moveDown();
            repaint();
        });
        dropPiece.start();
    }

    private synchronized void rotateClockwise() {
        currentPiece.rotateClockwise();
        if(invalid()) rotateCounterclockwise();
    }

    private synchronized void rotateCounterclockwise() {
        currentPiece.rotateCounterclockwise();
        if(invalid()) rotateCounterclockwise();
    }

    private synchronized void moveLeft() {
        currentPiece.moveLeft();
        if(invalid()) moveRight();
    }

    private synchronized void moveRight() {
        currentPiece.moveRight();
        if(invalid()) moveLeft();
    }

    private synchronized void moveDown() {
        currentPiece.moveDown();
        if(invalid()) moveUp();
    }

    private synchronized void moveUp() {
        currentPiece.moveUp();
        solidify();
    }

    private synchronized void solidify() {
        int[][] coords = currentPiece.getCoords();
        for(int[] coord : coords) {
            grid[coord[1]][coord[0]] = currentPiece.type;
        }
        clearLines();
        currentPiece = new Piece((int) (Math.random() * 7));
        if(invalid()) setVisible(false);
    }

    private void clearLines() {
        outer:
        for (int i = 0; i < grid.length; i++) {
            for (int cell : grid[i]) {
                if (cell == -1) continue outer;
            }
            for(int j = i; j < grid.length - 1; j++) {
                grid[j] = grid[j + 1];
            }
            grid[grid.length - 1] = new int[10];
            Arrays.fill(grid[grid.length - 1], -1);
            i--;
            clearedLines++;
        }
        dropPiece.setDelay((int) (((double) SPEEDS[Math.min(clearedLines / 10, 29)] / 60) * 1000));
    }

    private boolean invalid() {
        try {
            int[][] coords = currentPiece.getCoords();
            for (int[] coord : coords) {
                if (grid[coord[1]][coord[0]] != -1) return true;
            }
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                g.setColor(NUM_TO_COLOR.get(grid[i][j]));
                g.fillRect(j * scale, getHeight() - (i + 1) * scale, scale, scale);
                g.setColor(NUM_TO_COLOR.get(grid[i][j]).darker());
                g.drawRect(j * scale, getHeight() - (i + 1) * scale, scale, scale);
            }
        }
        for(int[] loc : currentPiece.getCoords()) {
            g.setColor(NUM_TO_COLOR.get(currentPiece.type));
            g.fillRect(loc[0] * scale, getHeight() - (loc[1] + 1) * scale, scale, scale);
            g.setColor(NUM_TO_COLOR.get(currentPiece.type).darker());
            g.drawRect(loc[0] * scale, getHeight() - (loc[1] + 1) * scale, scale, scale);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_X) rotateClockwise();
        if(e.getKeyCode() == KeyEvent.VK_Z) rotateCounterclockwise();
        if(e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft();
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight();
        if(e.getKeyCode() == KeyEvent.VK_DOWN) moveDown();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
