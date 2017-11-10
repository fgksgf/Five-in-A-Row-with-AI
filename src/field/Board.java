package field;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import piece.*;

public class Board extends JPanel {
    private static Font font = new Font("Consolas", Font.BOLD, 25);
    private static Image board;
    public static final int width = 700;
    public static final int height = 700;
    public static final int DISTANCE = 46;
    public static final int START_X = 29;
    public static final int START_Y = 29;

    private Piece[][] pieces = new Piece[15][15];

    // Record the latest piece's index of array to mark the latest piece on the board
    private int latestX;
    private int latestY;

    static {
        try {
            board = ImageIO.read(new File("img/board.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Board() {
        initBoard();
    }

    public void initBoard() {
        latestX = -1000;
        latestY = -1000;
        clearAll();
    }

    /**
     * Clear all pieces on the board.
     */
    public void clearAll() {
        for (int i = 0; i < 15; ++i) {
            for (int j = 0; j < 15; ++j) {
                pieces[i][j] = null;
            }
        }
    }

    /**
     * Place a piece on the board.
     */
    public void place(Piece piece) {
        latestX = piece.getX();
        latestY = piece.getY();
        pieces[latestX][latestY] = piece;
    }

    /**
     * Judge if the place that the mouse clicked can place a piece.
     *
     * @return True represents can place, False represents can't.
     */
    public boolean canPlace(int x, int y) {
        boolean ret = true;
        if (x < 0 || x > 14 || y < 0 || y > 14
                || pieces[x][y] != null) {
            ret = false;
        }
        return ret;
    }

    /**
     * Check the piece if makes five in a row
     *
     * @param _piece
     * @return True represents five in a row, false represents not.
     */
    public boolean checkFive(Piece _piece) {
        boolean ret = false;
        if (checkRow(_piece) || checkColumn(_piece) || checkLeftSlash(_piece) || checkRightSlash(_piece)) {
            ret = true;
        }
        return ret;
    }

    /**
     * Clear the piece at the specified position to undo a step.
     */
    public void clear(int x, int y) {
        pieces[x][y] = null;
    }

    public void setLatestXY(int x, int y) {
        latestX = x;
        latestY = y;
    }

    /**
     * Draw the board and pieces.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(board, 0, 0, null);
        for (int i = 0; i < 15; ++i) {
            for (int j = 0; j < 15; ++j) {
                if (pieces[i][j] != null) {
                    pieces[i][j].draw(g, i * DISTANCE + START_X, j * DISTANCE + START_Y);
                }
            }
        }

        // Mark the latest piece with a question mark on it
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString("+", latestX * DISTANCE + START_X - 7, latestY * DISTANCE + START_Y + 7);
    }

    /**
     * Check if there are five chess pieces with the same color as the _piece in the ROW where _piece is
     * then return the result: true or false
     */
    public boolean checkRow(Piece _piece) {
        boolean result = false;
        int count = 1;
        int x = _piece.getX();
        int y = _piece.getY();

        // Count the number of pieces have the same color with _piece on the left of _piece
        if (x - 1 > 0) {
            for (int i = x - 1; i >= 0; --i) {
                Piece p = pieces[i][y];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        // Count the number of pieces have the same color with _piece on the right of _piece
        if (x + 1 < 15) {
            for (int i = x + 1; i < 15; ++i) {
                Piece p = pieces[i][y];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        if (count >= 5) {
            result = true;
        }
        return result;
    }

    /**
     * Check if there are five chess pieces with the same color as the _piece in the COLUMN where _piece is
     * then return the result: true or false
     */
    public boolean checkColumn(Piece _piece) {
        boolean result = false;
        int count = 1;
        int x = _piece.getX();
        int y = _piece.getY();

        // Count the number of pieces have the same color with _piece on the top of _piece
        if (y - 1 > 0) {
            for (int j = y - 1; j >= 0; --j) {
                Piece p = pieces[x][j];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        // Count the number of pieces have the same color with _piece on the bottom of _piece
        if (y + 1 < 15) {
            for (int j = y + 1; j < 15; ++j) {
                Piece p = pieces[x][j];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        if (count >= 5) {
            result = true;
        }
        return result;
    }

    /**
     * Check if there are five chess pieces with the same color as the _piece in the SLASH where _piece is
     * The SLASH's direction is from the UPPER LEFT corner to the LOWER RIGHT corner
     */
    public boolean checkLeftSlash(Piece _piece) {
        boolean result = false;
        int count = 1;
        int x = _piece.getX();
        int y = _piece.getY();

        // Count the number of pieces have the same color with _piece on the UPPER LEFT corner of _piece
        for (int i = 1; i < 15; ++i) {
            if (x - i >= 0 && y - i >= 0) {
                Piece p = pieces[x - i][y - i];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        // Count the number of pieces have the same color with _piece on the LOWER RIGHT corner of _piece
        for (int i = 1; i < 15; ++i) {
            if (x + i < 15 && y + i < 15) {
                Piece p = pieces[x + i][y + i];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        if (count >= 5) {
            result = true;
        }
        return result;
    }

    /**
     * Check if there are five chess pieces with the same color as the _piece in the SLASH where _piece is
     * The SLASH's direction is from the UPPER RIGHT corner to the LOWER LEFT corner
     */
    public boolean checkRightSlash(Piece _piece) {
        boolean result = false;
        int count = 1;
        int x = _piece.getX();
        int y = _piece.getY();

        // Count the number of pieces have the same color with _piece on the UPPER RIGHT corner of _piece
        for (int i = 1; i < 15; ++i) {
            if (x + i < 15 && y - i >= 0) {
                Piece p = pieces[x + i][y - i];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        // Count the number of pieces have the same color with _piece on the LOWER LEFT corner of _piece
        for (int i = 1; i < 15; ++i) {
            if (x - i >= 0 && y + i < 15) {
                Piece p = pieces[x - i][y + i];
                if (p != null && p.getColor() == _piece.getColor()) {
                    count++;
                } else {
                    break;
                }
            }
        }

        if (count >= 5) {
            result = true;
        }
        return result;
    }
}
