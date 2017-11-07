//package game;
//
//import field.Board;
//import piece.Piece;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Random;
//
//public class AIRunnable extends Thread {
//    // AI's pieces' color
//    private boolean color;
//
//    // Store all of positions that AI can put a piece
//    private ArrayList<Piece> choices = new ArrayList<>();
//
//    private Piece leftUp;
//    private Piece rightDown;
//    private Status status;
//    private Board board;
//
//    public AIRunnable(boolean color, Status status, Board board) {
//        this.color = color;
//        this.status = status;
//        this.board = board;
//    }
//
//    /**
//     * Calculate the border of the search district.
//     */
//    public void calBorder() {
//        int minX = 15;
//        int maxX = -1;
//        int minY = 15;
//        int maxY = -1;
//
//        for (int i = 0; i < status.getHasPutSize(); ++i) {
//            Piece pos = status.getPiece(i);
//            if (pos.getX() > maxX) {
//                maxX = pos.getX();
//            }
//
//            if (pos.getX() < minX) {
//                minX = pos.getX();
//            }
//
//            if (pos.getY() > maxY) {
//                maxY = pos.getY();
//            }
//
//            if (pos.getY() < minY) {
//                minY = pos.getY();
//            }
//        }
//
//        for (int i = 0; i < 2; ++i) {
//            if (minX - 1 >= 0) {
//                minX--;
//            }
//            if (minY - 1 >= 0) {
//                minY--;
//            }
//            if (maxX + 1 < 15) {
//                maxX++;
//            }
//            if (maxY + 1 < 15) {
//                maxY++;
//            }
//        }
//        leftUp = new Piece(minX, minY);
//        rightDown = new Piece(maxX, maxY);
//    }
//
//    //TODO: Perfect the evaluate algorithm, check algorithm
//    private int weight(int value) {
//        int ret = value;
//        switch (value) {
//            case 1:
//                ret = 0;
//                break;
//            case 2:
//                ret *= 2;
//                break;
//            case 3:
//                ret *= 4;
//                break;
//            case 4:
//                ret *= 6;
//                break;
//            case 5:
//                ret *= 8;
//                break;
//        }
//        return ret;
//    }
//
//    /**
//     * To mark every available position in the search district.
//     * The following situations can get points:
//     * a.three
//     * b.four
//     * c.live-two
//     * d.obstruct more than two
//     */
//    public void evaluate() {
//        choices.clear();
//        ArrayList<Piece> alternate = new ArrayList<>();
//
//        for (int i = leftUp.getX(); i <= rightDown.getX(); ++i) {
//            for (int j = leftUp.getY(); j <= rightDown.getY(); ++j) {
//                if (board.canPlace(i, j)) {
//                    int grade = 0;
//                    Piece piece = new Piece(i, j, color);
//                    board.checkX(piece);
//                    board.checkY(piece);
//                    board.checkZL(piece);
//                    board.checkZR(piece);
//
//                    // Attack
//                    int x1 = piece.getStatus().getCountX1();
//                    int y1 = piece.getStatus().getCountY1();
//                    int zl1 = piece.getStatus().getCountZL1();
//                    int zr1 = piece.getStatus().getCountZR1();
//                    int extra1 = piece.getStatus().isObstruct1() ? 0 : 10;
//                    grade += weight(x1) + weight(y1) + weight(zl1) + weight(zr1) + extra1;
//
//                    // Defense: obstruct the player's piece.
//                    int x2 = piece.getStatus().getCountX2();
//                    int y2 = piece.getStatus().getCountY2();
//                    int zl2 = piece.getStatus().getCountZL2();
//                    int zr2 = piece.getStatus().getCountZR2();
//                    int extra2 = piece.getStatus().isObstruct2() ? 0 : 10;
//                    grade += weight(x2) + weight(y2) + weight(zl2) + weight(zr2) + extra2;
//
//                    /**
//                     * If the position's grade greater than 0,
//                     * then add it into choices ArrayList.
//                     * Else add it into alternate ArrayList.
//                     */
//                    piece.setGrade(grade);
//                    if (grade > 0) {
//                        choices.add(piece);
//                    } else {
//                        alternate.add(piece);
//                    }
//                }
//            }
//        }
//
//        /**
//         * If there is no choices, append alternate to choices
//         * to ensure choices won't be empty.
//         */
//        if (choices.size() == 0) {
//            choices.addAll(alternate);
//        }
//    }
//
//    /**
//     * Sort the choices ArrayList and choose the position with the highest
//     * grade from choices to place AI's piece.
//     * If there are more than one positions, use a random number to decide.
//     */
//    public Piece getChoice() {
//        choices.sort(new SortByGrade());
//        int maxGrade = choices.get(0).getGrade();
//        int count = 0;
//
//        // Calculate the number of choices with the maxGrade.
//        for (int i = 0; i < choices.size(); ++i) {
//            if (choices.get(i).getGrade() == maxGrade) {
//                count++;
//            } else {
//                break;
//            }
//        }
//
//        // Choose one position randomly.
//        Random random = new Random();
//        Piece choice = choices.get(random.nextInt(count));
//        return choice;
//    }
//
//    @Override
//    public void run() {
//        while (!status.isOver()) {
//            if (!status.isStallAI() && status.isBlackTurn() == color) {
//                if (status.getHasPutSize() == 0) {
//                    Piece newPiece = new Piece(7, 7, color);
//                    board.place(newPiece);
//                    status.addPiece(newPiece);
//                    status.updateTurn();
//                } else {
//                    calBorder();
//                    evaluate();
//                    Piece newPiece = getChoice();
//                    board.place(newPiece);
//                    status.addPiece(newPiece);
//                    status.updateTurn();
//                    if (board.checkFive(newPiece)) {
//                        status.setIsOver(true);
//                    }
//                    newPiece.showStatus();
//                    try {
//                        sleep(300);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//}
//
//class SortByGrade implements Comparator {
//    public int compare(Object o1, Object o2) {
//        Piece p1 = (Piece) o1;
//        Piece p2 = (Piece) o2;
//        if (p1.getGrade() > p2.getGrade()) {
//            return -1;
//        } else if (p1.getGrade() == p2.getGrade()) {
//            return 0;
//        }
//        return 1;
//    }
//}