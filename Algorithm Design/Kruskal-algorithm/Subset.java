package sample;
public class Subset {
    private int countOfVertices;
    private int[] vertices;

    public Subset(int countOfVertices, int vertice, int n) {
        vertices = new int[n];
        this.vertices[0] = vertice;
        this.countOfVertices = countOfVertices;
    }

    public boolean containAVertice(int vertice) {
        for (int i = 0; i < countOfVertices; i++) {
            if (vertices[i] == vertice) {
                return true;
            }
        }
        return false;
    }

    public int[] getVertices() {
        return this.vertices;
    }

    public int getCountOfVertices() {
        return this.countOfVertices;
    }

    public void setCountOfVertices(int countOfVertices) {
        this.countOfVertices = countOfVertices;
    }

    public void setVertices(int[] vertices) {
        this.vertices = vertices;
    }

}