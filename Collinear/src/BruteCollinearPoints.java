import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private ArrayList<LineSegment> segs;
  private LineSegment[] lineSegments;
  /**
   * to find all lines contain 4 points
   * @param points
   */
  
  public BruteCollinearPoints(Point[] points) { // finds all line segments
    if (points == null) {
      throw new NullPointerException();
    }
    Arrays.sort(points);
    for (int i = 0; i < points.length - 1; i++) {
      Point currentPoint = points[i];
      Point nextPoint = points[i + 1];
      // if (currentPoint==null || nextPoint==null) throw new
      // NullPointerException();
      if (currentPoint.compareTo(nextPoint) == 0) {
        throw new IllegalArgumentException();
      }
    }
    segs = new ArrayList<LineSegment>();
    for (int i = 0; i < points.length; i++) {
      Point endPoint = null;
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          for (int l = k + 1; l < points.length; l++) {
            Point ip = points[i], 
                jp = points[j], 
                kp = points[k], 
                lp = points[l];
            double sij = ip.slopeTo(jp);
            double sik = ip.slopeTo(kp);
            double sil = ip.slopeTo(lp);
            if (sij == sik && sij == sil) {
              Point[] unsortedPoints = { ip, jp, kp, lp };
              Arrays.sort(unsortedPoints);
              if (endPoint != unsortedPoints[3]) {
                endPoint = unsortedPoints[3];
                segs.add(new LineSegment(unsortedPoints[0], endPoint));
              }
            }
          }
        }
      }
    }

  }

  public int numberOfSegments() { // the number of line segments
    return segs.size();
  }

  public LineSegment[] segments() { // the line segments
    lineSegments = new LineSegment[segs.size()];
    return segs.toArray(lineSegments);
  }
}
