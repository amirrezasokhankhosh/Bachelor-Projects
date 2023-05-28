import java.util.Scanner;

class BinarySearch {
    static Scanner scanner;
    static int lenght;
    static int number;
    static int[] array;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter the lenght of the array :");
        lenght = scanner.nextInt();
        System.out.println("Enter the Number that you are looking for :");
        number = scanner.nextInt();
        array = new int[lenght];
        System.out.println("Enter the array :");
        for (int i = 0; i < lenght; i++) {
            array[i] = scanner.nextInt();
        }
        BubbleSort();
        System.out.println("********************");
        System.out.println("Which method do you want to use :");
        System.out.println("1.Recursive method");
        System.out.println("2.None recursive method");
        System.out.println("Enter your option: ");
        int option = scanner.nextInt();
        System.out.println("This is the sorted array of your input array : ");
        printSortedArray();
        int address = 0;
        if (option == 1) {
            address = RecBinarySearch(lenght - 1, 0) + 1;
        } else if (option == 2) {
            address = NonRecBinarySearch(lenght - 1, 0) + 1;
        }
        if (address == 1) {
            System.out.println("your number is the " + address + "st in the sorted array!");
        } else if (address == 2) {
            System.out.println("your number is the " + address + "nd in the sorted array!");
        } else if (address == 3) {
            System.out.println("your number is the " + address + "rd in the sorted array!");
        } else {
            System.out.println("your number is the " + address + "th in the sorted array!");
        }
    }

    public static int RecBinarySearch(int up, int down) {
        int lenghtInMethod = up - down + 1;
        int middle = (up + down) / 2;
        if (lenghtInMethod == 1) {
            if (array[middle] == number) {
                return middle;
            }
        } else {
            if (array[middle] == number) {
                return middle;
            } else if (array[middle] > number) {
                return RecBinarySearch(middle - 1, down);
            } else {
                return RecBinarySearch(up, middle + 1);
            }
        }
        return -1;
    }

    public static void BubbleSort() {
        int lenghtInMethod = lenght;
        for (int i = 0; i < lenghtInMethod; i++) {
            for (int j = 0; j < lenghtInMethod - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static int NonRecBinarySearch(int up, int down) {
        int lenghtInMethod = up - down + 1;
        int middle = (up + down) / 2;
        while (lenghtInMethod != 1) {
            if (array[middle] == number) {
                return middle;
            } else if (array[middle] > number) {
                up = middle - 1;
            } else {
                down = middle + 1;
            }
            lenghtInMethod = up - down + 1;
            middle = (up + down) / 2;
        }
        if (lenghtInMethod == 1) {
            if (array[middle] == number) {
                return middle;
            }
        }
        return -1;
    }

    public static void printSortedArray() {
        System.out.print("{ ");
        for (int i = 0; i < lenght; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("}\n");
    }
}