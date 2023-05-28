package ir.ac.kntu;

import java.util.ArrayList;

public class CFG {
    private static ArrayList<String> variables = new ArrayList<String>();
    private static ArrayList<String> alphabets = new ArrayList<String>();
    private static ArrayList<Rule> rules = new ArrayList<Rule>();
    private String StartingVariable;

    public static boolean addVariable(String theVariable) {
        if (!variables.contains(theVariable)) {
            variables.add(theVariable);
            return true;
        }
        return false;
    }

    public static boolean addAlphabet(String theAlphabet) {
        if (!alphabets.contains(theAlphabet)) {
            alphabets.add(theAlphabet);
            return true;
        }
        return false;
    }

    public static boolean addRule(Rule theRule) {
        if (!rules.contains(theRule)) {
            rules.add(theRule);
            return true;
        }
        return false;
    }

    public void isStartingVariable(String theStartingVariable) {
        this.StartingVariable = theStartingVariable;
    }

    public static ArrayList<Rule> getRules() {
        ArrayList<Rule> returningRules = new ArrayList<Rule>();
        for (Rule rule : rules) {
            returningRules.add(rule);
        }
        return returningRules;
    }

    public static ArrayList<String> getAlphabet() {
        ArrayList<String> returningAlphabet = new ArrayList<String>();
        for (String symbol : alphabets) {
            returningAlphabet.add(symbol);
        }
        return returningAlphabet;
    }

    public static ArrayList<String> getVariables() {
        ArrayList<String> returningVariables = new ArrayList<String>();
        for (String variable : variables) {
            returningVariables.add(variable);
        }
        return returningVariables;
    }
}