package cypath;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
	private List<Node> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Node node1, Node node2) {
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
    }

    public List<Node> getNeighbors(Node node) {
        return node.getNeighbors();
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    
    public void printGraph() {
        for (Node node : nodes) {
            System.out.print("Node (" + node.getX() + ", " + node.getY() + "): ");
            for (Node neighbor : node.getNeighbors()) {
                System.out.print("(" + neighbor.getX() + ", " + neighbor.getY() + ") ");
            }
            System.out.println();
        }
    }
    
    public List<Node> breadthFirstTraversal(Node startNode) {
        List<Node> visited = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();

        visited.add(startNode);
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            List<Node> neighbors = current.getNeighbors();

            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return visited;
    }
}
