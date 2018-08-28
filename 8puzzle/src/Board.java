import edu.princeton.cs.algs4.Point2D;

import java.util.Arrays;
import java.util.Stack;

public class Board {
  private int[] board1D;
  private int[] board1DReverse;
  private int dimention;

  /**
   * . construct a board from an n-by-n array of blocks
   */
  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new IllegalArgumentException();
    }
    dimention = blocks.length;
    board1D = new int[dimention * dimention];
    board1DReverse = board1D.clone();
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[i].length; j++) {
        int val = blocks[i][j];
        int index = i * dimention + j;
        board1D[index] = val;
        board1DReverse[val] = index;
      }
    }
  }

  private Board(int[] blocks1D) {
    if (blocks1D == null) {
      throw new IllegalArgumentException();
    }
    dimention = (int) Math.sqrt(blocks1D.length);
    board1D = blocks1D.clone();
    board1DReverse = new int[board1D.length];
    for (int i = 0; i < board1D.length; i++) {
      int val = board1D[i];
      int index = i;
      board1DReverse[val] = index;
    }
  }

  /**
   * . Board dimension
   * 
   * @return board dimension n
   */
  public int dimension() {
    return dimention;
  }

  /**
   * . number of blocks out of place
   * 
   * @return the number of blocks
   */
  public int hamming() {
    int hamming = 0;
    for (int i = 0; i < board1D.length; i++) {
      if (board1D[i] != 0 && board1D[i] != i + 1) {
        hamming++;
      }
    }
    return hamming;
  }

  /**
   * . sum of Manhattan distances between blocks and goal
   * 
   * @return manhattan number
   */
  public int manhattan() {
    int manhattan = 0;
    for (int i = 0; i < board1D.length; i++) {
      if (board1D[i] == 0) {
        continue;
      }
      int index = board1DReverse[board1D[i]];
      int goalIndex = board1D[i] - 1;
      int dI = (int) Math.abs(getIJ(goalIndex).x() - getIJ(index).x());
      int dJ = (int) Math.abs(getIJ(goalIndex).y() - getIJ(index).y());
      manhattan += (dI + dJ);
    }
    return manhattan;
  }

  private Point2D getIJ(int index) {
    int i = (index % dimention);
    int j = index / dimention;
    return new Point2D(i, j);
  }

  private int getIndex(int i, int j) {
    return i * dimention + j;
  }

  /**
   * . checks whether the board is goal board or not
   * 
   * @return true/false
   */
  public boolean isGoal() {
    if (hamming() == 0) {
      return true;
    }
    return false;
  }

  /**
   * . a board that is obtained by exchanging any pair of blocks
   * 
   * @return twin Board
   */
  public Board twin() {
    int zeroIndex = board1DReverse[0];
    int[] tb = board1D.clone();
    if (zeroIndex - 2 >= 0) {
      int tswap = tb[zeroIndex - 1];
      tb[zeroIndex - 1] = tb[zeroIndex - 2];
      tb[zeroIndex - 2] = tswap;
    } else {
      int tswap = tb[zeroIndex + dimention];
      tb[zeroIndex + dimention] = tb[zeroIndex + dimention - 1];
      tb[zeroIndex + dimention - 1] = tswap;
    }
    return new Board(tb);

  }

  /**
   * .does this board equal y?
   */
  public boolean equals(Object that) {
    if (that == null) {
      return false;
    }
    Board thatBoard;
    try {
      thatBoard = (Board) that;
    } catch (ClassCastException e) {
      return false;
    }
    return Arrays.equals(thatBoard.board1D, board1D);
  }

  /**
   * . find neighbors
   * 
   * @return neighbors
   */
  public Iterable<Board> neighbors() {
    Stack<Board> neighborsStack = new Stack<Board>();
    int zeroIndex = board1DReverse[0];
    int zeroI = (int) getIJ(zeroIndex).x();
    int zeroJ = (int) getIJ(zeroIndex).y();
    if (zeroI - 1 >= 0) {
      int[] tempBoard1D = board1D.clone();
      tempBoard1D[zeroIndex] = tempBoard1D[zeroIndex - 1];
      tempBoard1D[zeroIndex - 1] = 0;
      neighborsStack.push(new Board(tempBoard1D));
    }
    if (zeroI + 1 < dimention) {
      int[] tempBoard1D = board1D.clone();
      tempBoard1D[zeroIndex] = tempBoard1D[zeroIndex + 1];
      tempBoard1D[zeroIndex + 1] = 0;
      neighborsStack.push(new Board(tempBoard1D));
    }
    if (zeroJ + 1 < dimention) {
      int[] tempBoard1D = board1D.clone();
      tempBoard1D[zeroIndex] = tempBoard1D[zeroIndex + dimention];
      tempBoard1D[zeroIndex + dimention] = 0;
      neighborsStack.push(new Board(tempBoard1D));
    }
    if (zeroJ - 1 >= 0) {
      int[] tempBoard1D = board1D.clone();
      tempBoard1D[zeroIndex] = tempBoard1D[zeroIndex - dimention];
      tempBoard1D[zeroIndex - dimention] = 0;
      neighborsStack.push(new Board(tempBoard1D));
    }
    return neighborsStack;
  }

  /**
   * . string representation of this board (in the output format specified below
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(dimention + "\n");
    for (int i = 0; i < dimention; i++) {
      for (int j = 0; j < dimention; j++) {
        s.append(String.format("%2d ", board1D[getIndex(i, j)]));
      }
      s.append("\n");
    }
    return s.toString();
  }

}
