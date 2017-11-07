package piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Piece {
    private int x;
    private int y;

    // true represents black, false represents white.
    private boolean isBlack;

    private static Image blackPiece;
    private static Image whitePiece;

    // The width and height of the piece
    public static int pieceSize;

    static {
        try {
            blackPiece = ImageIO.read(new File("img/b.png"));
            whitePiece = ImageIO.read(new File("img/w.png"));
            pieceSize = blackPiece.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Piece(int x, int y, boolean color) {
        this(x, y);
        this.isBlack = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getColor() {
        return isBlack;
    }

    /**
     * @param x the piece' s center's x
     * @param y the piece' s center's y
     */
    public void draw(Graphics g, int x, int y) {
        if (isBlack) {
            g.drawImage(blackPiece,x - pieceSize / 2, y - pieceSize / 2,null);
        } else {
            g.drawImage(whitePiece,x - pieceSize / 2, y - pieceSize / 2,null);
        }
    }
}