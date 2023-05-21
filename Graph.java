package cypath;
/**
 * @author CY-Path Group 15
 * @version 1.0
 * @param nodes is a list of nodes of the class Node. See class Node documentation for more details.
 * 
 * This class contains methods which add nodes and edges to the graph. Method getNeighbors return a list of neighbors of the node in parameters. getNodes() returns a list of the nodes of the graph.
 * Print Graph displays all the relations between the nodes of the graph.
 * breadthFirstTraversal() is a BFS algorithm through the graph in parameters.
 * 
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
	private List<Node> nodes;
	//constructor
    public Graph() {
        nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);//add the node in parameter in the list of nodes of the graph
    }

    public void addEdge(Node node1, Node node2) { //adding an edge between two nodes is making 2 nodes neighbors from each other.
        node1.addNeighbor(node2); 
        node2.addNeighbor(node1);
    }
    //getters
    public List<Node> getNeighbors(Node node) {
        return node.getNeighbors();
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    //display graph
    public void printGraph() {
        for (Node node : nodes) {
            System.out.print("Node (" + node.getX() + ", " + node.getY() + "): "); //browse all the nodes of the graph
            for (Node neighbor : node.getNeighbors()) {
                System.out.print("(" + neighbor.getX() + ", " + neighbor.getY() + ") "); //list of neighbors of the current node
            }
            System.out.println();
        }
    }
    
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
