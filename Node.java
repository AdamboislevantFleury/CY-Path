package cypath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CY-Path Group 15
 * @version 1.0
 * Class <b> Node </b> describes the structure of node into a graph.
 *
 */

public class Node {

    /**
     * Boolean variable which is true if a pawn is at the matching position
     */
    public boolean isTaken;

    /**
     * Integer value. This is the first coordinate of the node in the matrix
     */
    private int x;

    /**
     * Integer value. This is the first coordinate of the node in the matrix
     */
    private int y;

    /**
     * List of nodes. It contains all the nodes at a distance of 1 from the node. An edge must exist between a node and its neighbors.
     */
    private List<Node> neighbors;
    
    //constructor

    /**
     * This method creates a new node with given
     * @param x integer value, 1st coordinate of the node
     * @param y interger value, 2nd coordinate of the node
     */
    public Node(int x, int y) {
        this.x = x; //x
        this.y = y; //y
        this.neighbors = new ArrayList<>();
        this.isTaken = false; //Default value of isTaken is false: there is no pawn at this place.
    }
    //getters

    /**
     * Getter X
     * @return value of the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter Y
     * @return value of the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * This method give a list of all neighbors
     * @return the list of neighbors
     */
    public List<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * This method adds a given node to the list of neighbors
     * @param neighbor is the node to add to the neighbor's list
     */
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor); //add the node neighbor to the list of neighbors.
    }
    
    //equals method

    public boolean equals(Object obj) {
    	if (obj instanceof Node) {
    		Node n = (Node)obj;
    		if(this.getX()==n.getX() && this.getY() ==n.getY()) {
    			return true;
    		}
    	}
    	return false;
    }
}
