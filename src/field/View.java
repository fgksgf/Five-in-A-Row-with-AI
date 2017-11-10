package field;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

public class View {
    private Board board;

    private JFrame frame = new JFrame();
    private static int frameWidth;
    private static int frameHeight;
    private static final Font BUTTON_FONT = new Font("Consolas", Font.PLAIN, 22);
    private static final int BUTTON_DISTANCE = 60;
    private static final int BUTTON_X = 730;
    private static final int BUTTON_Y = 180;
    private static final int BUTTON_WIDTH = 110;
    private static final int BUTTON_HEIGHT = 40;
    private ArrayList<JButton> buttons = new ArrayList<>();

    private static JLabel prompt = new JLabel("BLACK's turn.");
    private static final Font LABEL_FONT = new Font("黑体", Font.BOLD, 22);
    private static final int LABEL_X = 710;
    private static final int LABEL_Y = 30;
    private static final int LABEL_WIDTH = 200;
    private static final int LABEL_HEIGHT = 100;

    public View(Board board) {
        this.board = board;

        initButtons();
        initLabel();
        initBoard();
        initFrame();
    }

    public JButton getButton(int index) {
        return buttons.get(index);
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * display the whole game frame.
     */
    public void display() {
        frame.repaint();
    }

    /**
     * set the prompt label's text
     */
    public void setPrompt(boolean isBlack) {
        if (isBlack) {
            prompt.setText("BLACK's turn.");
        } else {
            prompt.setText("WHITE's turn.");
        }
    }

    public void setPromptVisible(boolean flag) {
            prompt.setVisible(flag);
    }

    /**
     * Create three buttons, set their size, position and function.
     */
    private void initButtons() {
        JButton start = new JButton("New");
        JButton undo = new JButton("Undo");
        JButton option = new JButton("Option");
        JButton about = new JButton("About");
        JButton exit = new JButton("Exit");

        buttons.add(start);
        buttons.add(undo);
        buttons.add(option);
        buttons.add(about);
        buttons.add(exit);

        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            buttons.get(i).setBounds(BUTTON_X, BUTTON_Y + i * BUTTON_DISTANCE, BUTTON_WIDTH, BUTTON_HEIGHT);
            buttons.get(i).setFont(BUTTON_FONT);
            frame.add(buttons.get(i));
        }
    }

    /**
     * Set the frame to center and some properties.
     */
    private void initFrame() {
        frameWidth = board.width + 180;
        frameHeight = board.height + 35;
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);

        // When the window is closing, a confirm dialog will appear automatically.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JLabel msg = new JLabel("<html><h1>Are you sure to exit ?</h1></html>");
                int ch = JOptionPane.showConfirmDialog(null, msg,
                        "Prompt", JOptionPane.YES_NO_OPTION);
                if (ch == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.setResizable(false);
        frame.setTitle("Five in A Row");
        frame.setVisible(true);
    }

    /**
     * Set the label's size, location and font.
     */
    private void initLabel() {
        prompt.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        prompt.setBounds(LABEL_X,LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT);
        prompt.setFont(LABEL_FONT);
        frame.add(prompt);
    }

    private void initBoard() {
        board.setBounds(0, 0, board.width, board.height);
        frame.add(board);
    }
}