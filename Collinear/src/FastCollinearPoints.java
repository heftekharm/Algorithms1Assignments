import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
  private ArrayList<LineSegment> segs;
  private LineSegment[] lineSegments;
  
  /**
   * to find all lines contain 4 points at least
   * @param points
   */
  
  public FastCollinearPoints(Point[] points) {
    // finds all line segments containing 4 or more points
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
    // Point[] sortablePoints=Arrays.copyOf(points, points.length);
    for (int i = 0; i < points.length - 1; i++) {
      Point p = points[i];
      Point[] sortablePoints = Arrays.copyOfRange(points, i + 1, points.length);
      Arrays.sort(sortablePoints, p.slopeOrder());
      double prevPointSlope = 0;
      int equalSlopesNumbers = 0;
      for (int j = 1; j < sortablePoints.length; j++) {
        if (j == 1) {
          prevPointSlope = sortablePoints[j - 1].slopeTo(p);
        }
        Point q = sortablePoints[j];
        double slope = p.slopeTo(q);
        if (slope == prevPointSlope) {
          equalSlopesNumbers++;
        } else {
          if (equalSlopesNumbers >= 2) {
            Point endPoint = sortablePoints[j - 1];
            boolean isSlope = false;
            for (int z = 0; z < i; z++) {
              double tempSlope = points[z].slopeTo(p);
              if (tempSlope == prevPointSlope) {
                isSlope = true;
                break;
              }
            }
            if (!isSlope) {
              segs.add(new LineSegment(p, endPoint));
            }
          }
          equalSlopesNumbers = 0;
        }
        prevPointSlope = slope;
      }
      if (equalSlopesNumbers >= 2) {
        Point endPoint = sortablePoints[sortablePoints.length - 1];
        boolean isSlope = false;
        for (int z = 0; z < i; z++) {
          double tempSlope = points[z].slopeTo(p);
          if (tempSlope == p.slopeTo(endPoint)) {
            isSlope = true;
            break;
          }
        }
        if (!isSlope) {
          segs.add(new LineSegment(p, endPoint));
        }
      }
    }
  }

  public int numberOfSegments() {
    // the number of line segments
    return segs.size();
  }

  public LineSegment[] segments() {
    // the line segments
    lineSegments = new LineSegment[segs.size()];
    return segs.toArray(lineSegments);
  }
}
