package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {
    static Scanner scanner;
    static int countOfJobs;
    static ArrayList<Job> jobs;
    static ArrayList<Job> rest;
    static Job[] plan;
    static int[] takenDays;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ShoeMaker Project!");

        GridPane gridPane = new GridPane();

        Text number = new Text("Number");
        Text numberOfJob = new Text("Number of job");
        Text profitAfterPenelty = new Text("Profit after penelty");

        gridPane.add(number, 0, 0);
        gridPane.add(numberOfJob, 1, 0);
        gridPane.add(profitAfterPenelty, 2, 0);

        for (int i = 0; i < countOfJobs; i++) {
            Text textForNumber = new Text(Integer.toString(i + 1));
            Text textForNumberOfJob = new Text(Integer.toString(plan[i].getNumber() + 1));
            Text textForProfitAfterPenelty = new Text(Integer.toString(plan[i].getProfit()));
            gridPane.add(textForNumber, 0, i + 1);
            gridPane.add(textForNumberOfJob, 1, i + 1);
            gridPane.add(textForProfitAfterPenelty, 2, i + 1);
        }

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(20);
        gridPane.setHgap(20);

        gridPane.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(gridPane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        getInput();
        doScheduleAlgorithm();
        launch(args);
    }

    public static void getInput() {
        scanner = new Scanner(System.in);
        jobs = new ArrayList<Job>();
        System.out.println("Enter the Number of Jobs : ");
        countOfJobs = scanner.nextInt();
        for (int i = 0; i < countOfJobs; i++) {
            System.out.println("Enter the profit of Job " + (i + 1) + " : ");
            int profit = scanner.nextInt();
            System.out.println("Enter the deadline of Job " + (i + 1) + " : ");
            int deadline = scanner.nextInt();
            System.out.println("Enter the payment penalty per a day of Job " + (i + 1) + " : ");
            int penalty = scanner.nextInt();
            Job job = new Job(profit, deadline - 1, i + 1, penalty);
            jobs.add(job);
        }
        ProfitCompare profitCompare = new ProfitCompare();
        Collections.sort(jobs, profitCompare);
        scanner.close();
    }

    public static void doScheduleAlgorithm() {
        plan = new Job[countOfJobs];
        rest = new ArrayList<Job>();
        takenDays = new int[countOfJobs];
        for (int i = 0; i < countOfJobs; i++) {
            takenDays[i] = -1;
        }
        int takenJobs = 0;
        while (takenJobs != countOfJobs) {
            Job job = jobs.get(0);
            int indexInPlan = check(job);
            if (indexInPlan != -1) {
                plan[indexInPlan] = job;
                takenDays[takenJobs] = indexInPlan;
                takenJobs = takenJobs + 1;
                jobs.remove(job);
            } else {
                job.setDeadLine(job.getDeadLine() + 1);
                job.setProfit(job.getProfit() - job.getPenalty());
                ProfitCompare profitCompare = new ProfitCompare();
                Collections.sort(jobs, profitCompare);
            }

        }

    }

    public static int check(Job job) {
        int i = job.getDeadLine();
        while (i >= 0) {
            if (takedBefore(i) == false) {
                return i;
            }
            i = i - 1;
        }
        return -1;
    }

    public static boolean takedBefore(int n) {
        for (int i = 0; i < countOfJobs; i++) {
            if (takenDays[i] == n) {
                return true;
            }
        }
        return false;
    }
}
