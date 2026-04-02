import java.util.ArrayList;
import java.util.Arrays;

public class HexGame {

    // Public interface

    /**
     * Initialize a Hex game with a grid of the specified size.
     * */
    public HexGame(int size) {
        this.size = size;
        int gridSize = size*size + 1; // So I can use 1-based indexing for code readability

        // Initialize empty Hex grid
        grid = new Color[gridSize];
        Arrays.fill(grid, Color.None);

        // Initialize DisjointSet
        // Add four additional elements of the DisjointSet for the four edges of the grid
        ds = new DisjointSet(gridSize + 4);

        // Assign DisjointSet elements to the four edges of the grid
        TOP_EDGE = gridSize;
        BOTTOM_EDGE = gridSize + 1;
        LEFT_EDGE = gridSize + 2;
        RIGHT_EDGE = gridSize + 3;

        // Determine the grid positions associated with each edge
        TOP_EDGE_POSITIONS = new int[size];
        BOTTOM_EDGE_POSITIONS = new int[size];
        LEFT_EDGE_POSITIONS = new int[size];
        RIGHT_EDGE_POSITIONS = new int[size];
        for (int i = 0; i < size; i ++) {
            TOP_EDGE_POSITIONS[i] = i + 1;
            BOTTOM_EDGE_POSITIONS[i] = i + 1 + (size - 1) * size;
            LEFT_EDGE_POSITIONS[i] = i * size + 1;
            RIGHT_EDGE_POSITIONS[i] = i * size + size;
        }

        // DEBUG: print the grid positions
        System.out.println("Top edge positions:");
        System.out.println(Arrays.toString(TOP_EDGE_POSITIONS));
        System.out.println("Bottom edge positions:");
        System.out.println(Arrays.toString(BOTTOM_EDGE_POSITIONS));
        System.out.println("Left edge positions:");
        System.out.println(Arrays.toString(LEFT_EDGE_POSITIONS));
        System.out.println("Right edge positions:");
        System.out.println(Arrays.toString(RIGHT_EDGE_POSITIONS));

    }

    /**
     * Make a move for the blue player.
     * Assumes a valid position is entered (1 through 121 if size==11)
     * Return true if win condition is reached. False otherwise.
     * */
    public boolean playBlue(int position, boolean displayNeighbors) {

        // Set the position to blue
        grid[position] = Color.Blue;

        // Union the position with all blue neighbors and either left or right board edge, if applicable.
        ArrayList<Integer> blueNeighbors = getNeighborsBlue(position);
        for (Integer neighbor: blueNeighbors) {
            ds.union(neighbor, position);
        }

        // Display neighbors if desired
        if (displayNeighbors) {
            System.out.print("Cell " + position +  ": [ ");
            for (Integer neighbor : blueNeighbors) {
                System.out.print(neighbor + " ");
            }
            System.out.println("]");
        }

        return ds.find(LEFT_EDGE) == ds.find(RIGHT_EDGE);
    }

    /**
     * Make a move for the red player.
     * Assumes a valid position is entered (1 through 121 if size==11)
     * Return true if win condition is reached. False otherwise.
     * */
    public boolean playRed(int position, boolean displayNeighbors) {

        // Set the position to red
        grid[position] = Color.Red;

        // Union the position with all blue neighbors and either left or right board edge, if applicable.
        ArrayList<Integer> redNeighbors = getNeighborsRed(position);
        for (Integer neighbor: redNeighbors) {
            ds.union(neighbor, position);
        }

        // Display neighbors if desired
        if (displayNeighbors) {
            System.out.print("Cell " + position +  ": [ ");
            for (Integer neighbor : redNeighbors) {
                System.out.print(neighbor + " ");
            }
            System.out.println("]");
        }

        return ds.find(TOP_EDGE) == ds.find(BOTTOM_EDGE);
    }

    public enum Color {
        None, Red, Blue
    }

    /**
     * Get the grid.
     * */
    public Color[] getGrid() {
        return grid;
    }

    /**
     * Get the size of a row or column of the grid.
     * */
    public int getSize() {
        return size;
    }

    // Private members
    private boolean isOccupied(int position) {
        return grid[position] != Color.None;
    }

    /**
     * Return the disjoint set elements associated with the red neighbors of the provided grid index
     * */
    private ArrayList<Integer> getNeighborsRed(int position) {
        var neighborsRed = new ArrayList<Integer>();
        if (touchingTopEdge(position)) {
            neighborsRed.add(TOP_EDGE);
        }
        if (touchingBottomEdge(position)) {
            neighborsRed.add(BOTTOM_EDGE);
        }
        // LEFT OFF HERE
    }

    /**
     * Return the disjoint set elements associated with the blue neighbors of the provided grid index
     * */
    private ArrayList<Integer> getNeighborsBlue(int position) {

    }

    private boolean touchingTopEdge(int idx) {
        return Arrays.binarySearch(TOP_EDGE_POSITIONS, idx) >= 0;
    }

    private boolean touchingBottomEdge(int idx) {
        return Arrays.binarySearch(BOTTOM_EDGE_POSITIONS, idx) >= 0;
    }

    private boolean touchingLeftEdge(int idx) {
        return Arrays.binarySearch(LEFT_EDGE_POSITIONS, idx) >= 0;
    }

    private boolean touchingRightEdge(int idx) {
        return Arrays.binarySearch(RIGHT_EDGE_POSITIONS, idx) >= 0;
    }

    private final int size;
    private final DisjointSet ds;
    private final Color[] grid;

    // DisjointSet indices associated with each edge
    private final int TOP_EDGE;
    private final int BOTTOM_EDGE;
    private final int LEFT_EDGE;
    private final int RIGHT_EDGE;

    // Grid indices associated with each edge
    private final int[] TOP_EDGE_POSITIONS;
    private final int[] BOTTOM_EDGE_POSITIONS;
    private final int[] LEFT_EDGE_POSITIONS;
    private final int[] RIGHT_EDGE_POSITIONS;

}
