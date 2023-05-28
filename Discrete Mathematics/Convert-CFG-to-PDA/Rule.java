package ir.ac.kntu;

public class Rule {

    private String leftSide;
    private String rightSide;

    public Rule(String leftSide, String rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public String getRightSide() {
        return rightSide;
    }
}