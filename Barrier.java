package cypath;

/**
 * 
 * @author CY-Path Group 15
 * @version 1.0
 * 
 * The class <b> Barrier </b> is an abstract object. We cannot instance it.
 *
 */

public class Barrier {

    /**
     * This method removes an edge between 2 nodes.
     * @param graph is an instance of the class graph. It represents a 9x9 matrix which is the game plate.
     * @param node1 is the first node of the graph "graph"
     * @param node2 is the second node of the graph "graph"
     */
    public static void removeEdge(Graph graph, Node node1, Node node2) {
        if (graph.getNeighbors(node1).contains(node2)) { //check if node1 is a neighbor of node2
            graph.getNeighbors(node1).remove(node2); //remove the edge between node1 and node2
            graph.getNeighbors(node2).remove(node1); //remove the edge between node2 and node1
            System.out.println("Edge removed between (" + node1.getX() + ", " + node1.getY() +
                    ") and (" + node2.getX() + ", " + node2.getY() + ")"); //display result
        } else {
            System.out.println("Invalid edge removal!"); //display error message
        }
    }
}