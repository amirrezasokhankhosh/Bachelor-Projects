import java.util.Scanner;
import java.util.ArrayList;

public class MergeSort {
    static Scanner scanner;
    static Integer[] theArray;
    static int n;
    static ArrayList<Integer[]> subsets;
    static Integer[] sortedArray;
    static int countOfElementsInSortedArray;

    public static void main(String[] args) {
        getInputs();
        doMergeSortAlgorithm();
        printSortedArray();
    }

    public static void getInputs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the lenght of your array : ");
        n = scanner.nextInt();
        theArray = new Integer[n];
        sortedArray = new Integer[n];
        countOfElementsInSortedArray = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the element of " + (i + 1));
            theArray[i] = scanner.nextInt();
        }
        subsets = new ArrayList<Integer[]>();
        scanner.close();
    }

    public static void doMergeSortAlgorithm() {
        findSubsets(theArray);
        mergeSortedSubsets();
    }

    public static void findSubsets(Integer[] anArray) {
        boolean ans = isItSorted(anArray);
        if (ans == true) {
            subsets.add(anArray);
        } else {
            int half = anArray.length / 2;
            Integer[] first = new Integer[half];
            Integer[] second = new Integer[anArray.length - half];
            for (int i = 0; i < half; i++) {
                first[i] = anArray[i];
            }
            for (int j = 0; j < anArray.length - half; j++) {
                second[j] = anArray[half + j];
            }
            findSubsets(first);
            findSubsets(second);
        }
    }

    public static boolean isItSorted(Integer anArray[]) {
        int lenght = anArray.length;
        for (int i = 1; i < lenght; i++) {
            if (anArray[i - 1] > anArray[i]) {
                return false;
            }
        }

        return true;
    }

    public static void mergeSortedSubsets() {
        for (Integer[] anArray : subsets) {
            mergeWithSortedArray(anArray);
        }
    }

    public static void mergeWithSortedArray(Integer[] anArray) {
        Integer[] savedArray = new Integer[n];
        for (int i = 0 ; i < countOfElementsInSortedArray ; i++){
            savedArray[i] = sortedArray[i];
        }
        int lenght = countOfElementsInSortedArray + anArray.length;
        int i = 0;
        int j = 0;
        for (int k = 0; k < lenght; k++) {
            if (anArray.length != i && countOfElementsInSortedArray != j) {
                if (anArray[i] < savedArray[j]) {
                    sortedArray[k] = anArray[i];
                    i = i + 1;
                } else {
                    sortedArray[k] = savedArray[j];
                    j = j + 1;
                }
            } else {
                if (anArray.length == i) {
                    sortedArray[k] = savedArray[j];
                    j = j + 1;
                } else if (countOfElementsInSortedArray == j) {
                    sortedArray[k] = anArray[i];
                    i = i + 1;
                }
            }
        }
        countOfElementsInSortedArray = lenght;
    }

    public static void printSortedArray() {
        System.out.print("{ ");
        for (int i = 0; i < n; i++) {
            System.out.print(sortedArray[i] + " ");
        }
        System.out.println("}");
    }
}