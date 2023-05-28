package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {
    static Scanner scanner = new Scanner(System.in);
    static int lenght = scanner.nextInt();
    static int[][] map = new int[lenght][lenght];

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mosaics!");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                switch (map[i][j]) {
                    case 1: {
                        Rectangle rectangle = new Rectangle(30 , 30 , Color.RED);
                        gridPane.add(rectangle , j , i);
                        break;
                    }
                    case 2: {
                        Rectangle rectangle = new Rectangle(30 , 30 , Color.BLUE);
                        gridPane.add(rectangle , j , i);
                        break;
                    }
                    case 3: {
                        Rectangle rectangle = new Rectangle(30 , 30 , Color.GREEN);
                        gridPane.add(rectangle , j , i);
                        break;
                    }
                    case 4: {
                        Rectangle rectangle = new Rectangle(30 , 30 , Color.YELLOW);
                        gridPane.add(rectangle , j , i);
                        break;
                    }
                    case 5:{
                        Rectangle rectangle = new Rectangle(30 , 30 , Color.PURPLE);
                        gridPane.add(rectangle , j , i);
                        break;
                    }
                }
            }
        }

        primaryStage.setScene(new Scene(gridPane, 300, 275));
        primaryStage.show();
    }

    public static void buildMosaics(int lenghtOfSquare, int rowOfEmptySquare, int colOfEmptySquare, int beginOfRow,
                                    int beginOfCol) {
        if (lenghtOfSquare == 2) {
            if (rowOfEmptySquare == beginOfRow && colOfEmptySquare - beginOfCol == 1) {
                map[beginOfRow][beginOfCol] = map[beginOfRow + 1][beginOfCol] = map[beginOfRow + 1][beginOfCol + 1] = 1;
                return;
            } else if (rowOfEmptySquare - beginOfRow == 1 && colOfEmptySquare - beginOfCol == 1) {
                map[beginOfRow][beginOfCol] = map[beginOfRow + 1][beginOfCol] = map[beginOfRow][beginOfCol + 1] = 2;
                return;
            } else if (rowOfEmptySquare - beginOfRow == 1 && colOfEmptySquare == beginOfCol) {
                map[beginOfRow][beginOfCol] = map[beginOfRow][beginOfCol + 1] = map[beginOfRow + 1][beginOfCol + 1] = 3;
                return;
            } else {
                map[beginOfRow][beginOfCol
                        + 1] = map[beginOfRow + 1][beginOfCol] = map[beginOfRow + 1][beginOfCol + 1] = 4;
                return;
            }
        } else {
            int rowMiddle = beginOfRow + (lenghtOfSquare / 2);
            int colMiddle = beginOfCol + (lenghtOfSquare / 2);
            int rowEnd = beginOfRow + lenghtOfSquare - 1;
            int colEnd = beginOfCol + lenghtOfSquare - 1;
            // empty square is in middle of map
            if (rowOfEmptySquare - rowMiddle == -1 && colOfEmptySquare == colMiddle) {
                map[rowOfEmptySquare][colOfEmptySquare] = 5;
                map[rowOfEmptySquare][colOfEmptySquare - 1] = map[rowOfEmptySquare + 1][colOfEmptySquare
                        - 1] = map[rowOfEmptySquare + 1][colOfEmptySquare] = 1;
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle - 1, beginOfRow, beginOfCol);
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle, beginOfRow, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle, rowMiddle, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle - 1, rowMiddle, beginOfCol);
                return;
            }
            // empty square is in up right
            else if (rowOfEmptySquare == beginOfRow && colOfEmptySquare == colEnd) {
                map[rowMiddle - 1][colMiddle - 1] = map[rowMiddle][colMiddle - 1] = map[rowMiddle][colMiddle] = 1;
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle - 1, beginOfRow, beginOfCol);
                buildMosaics(lenghtOfSquare / 2, beginOfRow, colEnd, beginOfRow, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle, rowMiddle, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle - 1, rowMiddle, beginOfCol);
                return;
            }
            // empty square is in down right
            else if (rowOfEmptySquare == rowEnd && colOfEmptySquare == colEnd) {
                map[rowMiddle - 1][colMiddle - 1] = map[rowMiddle - 1][colMiddle] = map[rowMiddle][colMiddle - 1] = 2;
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle - 1, beginOfRow, beginOfCol);
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle, beginOfRow, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowEnd, colEnd, rowMiddle, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle - 1, rowMiddle, beginOfCol);
                return;
            }
            // empty square is in down left
            else if (rowOfEmptySquare == rowEnd && colOfEmptySquare == beginOfCol) {
                map[rowMiddle - 1][colMiddle - 1] = map[rowMiddle - 1][colMiddle] = map[rowMiddle][colMiddle] = 3;
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle - 1, beginOfRow, beginOfCol);
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle, beginOfRow, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle, rowMiddle, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowEnd, beginOfCol, rowMiddle, beginOfCol);
                return;
            }
            // empty square is in up left
            else if (rowOfEmptySquare == beginOfRow && colOfEmptySquare == beginOfCol) {
                map[rowMiddle - 1][colMiddle] = map[rowMiddle][colMiddle - 1] = map[rowMiddle][colMiddle] = 4;
                buildMosaics(lenghtOfSquare / 2, beginOfRow, beginOfCol, beginOfRow, beginOfCol);
                buildMosaics(lenghtOfSquare / 2, rowMiddle - 1, colMiddle, beginOfRow, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle, rowMiddle, colMiddle);
                buildMosaics(lenghtOfSquare / 2, rowMiddle, colMiddle - 1, rowMiddle, beginOfCol);
                return;
            }
        }
    }

    public static void main(String[] args) {
        buildMosaics(lenght, (lenght / 2) - 1, (lenght / 2), 0, 0);
        launch(args);
    }
}