package game;

import piece.Piece;
import field.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class FIR {
    private Board board = new Board();
    private View view = new View(board);

    private boolean opponentAI = false;
    private boolean playerColor = true;
    private Font hintFont = new Font("Consolas", Font.BOLD, 17);
    private Font borderFont = new Font("Consolas", Font.BOLD, 20);
    private Font choiceFont = new Font("Consolas", Font.PLAIN, 20);
    private Status status = new Status();

//    private AIRunnable aiRunnable;

    private static final int distance = Board.DISTANCE;
    private static final int startX = Board.START_X;
    private static final int startY = Board.START_Y;
    private static final JLabel aboutMessage = new JLabel("<html><h1>Author: Zerone<br>" +
            "Email: fgksgf@yahoo.com<br/>" +
            "Version: 1.0</h1></html>");

    private void init() {
        status.setIsOver(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
//        aiRunnable = null;
        board.initBoard();
        status.reset();
        if (opponentAI) {
//            aiRunnable = new AIRunnable(!playerColor, status, board);
//            aiRunnable.start();
        }
        status.updateTurn();
        view.setPrompt(status.isBlackTurn());
    }

    public void placePiece(int x, int y) {
        Piece newPiece = new Piece(x, y, status.isBlackTurn());
        board.place(newPiece);
        status.addPiece(newPiece);
        status.updateTurn();

        if (board.checkFive(newPiece)) {
            status.setIsOver(true);
        } else if (status.getHasPutSize() == 225) {
            status.setIsOver(true);
            status.setDraw(true);
        }

        if (!status.isOver()) {
            view.setPrompt(status.isBlackTurn());
        }
    }

    /**
     * To check if the game is over. If so, check who is the winner.
     */
    public void check() {
        if (status.isOver()) {
            if (status.isDraw()) {
                JLabel msg = new JLabel("<html><h1>The game ended in a draw.</h1></html>");
                JOptionPane.showMessageDialog(null, msg, "Prompt", JOptionPane.CLOSED_OPTION);
            } else {
                if (status.isBlackTurn()) {
                    JLabel msg = new JLabel("<html><h1>WHITE wins !</h1></html>");
                    JOptionPane.showMessageDialog(null, msg, "Prompt",
                            JOptionPane.CLOSED_OPTION);
                } else {
                    JLabel msg = new JLabel("<html><h1>BLACK wins !</h1></html>");
                    JOptionPane.showMessageDialog(null, msg, "Prompt",
                            JOptionPane.CLOSED_OPTION);
                }
            }
        } else {
            status.updateTurn();
        }
    }

    /**
     * Add mouse clicked event
     */
    public void addMouseEvent() {
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!status.isOver()
                        && ((status.isBlackTurn() == playerColor)
                        || (!opponentAI))) {
                    super.mouseClicked(e);
                    int x = e.getX();
                    int y = e.getY();
                    if (x >= 10 && x <= 690 && y >= 10 && y <= 690) {
                        x = (int) Math.round((double) (x - startX) / distance);
                        y = (int) Math.round((double) (y - startY) / distance);
                        if (board.canPlace(x, y)) {
                            placePiece(x, y);
                        }
                        view.display();
                    }
                }
            }
        });
    }

    /**
     * add the new button's action
     */
    public void addNewButtonEvent() {
        view.getButton(0).addActionListener(e -> {
            JLabel msg = new JLabel("<html><h1>Want to start a new game ?</h1></html>");
            int choice = JOptionPane.showConfirmDialog(null, msg, "Prompt",
                    JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                init();
            }
        });
    }

    /**
     * Add the undo button's action to retreat one step.
     */
    public void addUndoButtonEvent() {
        view.getButton(1).addActionListener((ActionEvent e) -> {
            if ((!status.isOver()) && (status.getHasPutSize() != 0)) {

                // When the opponent is AI and want to undo
                if (opponentAI && status.isBlackTurn() == playerColor) {
                    // Remove two consecutive pieces in the stack
                    for (int i = 0; i < 2; ++i) {
                        Piece lastPiece = status.popPiece();
                        if (lastPiece != null) {
                            board.clear(lastPiece.getX(), lastPiece.getY());
                        }
                    }

                } else if (!opponentAI) {
                    // When the opponent is AI and want to undo
                    Piece lastPiece = status.popPiece();
                    if (lastPiece != null) {
                        board.clear(lastPiece.getX(), lastPiece.getY());
                    }
                }

                status.updateTurn();

                // Modify the latest piece's coordinate
                Piece p = status.getLastPiece();
                if (p != null) {
                    board.setLatestXY(p.getX(), p.getY());
                } else {
                    board.setLatestXY(-1000, -1000);
                }

                view.setPrompt(status.isBlackTurn());
            }
        });
    }

    /**
     * Add the option button's action
     */
    public void addOptionButtonEvent() {
        view.getButton(2).addActionListener(e -> {
            JDialog dialog = new JDialog(view.getFrame(), "Option");
            dialog.setSize(400, 380);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(new GridLayout(4, 1));

//            String[] c1 = {"Computer", "Player"};
            String[] c1 = {"Player"};
            String[] c2 = {"Black (Offensive)", "White (Defensive)"};
            String[] c3 = {"Easy", "Normal", "Hard", "Very hard", "Abnormal"};

            JComboBox opponent = new JComboBox(c1);
            opponent.setSelectedIndex(0);
            opponent.setFont(choiceFont);
            TitledBorder b1 = BorderFactory.createTitledBorder("Choose your opponent");
            b1.setTitleFont(borderFont);
            opponent.setBorder(b1);


            JComboBox color = new JComboBox(c2);
            color.setFont(choiceFont);
            color.setSelectedIndex(0);
            TitledBorder b2 = BorderFactory.createTitledBorder("Choose your piece's color");
            b2.setTitleFont(borderFont);
            color.setBorder(b2);

            JComboBox difficulty = new JComboBox(c3);
            difficulty.setFont(choiceFont);
            difficulty.setSelectedIndex(1);
            TitledBorder b3 = BorderFactory.createTitledBorder("Choose AI's difficulty");
            b3.setTitleFont(borderFont);
            difficulty.setBorder(b3);

            dialog.add(opponent);
            dialog.add(color);
            dialog.add(difficulty);

            JLabel hint = new JLabel(" Restart to make the settings take effect.");
            hint.setFont(hintFont);
            dialog.add(hint);
            dialog.setVisible(true);

            opponent.addItemListener(e1 -> {
                if (e1.getStateChange() == ItemEvent.SELECTED) {
                    if (opponent.getSelectedIndex() == 1) {
                        opponentAI = false;
                    }
                }
            });

            color.addItemListener(e2 -> {
                if (e2.getStateChange() == ItemEvent.SELECTED) {
                    if (color.getSelectedIndex() == 1) {
                        playerColor = false;
                    }
                }
            });
        });
    }

    public void addAboutButtonEvent() {
        view.getButton(3).addActionListener(e -> {
            JOptionPane.showMessageDialog(null, aboutMessage);
        });
    }

    /**
     * Add the exit button's action to exit the game.
     */
    public void addExitButtonEvent() {
        view.getButton(4).addActionListener(e -> {
            JLabel msg = new JLabel("<html><h1>Are you sure to exit ?</h1></html>");
            int ch = JOptionPane.showConfirmDialog(null, msg,
                    "Prompt", JOptionPane.YES_NO_OPTION);
            if (ch == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    /**
     * The main part of the game.
     */
    public void run() {
        addMouseEvent();
        addNewButtonEvent();
        addUndoButtonEvent();
        addOptionButtonEvent();
        addAboutButtonEvent();
        addExitButtonEvent();
        init();

        while (true) {
            if (!status.isOver()) {
                view.setPrompt(status.isBlackTurn());
                check();
            }
            view.setPromptVisible(!status.isOver());
            view.display();
        }
    }
}

class Status {
    // True represents Black's turn currently, false represents White's turn.
    private boolean blackTurn = true;

    private boolean isOver = false;

    // If the game ended in a draw
    private boolean isDraw = false;

    private boolean stallAI = false;

    // Store all of steps' positions
    private Stack<Piece> hasPut = new Stack<>();

    public void setStallAI(boolean status) {
        stallAI = status;
    }

    public boolean isStallAI() {
        return stallAI;
    }

    public synchronized void reset() {
        hasPut.clear();
        blackTurn = true;
        isOver = false;
    }

    public synchronized boolean isBlackTurn() {
        return blackTurn;
    }

    public synchronized void updateTurn() {
        blackTurn = hasPut.size() % 2 == 0;
    }

    public synchronized boolean isOver() {
        return isOver;
    }

    public synchronized void setIsOver(boolean over) {
        isOver = over;
    }

    public synchronized Piece popPiece() {
        Piece ret = null;
        if (!hasPut.isEmpty()) {
            ret = hasPut.pop();
        }
        return ret;
    }

    public synchronized Piece getLastPiece() {
        Piece ret = null;
        int s = hasPut.size() - 1;
        if (s >= 0) {
            ret = hasPut.get(s);
        }
        return ret;
    }

    public synchronized int getHasPutSize() {
        return hasPut.size();
    }

    public synchronized void addPiece(Piece piece) {
        hasPut.push(piece);
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
