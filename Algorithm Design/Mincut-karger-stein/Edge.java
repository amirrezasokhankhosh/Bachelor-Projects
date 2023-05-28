public class Edge {
    private int firstVertice;
    private int secondVertice;

    public Edge(int firstVertice, int secondVertice) {
        this.firstVertice = firstVertice;
        this.secondVertice = secondVertice;
    }

    public int getFirstVertice() {
        return this.firstVertice;
    }

    public int getSecondVertice() {
        return this.secondVertice;
    }
}