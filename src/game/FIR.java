package game;

import piece.Piece;
import field.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FIR {
    private Board board = new Board();
    private View view = new View(board);

    private boolean opponentAI = false;
    private boolean playerColor = true;

    private Status status = new Status();

//    private AIRunnable aiRunnable;

    private static final int distance = Board.DISTANCE;
    private static final int startX = Board.START_X;
    private static final int startY = Board.START_Y;
    private static final String aboutMessage = "Author: Zerone\n" +
            "Email: fgksgf@yahoo.com\n" +
            "Version: 1.0";

    private void init() {
        status.setIsOver(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
//        aiRunnable = null;
        board.clearAll();
        status.reset();
        if (opponentAI) {
//            aiRunnable = new AIRunnable(!playerColor, status, board);
//            aiRunnable.start();
        }
        status.updateTurn();
        view.setPrompt(status.isBlackTurn());
    }

    /**
     * To check if the game is over. If so, check who is the winner.
     */
    public void check() {
        if (status.isOver()) {
            if (status.isBlackTurn()) {
                JOptionPane.showMessageDialog(null,
                        "WHITE wins !", "Prompt", JOptionPane.CLOSED_OPTION);
            } else {
                JOptionPane.showMessageDialog(null,
                        "BLACK wins !", "Prompt", JOptionPane.CLOSED_OPTION);
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
                            Piece newPiece = new Piece(x, y, status.isBlackTurn());
                            board.place(newPiece);
                            status.addPiece(newPiece);
                            status.updateTurn();
                            if (board.checkFive(newPiece)) {
                                status.setIsOver(true);
                            }
                            if (!status.isOver()) {
                                view.setPrompt(status.isBlackTurn());
                            }
                        }
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
            int choice = JOptionPane.showConfirmDialog(null,
                    "Want to start a new game ?",
                    "Prompt", JOptionPane.YES_NO_OPTION);

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
            if ((!status.isOver())
                    && (status.getHasPutSize() != 0)
                    && (status.isBlackTurn() == playerColor)) {
                // stall the ai
//                status.setStallAI(true);
//
//                for (int i = 0; i < 2; ++i) {
//                    Piece lastPiece = status.getLastPiece();
//                    if (lastPiece != null) {
//                        board.clear(lastPiece.getX(), lastPiece.getY());
//                        status.removePiece(lastPiece);
//                    }
//                    status.updateTurn();
//                }
//
//                status.setStallAI(false);
//                view.setPrompt(status.isBlackTurn());
            }
        });
    }

    /**
     * Add the option button's action
     */
    public void addOptionButtonEvent() {
        view.getButton(2).addActionListener(e -> {
            JDialog dialog = new JDialog(view.getFrame(), "Option");
            dialog.setSize(350, 250);
            dialog.setLocation(512, 300);
            dialog.setLayout(new GridLayout(3, 1));

            String[] c1 = {"Computer", "Player"};
            String[] c2 = {"Black (Offensive)", "White (Defensive)"};
            JComboBox opponent = new JComboBox(c1);
            opponent.setSelectedIndex(0);
            opponent.setBorder(BorderFactory.createTitledBorder("Choose your opponent"));
            JComboBox color = new JComboBox(c2);
            color.setSelectedIndex(0);
            color.setBorder(BorderFactory.createTitledBorder("Choose your piece's color"));

            dialog.add(opponent);
            dialog.add(color);
            JLabel hint = new JLabel("Hint: Restart the game to make the settings take effect.");
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
            int ch = JOptionPane.showConfirmDialog(null,"Are you sure to exit ?",
                    "Prompt",JOptionPane.YES_NO_OPTION);
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

    private boolean stallAI = false;

    // Store all of steps' positions
    private ArrayList<Piece> hasPut = new ArrayList<>();

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

    public synchronized Piece getLastPiece() {
        Piece ret = null;
        if (hasPut.size() > 0) {
            ret = hasPut.get(hasPut.size() - 1);
        }
        return ret;
    }

    public synchronized Piece getPiece(int index) {
        return hasPut.get(index);
    }

    public synchronized int getHasPutSize() {
        return hasPut.size();
    }

    public synchronized void removePiece(Piece piece) {
        hasPut.remove(piece);
    }

    public synchronized void addPiece(Piece piece) {
        hasPut.add(piece);
    }
}
