import java.util.Scanner;

public class MulOfMatrices {
    static Scanner scanner;
    static int n;
    static int[] d;
    static int[][] m;
    static int[][] p;

    public static void main(String[] args) {
        getInputs();
        doMulOfMatrixesAlgorithm();
        printAnArray(m);
        printAnArray(p);
    }

    public static void getInputs() {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of matrixes : ");
        n = scanner.nextInt();
        d = new int[n + 1];
        m = new int[n][n];
        p = new int[n][n];
        for (int i = 0; i < n + 1; i++) {
            System.out.println("Enter the d[" + i + "] : ");
            d[i] = scanner.nextInt();
        }
    }

    public static void doMulOfMatrixesAlgorithm() {
        int i = 0;
        int j = 0;
        for (int count = 0; count < n; count++) {
            i = 0;
            j = count;
            while (i < n && j < n) {
                if (i == j) {
                    m[i][j] = 0;
                } else {
                    int min = Integer.MAX_VALUE;
                    int temp = 0;
                    for (int k = i; k <= (j - 1); k++) {
                        int phrase = m[i][k] + m[k + 1][j] + (d[i] * d[k + 1] * d[j + 1]);
                        min = Math.min(min, phrase);
                        if (min == phrase) {
                            temp = k;
                        }
                    }
                    m[i][j] = min;
                    p[i][j] = temp + 1;
                }
                i = i + 1;
                j = j + 1;
            }
        }
    }

    public static void printAnArray(int[][] anArray) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(anArray[i][j] + " ");
            }
            System.out.println("");
        }
    }
}