package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main extends Application {
    static File code;
    static Scanner scanner;

    static ArrayList<String> lines = new ArrayList<String>(); // Lines of the given code
    static ArrayList<String> identifiers = new ArrayList<String>();
    static ArrayList<String> keywords = new ArrayList<String>();
    static ArrayList<String> operators = new ArrayList<String>();
    static ArrayList<String> separators = new ArrayList<String>();
    static ArrayList<String> literals = new ArrayList<String>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lexical Analysis");

        GridPane gridPane = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        Text identifiersText = new Text("IDENTIFIERS");
        Text keywordsText = new Text("KEYWORDS");
        Text operatorsText = new Text("OPERATORS");
        Text separatorsText = new Text("SEPARATORS");
        Text literalsText = new Text("LITERALS");

        gridPane.add(identifiersText, 1, 0);
        gridPane.add(keywordsText, 2, 0);
        gridPane.add(operatorsText, 3, 0);
        gridPane.add(separatorsText, 4, 0);
        gridPane.add(literalsText, 5, 0);

        for(int i = 0 ; i < identifiers.size() ; i++) {
            Text identifierText = new Text(identifiers.get(i));
            gridPane.add(identifierText, 1, i + 1);
        }
        for(int i = 0 ; i < keywords.size() ; i++){
            Text text = new Text(keywords.get(i));
            gridPane.add(text , 2 , i + 1);
        }
        for(int i = 0 ; i < operators.size() ; i++){
            Text text = new Text(operators.get(i));
            gridPane.add(text , 3 , i + 1);
        }
        for(int i = 0 ; i < separators.size() ; i++){
            Text text = new Text(separators.get(i));
            gridPane.add(text , 4 , i + 1);
        }
        for(int i = 0 ; i < literals.size() ; i++){
            Text text = new Text(literals.get(i));
            gridPane.add(text , 5 , i + 1);
        }

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(scrollPane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        getLines(); // first we will get lines of the given code.
        tokenizer();
        launch(args);
    }

    public static void tokenizer() {
        // Then we will delete every white spaces inside the code and give anything else
        // to a function -> isToken
        for (int i = 0; i < lines.size(); i++) {
            StringTokenizer st = new StringTokenizer(lines.get(i));
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                boolean answer = isToken(token);
                if (!answer) {
                    // this is when we get a error on lexical analysis.
                    System.out.println("An Error occured while checking line " + (i + 1) + " and token " + (token));
                    return;
                }
            }
        }
    }

    // A function for reading the the given code and writing the lines on a global
    // static ArrayList
    public static void getLines() {

        try {
            code = new File("C:\\Users\\amirr\\Desktop\\Lexical_(Graphics)\\src\\sample\\Code.txt");
            scanner = new Scanner(code);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // to connect the tokens to their types or finding an error on lexical check for
    // each string.
    public static boolean isToken(String string) {
        /* SEPEHR */

        // Check if a given string is token or not consider that the given string
        // doesn't have any white spaces but it may have multiple tokens
        // Example : this is a possible string for this function "(String[]" that
        // contains {"(" , "String" , "[" , "]"}
        // Find these tokens and save them in a static arraylist
        // If the string is not token at all return false and the program will stop
        // there.

        // abnormal:
        boolean flag = false;
        ArrayList<String> list = checkComplexString(string);
        if (list.size() > 0) {
            for (String s : list) {
                if (s.length() == 0)
                    continue;
                flag = checkTokenType(s);
                if (!flag)
                    return false;
            }
        }
        // normal :
        if (checkTokenType(string))
            return true;

        return flag;// Just for Compiling correctly.
    }

    public static boolean checkTokenType(String string) {
        if (Token.isKeyword(string)) {
            keywords.add(string);
            return true;
        } else if (Token.isIdentifier(string)) {
            identifiers.add(string);
            return true;
        } else if (Token.isLiteral(string)) {
            literals.add(string);
            return true;
        } else if (Token.isOperator(string)) {
            operators.add(string);
            return true;
        } else if (Token.isSeparator(string)) {
            separators.add(string);
            return true;
        }
        return false;
    }

    private static ArrayList<String> checkComplexString(String string) {
        ArrayList<String> list = new ArrayList<String>();
        char[] chars = string.toCharArray();
        String identifier = "";
        String literal = "";
        int startLiteral = 0;
        int start = 0;
        boolean literalCheck = false;
        for (int i = 0; i < chars.length; i++) {
            if (!literalCheck) {
                if (isSeparator(chars[i])) {
                    identifier = fixSubString(string, start, i);
                    start = i + 1;
                    list.add(identifier);
                    list.add(Character.toString(chars[i]));
                }
            }
            if (chars[i] == '"' && literalCheck) {
                literalCheck = false;
                literal = fixSubString(string, startLiteral, i + 1);
                start = i + 1;
                list.add(literal);
            } else if (chars[i] == '"' && !literalCheck) {
                startLiteral = i;
                literalCheck = true;
            }

        }

        return list;
    }

    private static boolean isSeparator(char c) {
        char[] separator = {'.', '{', '(', '[', '<', '>', ']', ')', '}', ';'};
        for (char s : separator) {
            if (c == s)
                return true;
        }
        return false;
    }

    private static String fixSubString(String string, int startIndex, int endIndex) {
        return string.substring(startIndex, endIndex);
    }
}
