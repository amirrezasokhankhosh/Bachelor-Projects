package sample;
import java.util.Comparator;

public class Edge {
    private int firstVertice;
    private int secondVertice;
    private int weight;

    public Edge(int firstVertice, int secondVertice, int weight) {
        this.firstVertice = firstVertice;
        this.secondVertice = secondVertice;
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getFirstVertice() {
        return this.firstVertice;
    }

    public int getSecondVertice() {
        return this.secondVertice;
    }

    @Override
    public String toString() {
        return "Weight : " + this.weight + " - First Vertice : " + this.firstVertice + " - Second Vertice : " + secondVertice;
    }
}

class WeightCompare implements Comparator<Edge> {
    public int compare(Edge first, Edge second) {
        if (first.getWeight() < second.getWeight()) {
            return -1;
        } else if (first.getWeight() > second.getWeight()) {
            return 1;
        } else {
            return 0;
        }
    }
}