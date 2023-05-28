import java.util.Scanner;
import java.lang.Math;

public class FloydAlgorithm {
    static Scanner scanner;
    static int n;
    static int[][] weights;
    static int[][] distances;
    static int[][] path;
    static int answer;

    public static void main(String[] args) {
        getInputs();
        doFloydAlgorithm();
        System.out.println("Final distance array : ");
        printAnArray(distances);
        System.out.println("Final path array : ");
        printAnArray(path);
    }

    public static void getInputs() {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of your vertices : ");
        n = scanner.nextInt();
        weights = new int[n][n];
        distances = new int[n][n];
        path = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Enter the edge between " + (i + 1) + " and " + (j + 1)
                        + " also enter -1 when there is no edge between them : ");
                weights[i][j] = scanner.nextInt();
                if (weights[i][j] == -1) {
                    weights[i][j] = Integer.MAX_VALUE;
                }

            }
        }
    }

    public static void printAnArray(int[][] anArray) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (anArray[i][j] != Integer.MAX_VALUE) {
                    System.out.print(anArray[i][j] + " ");
                } else {
                    System.out.print("inf ");
                }
            }
            System.out.println("");
        }
    }

    public static void doFloydAlgorithm() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distances[i][j] = weights[i][j];
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && k != i && k != j) {
                        boolean check = acceptKOrRejectK(k, i, j);
                        distances[i][j] = answer;
                        if (check == true) {
                            path[i][j] = k + 1;
                        }
                    }
                }
            }
        }

    }

    public static boolean acceptKOrRejectK(int k, int i, int j) {
        int first = distances[i][j];
        int second;
        if (distances[i][k] == Integer.MAX_VALUE || distances[k][j] == Integer.MAX_VALUE) {
            second = Integer.MAX_VALUE;
        } else {
            second = distances[i][k] + distances[k][j];
        }
        answer = Math.min(first, second);
        if (first < second) {
            return false;
        } else {
            return true;
        }
    }
}