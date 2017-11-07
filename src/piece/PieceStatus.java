package piece;

public class PieceStatus {
    private int countX1 = 1;
    private int countX2 = 1;

    private int countY1 = 1;
    private int countY2 = 1;

    private int countZL1 = 1;
    private int countZL2 = 1;

    private int countZR1 = 1;
    private int countZR2 = 1;

    private boolean obstruct1 = false;
    private boolean obstruct2 = false;


    public int getCountX1() {
        return countX1;
    }

    public void setCountX1(int countX1) {
        this.countX1 = countX1;
    }

    public int getCountX2() {
        return countX2;
    }

    public void setCountX2(int countX2) {
        this.countX2 = countX2;
    }

    public int getCountY1() {
        return countY1;
    }

    public void setCountY1(int countY1) {
        this.countY1 = countY1;
    }

    public int getCountY2() {
        return countY2;
    }

    public void setCountY2(int countY2) {
        this.countY2 = countY2;
    }

    public int getCountZL1() {
        return countZL1;
    }

    public void setCountZL1(int countZL1) {
        this.countZL1 = countZL1;
    }

    public int getCountZL2() {
        return countZL2;
    }

    public void setCountZL2(int countZL2) {
        this.countZL2 = countZL2;
    }

    public int getCountZR1() {
        return countZR1;
    }

    public void setCountZR1(int countZR1) {
        this.countZR1 = countZR1;
    }

    public int getCountZR2() {
        return countZR2;
    }

    public void setCountZR2(int countZR2) {
        this.countZR2 = countZR2;
    }

    public boolean isObstruct1() {
        return obstruct1;
    }

    public void setObstruct1(boolean obstruct1) {
        this.obstruct1 = obstruct1;
    }

    public boolean isObstruct2() {
        return obstruct2;
    }

    public void setObstruct2(boolean obstruct2) {
        this.obstruct2 = obstruct2;
    }
}
