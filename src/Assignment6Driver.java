import java.io.File;
import java.util.Scanner;

public class Assignment6Driver {
    // Macros for setting console output color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        testDisjointSet();
        testGame();
        System.out.println();
        playGame("moves1.txt");
        System.out.println();
        playGame("moves2.txt");
    }

    private static void playGame(String filename) {
        File file = new File(filename);
        try (Scanner input = new Scanner(file)) {
            HexGame game = new HexGame(11);

            boolean win = false;
            String winner = "None";
            boolean blue = true;  // Blue starts
            int move = -1;
            while (!win && input.hasNextInt()) {  // Go until someone wins or there are no more moves in the file
                move = input.nextInt();

                // Alternate between playing blue and red
                if (blue) {
                    win = game.playBlue(move, false);
                    winner = "Blue";
                    blue = false;  // Toggle player
                }
                else {
                    win = game.playRed(move, false);
                    winner = "Red";
                    blue = true;  // Toggle player
                }
            }

            // If someone won, display the winner
            if (win) {
                System.out.println(winner + " wins with move at position " + move + "!!");
            }

            printGrid(game);

        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }
    }

    private static void testGame() {
        HexGame game = new HexGame(11);

        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

        System.out.println("--- blue ---");
        game.playBlue(1, true);
        game.playBlue(2, true);
        game.playBlue(11, true);
        game.playBlue(12, true);
        game.playBlue(121, true);
        game.playBlue(122 - 11, true);
        game.playBlue(62, true);

        printGrid(game);
    }

    /**
     * Make the text display in red when printed to the console.
     * */
    private static String makeRed(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    /**
     * Make the text display in blue when printed to the console.
     * */
    private static String makeBlue(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    private static void printGrid(HexGame game) {
        System.out.println();
        HexGame.Color[] grid = game.getGrid();
        int size = game.getSize();
        for (int row = 0; row < size; row++) {
            System.out.print(" ".repeat(row));
            for (int col = 1; col <= size; col++) {
                int pos = (row * size) + col;
                HexGame.Color c = grid[pos];
                System.out.print(" ");
                if (c == HexGame.Color.Red) {
                    System.out.print(makeRed("R"));
                }
                else if (c == HexGame.Color.Blue) {
                    System.out.print(makeBlue("B"));
                }
                else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }

    }

    /**
     * Simple unit test for ensuring the DisjointSet class was implemented properly.
     * */
    private static void testDisjointSet() {
        var ds = new DisjointSet(6 + 1);
        ds.union(1,2);
        ds.union(3,4);
        ds.union(5,6);
        assert ds.find(1) == ds.find(2);
        assert ds.find(3) == ds.find(4);
        assert ds.find(5) == ds.find(6);
        assert ds.find(1) != ds.find(3);
        assert ds.find(3) != ds.find(5);
        ds.union(2,4);
        ds.union(4,6);
        assert ds.find(1) == ds.find(6);

        System.out.println("DisjointSet test passed!");
    }

}
