package cypath;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private int x;
    private int y;
    private List<Node> neighbors;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }
    
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
