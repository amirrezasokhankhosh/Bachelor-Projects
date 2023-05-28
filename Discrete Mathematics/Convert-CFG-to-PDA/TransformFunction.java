package ir.ac.kntu;

public class TransformFunction {

    private String presentState;
    private String nextState;
    private String symbol;
    private String presentSymbolOnStack;
    private String nextSymbolOnStack;

    public TransformFunction(String presentState, String nextState, String symbol, String presentSymbolOnStack,
            String nextSymbolOnStack) {
        this.presentState = presentState;
        this.nextState = nextState;
        this.symbol = symbol;
        this.presentSymbolOnStack = presentSymbolOnStack;
        this.nextSymbolOnStack = nextSymbolOnStack;
    }

    public void printFunction() {
        System.out.println(presentState + "---(" + symbol + "/" + presentSymbolOnStack + "->" + nextSymbolOnStack
                + ")--->" + nextState);
    }
}