package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    static Scanner scanner;
    static int lenght;
    static int[][] board;
    static int countOfPuttedQueens = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("N Queens Problem!");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0 ; i < lenght ; i++){
            for (int j = 0 ; j < lenght ; j++){
                if(board[i][j] == 1){
                    Rectangle rectangle = new Rectangle(30 , 30 , Color.RED);
                    gridPane.add(rectangle , j , i);
                }else {
                    Rectangle rectangle = new Rectangle(30 , 30 , Color.LIGHTGRAY);
                    gridPane.add(rectangle , j , i);
                }
            }
        }
        primaryStage.setScene(new Scene(gridPane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        initiate();
        callTheAlgorithm();
        launch(args);
    }

    public static void initiate() {
        scanner = new Scanner(System.in);
        System.out.println("Enter the lenght of your board : ");
        lenght = scanner.nextInt();
        board = new int[lenght][lenght];
        scanner.close();
    }

    public static boolean findQueens(int rowOfQueen, int colOfQueen) {
        if (CanWePutThisQueen(rowOfQueen, colOfQueen)) {
            // put the queen
            board[rowOfQueen][colOfQueen] = 1;
            countOfPuttedQueens = countOfPuttedQueens + 1;
            if (countOfPuttedQueens == lenght) {
                return true;
            } else {
                boolean ans = false;
                for (int i = 0; i < lenght; i++) {
                    if (findQueens(rowOfQueen + 1, i)) {
                        ans = true;
                    }
                }
                if (ans == false) {
                    // remove the queen
                    board[rowOfQueen][colOfQueen] = 0;
                    countOfPuttedQueens = countOfPuttedQueens - 1;
                }
                return ans;
            }
        }
        return false;
    }

    public static boolean CanWePutThisQueen(int row, int col) {
        boolean ans = true;
        for (int i = 0; i < lenght; i++) {
            if (board[i][col] == 1 || board[row][i] == 1) {
                ans = false;
                break;
            }
            if (row - i >= 0 && col - i >= 0) {
                if (board[row - i][col - i] == 1) {
                    ans = false;
                    break;
                }
            }
            if (row - i >= 0 && col + i < lenght) {
                if (board[row - i][col + i] == 1) {
                    ans = false;
                    break;
                }
            }
            if (row + i < lenght && col - i >= 0) {
                if (board[row + i][col - i] == 1) {
                    ans = false;
                    break;
                }
            }
            if (row + i < lenght && col + i < lenght) {

                if (board[row + i][col + i] == 1) {
                    ans = false;
                    break;
                }
            }
        }
        return ans;
    }

    public static void callTheAlgorithm() {
        for (int i = 0; i < lenght; i++) {
            if (findQueens(0, i)) {
                break;
            }
        }
    }
}
