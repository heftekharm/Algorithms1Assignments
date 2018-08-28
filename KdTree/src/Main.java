import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Main {

  public static void main(String[] args) {
    long ft = System.currentTimeMillis();

    for (int i = 0; i < 10000000; i++) {
      new RectHV(0, 0, 1, 1);
    }

    System.out.print(System.currentTimeMillis() - ft);
  }
}
