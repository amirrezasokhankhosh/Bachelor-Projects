import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        File myObj = new File("./input.txt");
        Scanner myReader = new Scanner(myObj);
        int windowSize = Integer.parseInt(myReader.nextLine());
        Boolean GBN = Boolean.parseBoolean(myReader.nextLine()); 
        myReader.close();

        int[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };

        Buffer buffer = new Buffer();
        Sender sender = new Sender(buffer, windowSize, data , GBN);
        Recevier recevier = new Recevier(windowSize, sender, 16, buffer);

        clearTheFile("./sender.txt");
        clearTheFile("./recevier.txt");

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(recevier);
        pool.execute(sender);
        pool.shutdown();
    }

    public static void clearTheFile(String filename) {
        try {
            FileWriter fwOb = new FileWriter(filename, false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
