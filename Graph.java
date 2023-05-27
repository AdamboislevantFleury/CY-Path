package cypath;
/**
 * @author CY-Path Group 15
 * @version 1.0
 * This class contains method to create the board.
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {

     /**
      * @param nodes is a list of nodes of the class Node.
      * It is the list of all nodes in the board with their coordinates.
      *
      */
	private List<Node> nodes;
	//constructor

    /**
     * Constructor for the graph.
     */
    public Graph() {
        nodes = new ArrayList<>();
    }

    /**
     * Add nodes in the list of nodes of the graph.
     * @param node is a given node
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Add an edge between 2 chosen nodes, and make them neighbors.
     * @param node1 is the first chosen node.
     * @param node2 is the second chose node.
     */
    public void addEdge(Node node1, Node node2) { //adding an edge between two nodes is making 2 nodes neighbors from each other.
        node1.addNeighbor(node2); 
        node2.addNeighbor(node1);
        //System.out.println("Edge added between (" + node1.getX() + ", " + node1.getY() +
                //") and (" + node2.getX() + ", " + node2.getY() + ")");
    }
    //getters
    /**
     * Method getNeighbors return a list of neighbors of the node in parameters.
     * @param node in parameter.
     * @return the list of the node's neighbors.
     */
    public List<Node> getNeighbors(Node node) {
        return node.getNeighbors();
    }

    /**
     * This method gives the list of all the nodes on a certain graph.
     * @return a list of the nodes of the graph.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * printGraph displays all the relations between the nodes of the graph.
     */
    public void printGraph() {
        for (Node node : nodes) {
            System.out.print("Node (" + node.getX() + ", " + node.getY() + "): "); //browse all the nodes of the graph
            for (Node neighbor : node.getNeighbors()) {
                System.out.print("(" + neighbor.getX() + ", " + neighbor.getY() + ") "); //list of neighbors of the current node
            }
            System.out.println();
        }
    }

    /**
     * Breadth First Traversal algorithm
     * @param startNode is the chosen starting node
     * @return the list of visited node
     */
    public List<Node> breadthFirstTraversal(Node startNode) { //BFS Algorithm
        List<Node> visited = new ArrayList<>(); //list of visited nodes
        Queue<Node> queue = new LinkedList<>(); //queue of visited nodes

        visited.add(startNode); //mark the starting node as visited
        queue.offer(startNode); //add the node to the queue

        while (!queue.isEmpty()) {
            Node current = queue.poll(); //add the node to the queue
            List<Node> neighbors = current.getNeighbors(); //get all the neighbors of the node

            for (Node neighbor : neighbors) { //browse all neighbors of the current node
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor); //mark as visited
                    queue.offer(neighbor); //add to the queue
                }
            }
        }

        return visited; //return a list of visited nodes.
    }
}
