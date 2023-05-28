import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static int countOfVertices;
    static int[][] graph;
    static int[] coloredVertices;
    static int countOfColors;
    static ArrayList<Integer> colors;

    public static void main(String[] args) {
        getInputs();
        callAlgorithm();
    }

    public static void getInputs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of your vertices : ");
        countOfVertices = scanner.nextInt();
        graph = new int[countOfVertices][countOfVertices];
        coloredVertices = new int[countOfVertices];
        countOfColors = 1;
        colors = new ArrayList<Integer>();
        for (int i = 0; i < countOfVertices; i++) {
            coloredVertices[i] = -1; // -1 means its not colored yet.
        }
        for (int i = 0; i < countOfVertices; i++) {
            for (int j = i + 1; j < countOfVertices; j++) {
                System.out.println(
                        "If you have edge between vertices " + (i + 1) + " and " + (j + 1) + " , Enter 1 else 0 : ");
                graph[j][i] = graph[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
    }

    public static void callAlgorithm() {
        while (countOfColors <= countOfVertices) {
            updateColors();
            boolean ans = coloringAlgorithm(0);
            if (ans == true) {
                break;
            }
            countOfColors = countOfColors + 1;
        }
    }

    public static boolean coloringAlgorithm(int vertice) {
        updateColors();
        ArrayList<Integer> possibleColors = getPossibleColors(vertice);
        if (possibleColors.size() != 0) {
            if (vertice == countOfVertices - 1) {
                coloredVertices[vertice] = possibleColors.get(0);
                printColoredVertices();
                return true;
            } else {
                while (possibleColors.size() != 0) {
                    int color = possibleColors.remove(0);
                    coloredVertices[vertice] = color;
                    boolean ans = coloringAlgorithm(vertice + 1);
                    if (ans == true) {
                        return true;
                    }
                }
                coloredVertices[vertice] = -1;
            }
        }
        return false;
    }

    public static void updateColors() {
        colors.clear();
        for (int i = 0; i < countOfColors; i++) {
            colors.add(i);
        }
    }

    public static ArrayList<Integer> getPossibleColors(int vertice) {
        ArrayList<Integer> possibleColors = new ArrayList<Integer>();
        possibleColors = colors;
        for (int i = 0; i < countOfVertices; i++) {
            if (possibleColors.size() != 0) {
                if (i != vertice) {
                    if (graph[i][vertice] == 1) {
                        if (coloredVertices[i] != -1) {
                            int index = possibleColors.indexOf(coloredVertices[i]);
                            if (index != -1) {
                                possibleColors.remove(index);
                            }
                        }
                    }
                }
            }
        }
        return possibleColors;
    }

    public static void printColoredVertices() {
        for (int i = 0; i < countOfVertices; i++) {
            System.out.println("Vertice " + (i + 1) + " - Color : " + (coloredVertices[i] + 1));
        }
    }
}