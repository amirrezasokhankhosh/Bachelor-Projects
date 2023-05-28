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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main extends Application {
    static ArrayList<String> productions = new ArrayList<String>();
    static FirstAndFollow firstAndFollow;
    static ArrayList<Set> first;
    static ArrayList<Set> follow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Compute first and follow!");

        GridPane gridPane = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        Text firstText = new Text("FIRST");
        Text followText = new Text("FOLLOW");

        gridPane.add(firstText, 0, 0);

        int count = 1;
        for (Set set : first) {
            char name = set.setName;
            Text nameText = new Text(Character.toString(name));
            gridPane.add(nameText, 0, count);
            ArrayList<Character> list = set.setList;
            for (int i = 0; i < list.size(); i++) {
                Text element = new Text(Character.toString(list.get(i)));
                gridPane.add(element, i + 1, count);
            }
            count++;
        }

        gridPane.add(followText, 0, count);
        count++;
        for (Set set : follow) {
            char name = set.setName;
            Text nameText = new Text(Character.toString(name));
            gridPane.add(nameText, 0, count);
            ArrayList<Character> list = set.setList;
            for (int i = 0; i < list.size(); i++) {
                Text element = new Text(Character.toString(list.get(i)));
                gridPane.add(element, i + 1, count);
            }
            count++;
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
        runProgram();
        launch(args);
    }

    public static void runProgram() {
        int count = readFile();
        firstAndFollow = new FirstAndFollow(productions, count);
        firstAndFollow.runAdjusmentOnProduction();
        first = firstAndFollow.calculateFirst();
        follow = firstAndFollow.calculateFollow();
        follow = removeDuplicates(follow);
    }

    public static int readFile() {
        int count = 0;
        try {
            File myFile = new File("C:\\Users\\amirr\\Desktop\\Compute_(Graphics)\\src\\sample\\CFG.txt");
            Scanner scanner = new Scanner(myFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                productions.add(data);
                count++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return count;
    }

    public static ArrayList<Set> removeDuplicates(ArrayList<Set> list) {
        ArrayList<Set> newList = new ArrayList<Set>();

        // Traverse through the first list
        for (Set element : follow) {

            // If this element is not present in newList
            // then add it
            boolean contains = false;
            for (Set s : newList) {
                if (s.setName == element.setName) {
                    contains = true;
                }
            }

            if (!contains) {
                newList.add(element);
            }
        }

        return newList;
    }

}
