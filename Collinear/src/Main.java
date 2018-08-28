import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    // read the n points from a file
	    In in = new In("rs1423.txt");
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    StdDraw.setPenRadius(0.004);
		Arrays.sort(points);
	    for (Point p : points) {
		    StdDraw.show();
	        p.draw();
	    }
	    StdDraw.show();
	    //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	        StdDraw.show();
	    }
	    StdDraw.show();
	}

}
