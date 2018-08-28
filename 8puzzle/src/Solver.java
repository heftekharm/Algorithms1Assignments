import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
  private SearchNode searchNode;
  private boolean solved;

  /**
   * . find a solution to the initial board (using the A* algorithm)
   * 
   * @param initial
   *          initial board
   */
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }
    // Original
    searchNode = new SearchNode(initial, null, 0);
    MinPQ<SearchNode> snMinPq = new MinPQ<Solver.SearchNode>();
    solved = false;
    // Twin
    SearchNode twinSearchNode = new SearchNode(initial.twin(), null, 0);
    MinPQ<SearchNode> twinSnMinPq = new MinPQ<SearchNode>();
    boolean twinSolved = false;

    if (!searchNode.getBoard().isGoal()) {
      do {
        searchNode = insertNeighborsIntoPqThenDelMin(searchNode, snMinPq, false);
        twinSearchNode = insertNeighborsIntoPqThenDelMin(twinSearchNode,
            twinSnMinPq, true);
        solved = searchNode.getBoard().isGoal();
        twinSolved = twinSearchNode.getBoard().isGoal();
      } while (!solved && !twinSolved);

    } else {
      solved = true;
    }
  }

  private SearchNode insertNeighborsIntoPqThenDelMin(SearchNode node,
      MinPQ<SearchNode> pq, boolean isTwin) {
    Iterable<Board> neighbors = node.getBoard().neighbors();
    for (Board neighbor : neighbors) {
      if (!neighbor.equals(node.getPrevSearchNode() != null ? node
          .getPrevSearchNode().getBoard() : null)) {
        SearchNode neighborSearchNode = new SearchNode(neighbor, node,
            node.getMoves() + 1);
        pq.insert(neighborSearchNode);
      }
    }
    return pq.delMin();
  }

  public boolean isSolvable() {
    // is the initial board solvable?
    return solved;
  }

  /**
   * . min number of moves to solve initial board; -1 if unsolvable
   * 
   * @return moves
   */
  public int moves() {
    if (!isSolvable()) {
      return -1;
    }
    return searchNode.getMoves();
  }

  /**
   * . return the path to the solution
   * 
   * @return the solution
   */
  public Iterable<Board> solution() {
    // sequence of boards in a shortest solution; null if unsolvable
    if (!solved) {
      return null;
    }
    ArrayList<Board> nodes = new ArrayList<Board>();
    for (Board board : searchNode) {
      nodes.add(board);
    }
    Collections.reverse(nodes);
    return nodes;
  }

  private class SearchNode implements Comparable<SearchNode>, Iterable<Board> {
    private int moves;
    private Board board;
    private SearchNode prevSearchNode;
    private int priority = -1;

    public SearchNode(Board board, SearchNode prevNode, int moves) {
      // TODO Auto-generated constructor stub
      this.setPrevSearchNode(prevNode);
      this.setBoard(board);
      this.setMoves(moves);
    }

    @Override
    public int compareTo(SearchNode that) {
      // TODO Auto-generated method stub
      int thisPriority = getPriority();
      int thatPriority = that.getPriority();
      int res = Integer.compare(thisPriority, thatPriority);
      return res;
    }

    int getMoves() {
      return moves;
    }

    void setMoves(int moves) {
      this.moves = moves;
    }

    Board getBoard() {
      return board;
    }

    void setBoard(Board board) {
      this.board = board;
    }

    int getPriority() {
      if (priority < 0) {
        priority = getMoves() + getBoard().manhattan();
      }
      return priority;
    }

    public String toString() {
      // TODO Auto-generated method stub
      return String.format("Priority:%d\nMoves:%d\n %s", getPriority(),
          getMoves(), board.toString());
    }

    SearchNode getPrevSearchNode() {
      return prevSearchNode;
    }

    void setPrevSearchNode(SearchNode prevSearchNode) {
      this.prevSearchNode = prevSearchNode;
    }

    @Override
    public Iterator<Board> iterator() {
      // TODO Auto-generated method stub
      return new Iterator<Board>() {
        private SearchNode nextNode = SearchNode.this;

        @Override
        public boolean hasNext() {
          // TODO Auto-generated method stub
          return nextNode != null;
        }

        @Override
        public Board next() {
          // TODO Auto-generated method stub
          if (nextNode == null) {
            throw new NoSuchElementException();
          }
          SearchNode current = nextNode;
          nextNode = nextNode.getPrevSearchNode();
          return current.getBoard();
        }
      };
    }

  }
}
