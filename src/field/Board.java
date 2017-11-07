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
//        checkX(_piece);
//        checkY(_piece);
//        checkZL(_piece);
//        checkZR(_piece);

//        if (_piece.getColor()) {
//            if (_piece.getStatus().getCountX1() >= 5
//                    || _piece.getStatus().getCountY1() >= 5
//                    || _piece.getStatus().getCountZL1() >= 5
//                    || _piece.getStatus().getCountZR1() >= 5) {
//                ret = true;
//            }
//        } else {
//            if (_piece.getStatus().getCountX2() >= 5
//                    || _piece.getStatus().getCountY2() >= 5
//                    || _piece.getStatus().getCountZL2() >= 5
//                    || _piece.getStatus().getCountZR2() >= 5) {
//                ret = true;
//            }
//        }
        return ret;
    }

    /**
     * Clear the piece at the specified position to undo a step.
     */
    public void clear(int x, int y) {
        pieces[x][y] = null;
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

    //TODO: perfect four check algorithms

    /**
     * Count the mount of the pieces with the same x,
     * calculate both the same color and different color.
     */
//    public void checkX(Piece _piece) {
//        int countX1 = 1;
//        int countX2 = 1;
//        boolean obstruct1 = false;
//        boolean obstruct2 = false;
//        int x = _piece.getX();
//        int y = _piece.getY();
//
//        if (y - 1 > 0) {
//            for (int j = y - 1; j >= 0; --j) {
//                Piece piece = pieces[x][j];
//                if (piece != null) {
//                    if (piece.getColor() && !obstruct1) {
//                        countX1++;
//                        obstruct2 = true;
//                    } else if (!piece.getColor() && !obstruct2) {
//                        countX2++;
//                        obstruct1 = true;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//
//        if (y + 1 < 15 && (countX1 < 5 || countX2 < 5)) {
//            for (int j = y + 1; j < 15; ++j) {
//                Piece piece = pieces[x][j];
//                if (piece != null) {
//                    if (piece.getColor() && !obstruct1) {
//                        countX1++;
//                        obstruct2 = true;
//                    } else if (!piece.getColor() && !obstruct2) {
//                        countX2++;
//                        obstruct1 = true;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//
//        _piece.getStatus().setCountX1(countX1);
//        _piece.getStatus().setCountX2(countX2);
//        _piece.getStatus().setObstruct1(obstruct1);
//        _piece.getStatus().setObstruct2(obstruct2);
//    }
//
//    /**
//     * Count the mount of the pieces with the same x,
//     * calculate both the same color and different color.
//     */
//    public void checkY(Piece _piece) {
//        int countY1 = 1;
//        int countY2 = 1;
//        boolean obstruct1 = false;
//        boolean obstruct2 = false;
//        int x = _piece.getX();
//        int y = _piece.getY();
//
//        if (x - 1 > 0) {
//            for (int i = x - 1; i >= 0; --i) {
//                Piece piece = pieces[i][y];
//                if (piece != null) {
//                    if (piece.getColor() && !obstruct1) {
//                        countY1++;
//                        obstruct2 = true;
//                    } else if (!piece.getColor() && !obstruct2) {
//                        countY2++;
//                        obstruct1 = true;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//        if (x + 1 < 15 && (countY1 < 5 || countY2 < 5)) {
//            for (int i = x + 1; i < 15; ++i) {
//                Piece piece = pieces[i][y];
//                if (piece != null) {
//                    if (piece.getColor() && !obstruct1) {
//                        countY1++;
//                        obstruct2 = true;
//                    } else if (!piece.getColor() && !obstruct2) {
//                        countY2++;
//                        obstruct1 = true;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//
//        _piece.getStatus().setCountY1(countY1);
//        _piece.getStatus().setCountY2(countY2);
//        _piece.getStatus().setObstruct1(obstruct1);
//        _piece.getStatus().setObstruct2(obstruct2);
//    }
//
//    /**
//     * Count the mount of the pieces with the same color
//     * and the same left oblique.
//     */
//    public void checkZL(Piece _piece) {
//        int countZL1 = 1;
//        int countZL2 = 1;
//        boolean obstruct1 = false;
//        boolean obstruct2 = false;
//        int x = _piece.getX();
//        int y = _piece.getY();
//
//        for (int i = 1; i < 15; ++i) {
//            if (x - i > 0 && y - i > 0) {
//                Piece piece = pieces[x - i][y - i];
//                if (piece != null) {
//                    if (piece.getColor() && !obstruct1) {
//                        countZL1++;
//                        obstruct2 = true;
//                    } else if (!piece.getColor() && !obstruct2) {
//                        countZL2++;
//                        obstruct1 = true;
//                    }
//                } else {
//                    break;
//                }
//            } else {
//                break;
//            }
//        }
//
//        if (countZL1 < 5 || countZL2 < 5) {
//            for (int i = 1; i < 15; ++i) {
//                if (x + i < 15 && y + i < 15) {
//                    Piece piece = pieces[x + i][y + i];
//                    if (piece != null) {
//                        if (piece.getColor() && !obstruct1) {
//                            countZL1++;
//                            obstruct2 = true;
//                        } else if (!piece.getColor() && !obstruct2) {
//                            countZL2++;
//                            obstruct1 = true;
//                        }
//                    } else {
//                        break;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//
//        _piece.getStatus().setCountZL1(countZL1);
//        _piece.getStatus().setCountZL2(countZL2);
//        _piece.getStatus().setObstruct1(obstruct1);
//        _piece.getStatus().setObstruct2(obstruct2);
//    }
//
//    /**
//     * Count the mount of the pieces with the same color
//     * and the same right oblique.
//     */
//    public void checkZR(Piece _piece) {
//        int countZR1 = 1;
//        int countZR2 = 1;
//        boolean obstruct1 = false;
//        boolean obstruct2 = false;
//        int x = _piece.getX();
//        int y = _piece.getY();
//
//        for (int i = 1; i < 15; ++i) {
//            if (x + i < 15 && y - i > 0) {
//                Piece piece = pieces[x + i][y - i];
//                if (piece != null) {
//                    if (piece.getColor() && !obstruct1) {
//                        countZR1++;
//                        obstruct2 = true;
//                    } else if (!piece.getColor() && !obstruct2) {
//                        countZR2++;
//                        obstruct1 = true;
//                    }
//                } else {
//                    break;
//                }
//            } else {
//                break;
//            }
//        }
//        if (countZR1 < 5 || countZR2 < 5) {
//            for (int i = 1; i < 15; ++i) {
//                if (x - i > 0 && y + i < 15) {
//                    Piece piece = pieces[x - i][y + i];
//                    if (piece != null) {
//                        if (piece.getColor()) {
//                            countZR1++;
//                            obstruct2 = true;
//                        } else {
//                            countZR2++;
//                            obstruct1 = true;
//                        }
//                    } else {
//                        break;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//
//        _piece.getStatus().setCountZR1(countZR1);
//        _piece.getStatus().setCountZR2(countZR2);
//        _piece.getStatus().setObstruct1(obstruct1);
//        _piece.getStatus().setObstruct2(obstruct2);
//    }
}
