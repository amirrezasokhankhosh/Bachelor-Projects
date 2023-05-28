import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class MinCut2 {
    static Scanner scanner;
    static int nAtFirst;
    static ArrayList<Edge> edgesAtFirst;
    static ArrayList<Subset> subsetsAtFirst;
    static Random random = new Random();
    static int call;
    static int radical;

    public static void main(String[] args) {
        getInputs();
        createSubsets();
        //call = 1;
        ArrayList<Edge> edges = doTheKargerAlgorithm(edgesAtFirst, subsetsAtFirst, nAtFirst);
        printEdges(edges);
    }

    public static void getInputs() {
        scanner = new Scanner(System.in);
        edgesAtFirst = new ArrayList<Edge>();
        System.out.println("Enter 2 or 3 for radical : ");
        radical = scanner.nextInt();
        System.out.println("Enter the count of vertices of the graph : ");
        nAtFirst = scanner.nextInt();
        for (int i = 0; i < nAtFirst; i++) {
            for (int j = i + 1; j < nAtFirst; j++) {
                System.out.println("Enter 1 if there is a edge between " + (i + 1) + " and " + (j + 1));
                int ans = scanner.nextInt();
                if (ans == 1) {
                    int first = i;
                    int second = j;
                    Edge edge = new Edge(first, second);
                    edgesAtFirst.add(edge);
                }
            }
        }
        scanner.close();
    }

    public static void createSubsets() {
        subsetsAtFirst = new ArrayList<Subset>();
        for (int i = 0; i < nAtFirst; i++) {
            Subset subset = new Subset(1, i, nAtFirst);
            subsetsAtFirst.add(subset);
        }
    }

    public static ArrayList<Edge> doTheKargerAlgorithm(ArrayList<Edge> edges, ArrayList<Subset> subsets, int n) {
        //int callNumber = call;
        //System.out.println("This is call number " + call + " .\n");
        ArrayList<Edge> edgesForThisFunction = new ArrayList<Edge>();
        for (Edge edge : edges) {
            edgesForThisFunction.add(edge);
        }
        ArrayList<Subset> subsetsForThisFunction = new ArrayList<Subset>();
        for (Subset subset : subsets) {
            subsetsForThisFunction.add(subset);
        }
        edgesForThisFunction = deleteEdgesInSubsets(edgesForThisFunction, subsetsForThisFunction);
        if (subsetsForThisFunction.size() != 2) {
            Edge edge = selectARandomEdge(edgesForThisFunction);
            // System.out.println(
            //         "The edge between " + edge.getFirstVertice() + " and " + edge.getSecondVertice() + " selected .\n");
            edgesForThisFunction.remove(edge);
            //System.out.println("Edges after select are :\n");
            //printEdges(edgesForThisFunction);
            int firstSubset = findSubsetOfthisVertice(edge.getFirstVertice(), subsetsForThisFunction);
            int secondSubset = findSubsetOfthisVertice(edge.getSecondVertice(), subsetsForThisFunction);
            System.out.println("Connecting the subest number " + firstSubset + " and " + secondSubset + " ...\n");
            if (firstSubset != secondSubset) {
                subsetsForThisFunction = connectSubsets(firstSubset, secondSubset, subsetsForThisFunction);
                //System.out.println("Subsets after connect are :");
                //printSubsets(subsetsForThisFunction);
            }
            double radical2Or3 = Math.sqrt(radical);
            Double number = new Double(n);
            double check = number / radical2Or3;
            check = Math.round(check);
            int checkInt = (int) check;
            if (subsetsForThisFunction.size() != checkInt) {
                // call = call + 1;
                ArrayList<Edge> answer = doTheKargerAlgorithm(edgesForThisFunction, subsetsForThisFunction, n);
                // System.out.println("A Value returned from call number to call number " +
                // callNumber + " .\n");
                return answer;
            } else {
                // System.out.println("\n***** WE MAKE 2 COPYS OF THE ALGORITHM *******\n");
                // call = call + 1;
                ArrayList<Edge> answerOfFirst = doTheKargerAlgorithm(edgesForThisFunction, subsetsForThisFunction,
                        subsetsForThisFunction.size());
                // System.out.println("A Value returned from call number to call number " +
                // callNumber + " .\n");
                // call = call + 1;
                ArrayList<Edge> answerOfSecond = doTheKargerAlgorithm(edgesForThisFunction, subsetsForThisFunction,
                        subsetsForThisFunction.size());
                // System.out.println("A Value returned from call number to call number " +
                // callNumber + " .\n");
                if (answerOfFirst.size() < answerOfSecond.size()) {
                    // System.out.println("In Call number "+ callNumber + "First was better .\n");
                    return answerOfFirst;
                } else {
                    // System.out.println("In Call number "+ callNumber + "Second was better or equal .\n");
                    return answerOfSecond;
                }
            }
        } else {
            return edgesForThisFunction;
        }

    }

    public static Edge selectARandomEdge(ArrayList<Edge> edges) {
        int countOfEdges = edges.size();
        int randomNumber = random.nextInt(countOfEdges);
        Edge edge = edges.get(randomNumber);
        return edge;
    }

    public static int findSubsetOfthisVertice(int vertice, ArrayList<Subset> subsets) {
        for (Subset subset : subsets) {
            if (subset.containAVertice(vertice)) {
                return subsets.indexOf(subset);
            }
        }
        return -1;
    }

    public static ArrayList<Subset> connectSubsets(int firstSubset, int secondSubset, ArrayList<Subset> subsets) {

        ArrayList<Subset> subsetsForThisFunction = new ArrayList<Subset>();
        for (Subset subset : subsets) {
            subsetsForThisFunction.add(subset);
        }
        Subset second = new Subset(1, 0, nAtFirst);
        second.setCountOfVertices(subsetsForThisFunction.get(secondSubset).getCountOfVertices());
        second.setVertices(subsetsForThisFunction.get(secondSubset).getVertices());
        Subset first = new Subset(1, 0, nAtFirst);
        first.setCountOfVertices(subsetsForThisFunction.get(firstSubset).getCountOfVertices());
        first.setVertices(subsetsForThisFunction.get(firstSubset).getVertices());
        int countOfVertices1 = first.getCountOfVertices();
        int countOfVertices2 = second.getCountOfVertices();
        first.setCountOfVertices(countOfVertices1 + countOfVertices2);
        int[] vertices1 = first.getVertices();
        int[] vertices2 = second.getVertices();
        int[] newVertices = new int[nAtFirst];
        for (int i = 0; i < countOfVertices1; i++) {
            newVertices[i] = vertices1[i];
        }
        for (int i = 0; i < countOfVertices2; i++) {
            newVertices[i + countOfVertices1] = vertices2[i];
        }
        first.setVertices(newVertices);
        subsetsForThisFunction.set(firstSubset, first);
        subsetsForThisFunction.remove(secondSubset);
        return subsetsForThisFunction;
    }

    public static void printEdges(ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            int first = edge.getFirstVertice();
            int second = edge.getSecondVertice();
            System.out.print((edges.indexOf(edge) + 1) + ". ");
            System.out.println("First vertice : " + (first) + " Second vertice : " + (second));
        }
    }

    public static void printSubsets(ArrayList<Subset> subsets) {
        for (Subset subset : subsets) {
            System.out.print((subsets.indexOf(subset) + 1) + ". ");
            int[] vertices = subset.getVertices();
            System.out.print("{ ");
            for (int i = 0; i < subset.getCountOfVertices(); i++) {
                System.out.print(vertices[i] + " ");
            }
            System.out.println("}");
        }
    }

    public static ArrayList<Edge> deleteEdgesInSubsets(ArrayList<Edge> edges, ArrayList<Subset> subsets) {
        ArrayList<Edge> edgesForThisFunction = new ArrayList<Edge>();
        for (Edge edge : edges) {
            edgesForThisFunction.add(edge);
        }
        ArrayList<Edge> mustRemoveEdges = new ArrayList<Edge>();
        for (Edge edge : edgesForThisFunction) {
            int firstSubset = findSubsetOfthisVertice(edge.getFirstVertice(), subsets);
            int secondSubset = findSubsetOfthisVertice(edge.getSecondVertice(), subsets);
            if (firstSubset == secondSubset) {
                mustRemoveEdges.add(edge);
            }
        }
        for (Edge edge : mustRemoveEdges) {
            edgesForThisFunction.remove(edge);
        }
        return edgesForThisFunction;
    }

}