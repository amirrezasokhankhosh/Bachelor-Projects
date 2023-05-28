package ir.ac.kntu;

import java.util.Scanner;

import ir.ac.kntu.CFG;
import ir.ac.kntu.TransformFunction;

import java.util.ArrayList;

public class Main {

    public static void main(String[] argv) {
        CFG cfg = new CFG();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Oh Hello mate!");
        System.out.println("First of all enter your CFG please!");
        getCFG(scanner, cfg);
        givePDA(scanner, cfg);
    }

    public static void getCFG(Scanner scanner, CFG cfg) {
        getVariables(scanner, cfg);
        getAlphabet(scanner, cfg);
        getRules(scanner, cfg);
        isStartingVariable(scanner, cfg);
    }

    public static void getVariables(Scanner scanner, CFG cfg) {
        String variable = "";
        System.out.println("Enter your variables and enter done when you are done.");
        while (1 == 1) {
            variable = scanner.next();
            if (!variable.equals("done")) {
                if (!cfg.addVariable(variable)) {
                    System.out.println("Already exist.");
                }
            } else {
                break;
            }
        }
    }

    public static void getAlphabet(Scanner scanner, CFG cfg) {
        String alphabet = "";
        System.out.println("Enter your alphabets and enter done when you are done.");
        while (1 == 1) {
            alphabet = scanner.next();
            if (!alphabet.equals("done")) {
                if (!cfg.addAlphabet(alphabet)) {
                    System.out.println("Already exist.");
                }
            } else {
                break;
            }
        }
    }

    public static void getRules(Scanner scanner, CFG cfg) {
        String leftSide, rightSide;
        leftSide = rightSide = "";
        System.out.println("Enter your rules and enter done when you are done.");
        while (1 == 1) {
            System.out.println("leftSide: ");
            leftSide = scanner.next();
            if (!leftSide.equals("done")) {
                System.out.println("rightSide: ");
                rightSide = scanner.next();
                Rule rule = new Rule(leftSide, rightSide);
                if (!cfg.addRule(rule)) {
                    System.out.println("Already exist.");
                }
            } else {
                break;
            }
        }
    }

    public static void isStartingVariable(Scanner scanner, CFG cfg) {
        String startingVariable;
        System.out.println("Enter the starting variable:");
        startingVariable = scanner.next();
        cfg.isStartingVariable(startingVariable);
    }

    public static void givePDA(Scanner scanner, CFG cfg) {
        PDA pda = new PDA();
        String q_start = "Q_start";
        String q_loop = "Q_loop";
        String q_accept = "Q_accept";
        addQ_start_loop_accept(pda, q_start, q_loop, q_accept);
        addFunctionToTransformFunction(pda, q_start, q_loop, "Ep", "Ep", "S$");
        addFunctionToTransformFunction(pda, q_loop, q_accept, "Ep", "$", "Ep");
        otherFunctionsAndStates(pda, cfg, q_start, q_loop, q_accept);
        printPDA(pda);
    }

    public static void addQ_start_loop_accept(PDA pda, String q_start, String q_loop, String q_accept) {
        pda.addState(q_start);
        pda.addState(q_loop);
        pda.addState(q_accept);
        pda.isStartingState(q_start);
        pda.addFinalState(q_accept);
    }

    public static void addFunctionToTransformFunction(PDA pda, String presentState, String nextState, String symbol,
            String presentSymbolOnStack, String nextSymbolOnStack) {
        TransformFunction transformFunction = new TransformFunction(presentState, nextState, symbol,
                presentSymbolOnStack, nextSymbolOnStack);
        pda.addFunction(transformFunction);
    }

    public static void otherFunctionsAndStates(PDA pda, CFG cfg, String q_start, String q_loop, String q_accept) {
        ArrayList<Rule> rules = cfg.getRules();
        ArrayList<String> alphabets = cfg.getAlphabet();
        ArrayList<String> variables = cfg.getVariables();
        int number = 1;
        for (Rule rule : rules) {
            number = addOthers(rule, pda, q_start, q_loop, q_accept, number);
        }

        for (String symbol : alphabets) {
            addFunctionToTransformFunction(pda, q_loop, q_loop, symbol, symbol, "Ep");
            pda.addAlphabetOfStack(symbol);
            pda.addAlphabetOfInput(symbol);
        }
        for (String variable : variables){
            pda.addAlphabetOfInput(variable);
            pda.addAlphabetOfStack(variable);
        }
    }

    public static int addOthers(Rule rule, PDA pda, String q_start, String q_loop, String q_accept, int number) {
        String q = "Q_";
        String Ep = "#";
        String leftSide = rule.getLeftSide();
        String rightSide = rule.getRightSide();
        if (rightSide.equals("Ep")) {
            rightSide = "#";
        }
        int length = rightSide.length();
        for (int i = 0; i < length - 1; i++) {
            leftSide = Ep.concat(leftSide);
        }

        if (length != 1) {
            for (int i = 0; i < length; i++) {
                String presentSymbol = Character.toString(leftSide.charAt(length - (1 + i)));
                if (presentSymbol.equals("#")) {
                    presentSymbol = "Ep";
                }
                if (i == 0) {
                    q = q.concat(Integer.toString(number));
                    pda.addState(q);
                    addFunctionToTransformFunction(pda, q_loop, q, "Ep", presentSymbol,
                            Character.toString(rightSide.charAt(length - (i + 1))));
                    number++;
                } else if (i == length - 1) {
                    addFunctionToTransformFunction(pda, q, q_loop, "Ep", presentSymbol,
                            Character.toString(rightSide.charAt(length - (i + 1))));
                } else {
                    String temp = q;
                    q = q.concat(Integer.toString(number));
                    pda.addState(q);
                    addFunctionToTransformFunction(pda, temp, q, "Ep", presentSymbol,
                            Character.toString(rightSide.charAt(length - (i + 1))));
                    number++;
                }
            }
        } else {
            if (rightSide.equals("#")) {
                rightSide = "Ep";
            }
            addFunctionToTransformFunction(pda, q_loop, q_loop, "Ep", leftSide, rightSide);
        }
        return number;
    }

    public static void printPDA(PDA pda) {
        pda.printAll();
    }
}