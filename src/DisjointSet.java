import java.util.ArrayList;
import java.util.Arrays;

public class DisjointSet {

    // Public Interface

    /**
     * Create a disjoint set of a specified size.
     * */
    public DisjointSet(int size) {
        s = new int[size];
        Arrays.fill(s, -1);
    }

    /**
     * Combine two sets.
     * Assumption: negative number is size, nonnegative number is parent
     * */
    public void union(int node1, int node2) {
        // Find the set each node belongs to
        int root1 = find(node1);
        int root2 = find(node2);
        // If both nodes already belong to the same set, do nothing
        if (root1 == root2) {
            return;
        }
        // Else, union the sets
        if (s[root2] < s[root1]) {
            // root2 is larger because it is more negative
            s[root2] += s[root1];
            s[root1] = root2;
        }
        else {
            // root1 is equal or larger
            s[root1] += s[root2];
            s[root2] = root1;
        }
    }

    /**
     * Determine what set an element resides in by traversing up the tree.
     * */
    public int find(int node) {

        // Traverse up the tree while keeping track of nodes visited
        var nodesVisited = new ArrayList<Integer>();
        int currentNode = node;
        while (s[currentNode] >= 0) {
            nodesVisited.add(currentNode);
            currentNode = s[currentNode];
        }

        // Path compression on visited nodes
        for (Integer nodeVisited : nodesVisited) {
            s[nodeVisited] = currentNode;
        }

        return currentNode;
    }

    // Private Members
    int[] s;

}
