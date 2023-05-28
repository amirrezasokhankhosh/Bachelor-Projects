package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;

public class Main extends Application {
    static Scanner scanner;
    static int n;
    static Vertice[] vertices;
    static double[][] diameters;
    static int[][] path;
    static ArrayList<Line> lines;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Triangle Project!");

        Group group = new Group();

        double points[] = new double[(2 * n)];
        points = putVerticesToPoints(points);
        Polygon polygan = new Polygon(points);
        polygan.setStroke(Color.BLACK);
        polygan.setFill(Color.WHITE);
        group.getChildren().add(polygan);

        lines = new ArrayList<Line>();
        findLines(0, n - 1);
        for (Line line: lines){
            group.getChildren().add(line);
        }

        for (int i = 0 ; i < n ; i++){
            Circle circle = new Circle(vertices[i].getY() , vertices[i].getX() , 15);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            group.getChildren().add(circle);
        }

        for (int i = 0 ; i < n ; i++){
            Text text = new Text(vertices[i].getY() - 3 , vertices[i].getX() + 3 , Integer.toString(i + 1));
            group.getChildren().add(text);
        }

        Scene scene = new Scene(group, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public double[] putVerticesToPoints(double[] points) {
        for (int i = 0; i < n; i++) {
            points[(2 * i)] = vertices[i].getY();
            points[(2 * i) + 1] = vertices[i].getX();
        }
        return points;
    }

    public void findLines(int a, int b) {
        if (path[a][b] != 0) {
            Line line1 = new Line(vertices[a].getY(), vertices[a].getX(), vertices[path[a][b]].getY(), vertices[path[a][b]].getX());
            Line line2 = new Line(vertices[path[a][b]].getY(), vertices[path[a][b]].getX() , vertices[b].getY(), vertices[b].getX());
            lines.add(line1);
            lines.add(line2);
            findLines(a , path[a][b]);
            findLines(path[a][b] , b);
        }

    }

    public static void main(String[] args) {
        getInputs();
        findTheBestWay();
        launch(args);
    }

    public static void getInputs() {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of your vertices : ");
        n = scanner.nextInt();
        vertices = new Vertice[n];
        for (int i = 0; i < n; i++) {
            double x, y;
            System.out.println("Enter the x of your vertice : ");
            x = scanner.nextDouble();
            System.out.println("Enter the y of your vertice : ");
            y = scanner.nextDouble();
            Vertice vertice = new Vertice(x, y);
            vertices[i] = vertice;
        }
    }

    public static void findTheBestWay() {
        diameters = new double[n][n];
        path = new int[n][n];
        for (int i = 0; i < n; i++) {
            int tempI = 0;
            for (int j = i; j < n; j++) {
                if (j - tempI > 2) {
                    double min = Double.MAX_VALUE;
                    for (int k = tempI + 1; k < j; k++) {
                        if (min > (diameters[tempI][k] + diameters[k][j] + weightOfDiameter(tempI, k)
                                + weightOfDiameter(k, j))) {
                            min = (diameters[tempI][k] + diameters[k][j] + weightOfDiameter(tempI, k)
                                    + weightOfDiameter(k, j));
                            path[tempI][j] = k;
                        }
                    }
                    diameters[tempI][j] = min;
                }
                tempI = tempI + 1;
            }
        }
    }

    public static double weightOfDiameter(int a, int b) {
        if (Math.abs(a - b) > 1) {
            double x1 = vertices[a].getX();
            double x2 = vertices[b].getX();
            double y1 = vertices[a].getY();
            double y2 = vertices[b].getY();
            double state1 = Math.pow(x2 - x1, 2);
            double state2 = Math.pow(y2 - y1, 2);
            return (Math.sqrt(state1 + state2));
        } else {
            return 0;
        }
    }

}
