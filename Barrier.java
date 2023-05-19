package cypath;

public class Barrier {
    public static void removeEdge(Graph graph, Node node1, Node node2) {
        if (graph.getNeighbors(node1).contains(node2)) {
            graph.getNeighbors(node1).remove(node2);
            graph.getNeighbors(node2).remove(node1);
            System.out.println("Edge removed between (" + node1.getX() + ", " + node1.getY() +
                    ") and (" + node2.getX() + ", " + node2.getY() + ")");
        } else {
            System.out.println("Invalid edge removal!");
        }
    }
}