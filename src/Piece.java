public class Piece {
    /**
     * What type the piece is.
     * <ul>
     *     <li>0 = I piece</li>
     *     <li>1 = O piece</li>
     *     <li>2 = J piece</li>
     *     <li>3 = L piece</li>
     *     <li>4 = S piece</li>
     *     <li>5 = Z piece</li>
     *     <li>6 = T piece</li>
     * </ul>
     */
    public final int type;
    private int rotation;
    private int centerX;
    private int centerY;
    /**
     * Represents pieces relation to their center for each possible rotation of a block. The first dimension
     * represents which piece it is. The second represents the current rotation. The third represents which piece.
     * Finally, the fourth represents the x and y coordinates, respectively. This is made such that the current rotation
     * can be called using <pre>PIECE_ROTATIONS[type][rotation][0][0]</pre> to get the first x-coordinate of the default
     * rotation of your current piece type.
     */
    private static final int[][][][] PIECE_ROTATIONS = {
            //I piece
            {
                    {
                            {-2, 0}, {-1, 0}, {1, 0}
                    },
                    {
                            {0, 2}, {0, 1}, {0, -1}
                    }
            },
            //O piece
            {
                    {
                            {-1, 0}, {-1, -1}, {0, -1}
                    }
            },
            //J piece
            {
                    {
                            {-1, 0}, {1, 0}, {1, -1}
                    },
                    {
                            {0, 1}, {0, -1}, {-1, -1}
                    },
                    {
                            {-1, 1}, {-1, 0}, {1, 0}
                    },
                    {
                            {0, 1}, {1, 1}, {0, -1}
                    }
            },
            //L piece
            {
                    {
                            {-1, 0}, {-1, -1}, {1, 0}
                    },
                    {
                            {-1, 1}, {0, 1}, {0, -1}
                    },
                    {
                            {-1, 0}, {1, 0}, {1, 1}
                    },
                    {
                            {0, 1}, {0, -1}, {1, -1}
                    }
            },
            //S piece
            {
                    {
                            {-1, -1}, {0, -1}, {1, 0}
                    },
                    {
                            {0, 1}, {1, 0}, {1, -1}
                    }
            },
            //Z piece
            {
                    {
                            {-1, 0}, {0, -1}, {1, -1}
                    },
                    {
                            {1, 1}, {1, 0}, {0, -1}
                    }
            },
            //T piece
            {
                    {
                            {-1, 0}, {1, 0}, {0, -1}
                    },
                    {
                            {0, 1}, {-1, 0}, {0, -1}
                    },
                    {
                            {-1, 0}, {0, 1}, {1, 0}
                    },
                    {
                            {0, 1}, {1, 0}, {0, -1}
                    }
            }
    };

    public Piece(int type) {
        this.type = type;
        centerX = 5;
        centerY = 19;
    }

    public void rotateClockwise() {
        rotation = Math.floorMod(rotation + 1, PIECE_ROTATIONS[type].length);
    }

    public void rotateCounterclockwise() {
        rotation = Math.floorMod(rotation - 1, PIECE_ROTATIONS[type].length);
    }

    public void moveLeft() {
        centerX--;
    }

    public void moveRight() {
        centerX++;
    }

    public void moveDown() {
        centerY--;
    }

    public void moveUp() {
        centerY++;
    }

    public int[][] getCoords() {
        int[][] coords = new int[4][2];
        coords[0] = new int[] {centerX, centerY};
        for(int i = 0; i < 3; i++) {
            coords[i + 1] = new int[] {PIECE_ROTATIONS[type][rotation][i][0] + centerX, PIECE_ROTATIONS[type][rotation][i][1] + centerY};
        }
        return coords;
    }
}
