package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


public class Main extends Application {
    static Scanner scanner;
    static int numberOfVertices;
    static ArrayList<Edge> edges;
    static ArrayList<Subset> subsets;
    static ArrayList<Edge> usedEdges;
    static Vertice[] vertices;
    static ArrayList<Line> lines;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Kruskal Algorithm!");

        Group group = new Group();

        lines = new ArrayList<Line>();
        findLines();
        for (Line line : lines) {
            group.getChildren().add(line);
        }

        for (int i = 0; i < numberOfVertices; i++) {
            Circle circle = new Circle(vertices[i].getX(), vertices[i].getY(), 15);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            group.getChildren().add(circle);
        }

        for (int i = 0; i < numberOfVertices; i++) {
            Text text = new Text(vertices[i].getX() - 3, vertices[i].getY() + 3, Integer.toString(i + 1));
            group.getChildren().add(text);
        }

        Scene scene = new Scene(group, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void findLines() {
        for (Edge edge : usedEdges) {
            Line line = new Line(vertices[edge.getFirstVertice()].getX(), vertices[edge.getFirstVertice()].getY(), vertices[edge.getSecondVertice()].getX(), vertices[edge.getSecondVertice()].getY());
            lines.add(line);
        }
    }


    public static void main(String[] args) {
        getInputs();
        doKruskalAlgorithm();
        launch(args);
    }

    public static void getInputs() {
        scanner = new Scanner(System.in);
        edges = new ArrayList<Edge>();
        System.out.println("Enter the number of your vertices: ");
        numberOfVertices = scanner.nextInt();
        vertices = new Vertice[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = i + 1; j < numberOfVertices; j++) {
                System.out.println("Enter the weight of the edge between " + (i + 1) + " " + (j + 1) + " : ");
                int weight = scanner.nextInt();
                if (weight == -1) {
                    weight = Integer.MAX_VALUE;
                }
                Edge edge = new Edge(i, j, weight);
                edges.add(edge);
            }
        }
        double x = 100;
        double y = 100;
        int temp = numberOfVertices;
        if(temp % 4 >= 2){
            temp = temp / 4 + 1;
        }
        else{
            temp = temp / 4;
        }
        for (int j = 0; j < temp; j++) {
            x = x + 100;
            Vertice vertice = new Vertice(x, y);
            vertices[j] = vertice;
        }
        temp = numberOfVertices;
        if(temp % 4 >= 2){
            temp = temp / 4 + 1;
        }
        else{
            temp = temp / 4;
        }
        x = x + 100;
        for (int j = temp; j < temp * 2; j++) {
            y = y + 100;
            Vertice vertice = new Vertice(x, y);
            vertices[j] = vertice;
        }
        temp = numberOfVertices;
        if(temp % 4 >= 2){
            temp = temp / 4 + 1;
        }
        else{
            temp = temp / 4;
        }
        y = y + 100;
        for (int j = temp * 2; j < (temp * 3); j++) {
            x = x - 100;
            Vertice vertice = new Vertice(x, y);
            vertices[j] = vertice;
        }
        temp = numberOfVertices;
        if(temp % 4 >= 2){
            temp = temp / 4 + 1;
        }
        else{
            temp = temp / 4;
        }
        x = x - 100;
        for (int j = (temp * 3); j < numberOfVertices; j++) {
            y = y - 100;
            Vertice vertice = new Vertice(x, y);
            vertices[j] = vertice;
        }
        WeightCompare weightCompare = new WeightCompare();
        Collections.sort(edges, weightCompare);
        scanner.close();
    }

    public static void doKruskalAlgorithm() {
        subsets = new ArrayList<Subset>();
        usedEdges = new ArrayList<Edge>();
        for (int i = 0; i < numberOfVertices; i++) {
            Subset subset = new Subset(1, i, numberOfVertices);
            subsets.add(subset);
        }
        int usedEdges1 = 0;
        int count = 0;
        while (usedEdges1 < numberOfVertices - 1 && count < numberOfVertices * numberOfVertices) {
            Edge edge = edges.get(count);
            if (canItConnect(edge)) {
                int firstSubset = findSubsetOfthisVertice(edge.getFirstVertice());
                int secondSubset = findSubsetOfthisVertice(edge.getSecondVertice());
                connectSubsets(firstSubset, secondSubset, edge);
                usedEdges.add(edge);
                usedEdges1 = usedEdges1 + 1;
            }
            count = count + 1;
        }
    }

    public static boolean canItConnect(Edge edge) {
        int firstVertice = edge.getFirstVertice();
        int secondVertice = edge.getSecondVertice();
        for (Subset subset : subsets) {
            if (subset.containAVertice(firstVertice) && subset.containAVertice(secondVertice)) {
                return false;
            }
        }
        return true;
    }

    public static int findSubsetOfthisVertice(int vertice) {
        for (Subset subset : subsets) {
            if (subset.containAVertice(vertice)) {
                return subsets.indexOf(subset);
            }
        }
        return -1;
    }

    public static void connectSubsets(int firstSubset, int secondSubset, Edge edge) {
        Subset second = subsets.get(secondSubset);
        Subset first = subsets.get(firstSubset);
        int countOfVertices1 = first.getCountOfVertices();
        int countOfVertices2 = second.getCountOfVertices();
        first.setCountOfVertices(countOfVertices1 + countOfVertices2);
        int[] vertices1 = first.getVertices();
        int[] vertices2 = second.getVertices();
        int[] newVertices = new int[numberOfVertices];
        for (int i = 0; i < countOfVertices1; i++) {
            newVertices[i] = vertices1[i];
        }
        for (int i = 0; i < countOfVertices2; i++) {
            newVertices[i + countOfVertices1] = vertices2[i];
        }
        first.setVertices(newVertices);
        subsets.set(firstSubset, first);
        subsets.remove(secondSubset);
    }
}
