package ir.ac.kntu;

import java.util.ArrayList;

public class PDA {

    private static ArrayList<String> states = new ArrayList<String>();
    private static ArrayList<String> alphabetsOfInputs = new ArrayList<String>();
    private static ArrayList<String> alphabetsOfStack = new ArrayList<String>();
    private static ArrayList<TransformFunction> transformFunction = new ArrayList<TransformFunction>();
    private String startingState;
    private static ArrayList<String> finalStates = new ArrayList<String>();

    public static boolean addState(String theState) {
        if (!states.contains(theState)) {
            states.add(theState);
            return true;
        }
        return false;
    }

    public static boolean addAlphabetOfInput(String theAlphabet) {
        if (!alphabetsOfInputs.contains(theAlphabet)) {
            alphabetsOfInputs.add(theAlphabet);
            return true;
        }
        return false;
    }

    public static boolean addAlphabetOfStack(String theAlphabet) {
        if (!alphabetsOfStack.contains(theAlphabet)) {
            alphabetsOfStack.add(theAlphabet);
            return true;
        }
        return false;
    }

    public static boolean addFunction(TransformFunction theFunction) {
        if (!transformFunction.contains(theFunction)) {
            transformFunction.add(theFunction);
            return true;
        }
        return false;
    }

    public void isStartingState(String theStartingState) {
        this.startingState = theStartingState;
    }

    public static boolean addFinalState(String theState) {
        if (!finalStates.contains(theState)) {
            finalStates.add(theState);
            return true;
        }
        return false;
    }

    public void printAll() {
        System.out.println("States of PDA are: ");
        for (String state : states) {
            System.out.println(state);
        }
        System.out.println("Starting state is : " + startingState);
        for (String state : finalStates) {
            System.out.println("Final state is : " + state);
        }
        System.out.println("Alphabet of stack and inputs are : ");
        for (String symbol : alphabetsOfInputs){
            System.out.println(symbol);
        }
        System.out.println("Functions are: ");
        for (TransformFunction function : transformFunction) {
            function.printFunction();
        }
    }
}