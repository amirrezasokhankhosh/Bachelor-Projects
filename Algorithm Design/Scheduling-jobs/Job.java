package sample;

import java.util.Comparator;

public class Job {
    private int profit;
    private int deadLine;
    private int number;
    private int penalty;

    public Job(int profit, int deadLine, int number, int penalty) {
        this.profit = profit;
        this.deadLine = deadLine;
        this.number = number;
        this.penalty = penalty;
    }

    public int getDeadLine() {
        return this.deadLine;
    }

    public int getProfit() {
        return this.profit;
    }

    public int getNumber() {
        return this.number;
    }

    public int getPenalty() {
        return this.penalty;
    }

    public void setDeadLine(int deadLine) {
        this.deadLine = deadLine;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Number : " + this.number + " Profit : " + this.profit + " DeadLine : " + this.deadLine;
    }
}

class ProfitCompare implements Comparator<Job> {
    public int compare(Job first, Job second) {
        if (first.getProfit() < second.getProfit()) {
            return 1;
        } else if (first.getProfit() > second.getProfit()) {
            return -1;
        } else {
            return 0;
        }
    }
}
