import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestClient {
  /**
   * . Test Client
   * 
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // create initial board from file
    String filename = "puzzle3x3-unsolvable1";
    In in = new In(filename + ".txt");
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);
    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible" + solver.moves());
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      int counter = 0;
      for (Board board : solver.solution()) {
        StdOut.println(counter++);
        StdOut.println(board);
      }
    }
  }

}
