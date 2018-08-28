import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
  private final TreeSet<Point2D> pointsSet = new TreeSet<Point2D>();

  public PointSET() {
    // construct an empty set of points
  }

  public boolean isEmpty() {
    // is the set empty?
    return pointsSet.isEmpty();
  }

  public int size() {
    // number of points in the set
    return pointsSet.size();
  }

  public void insert(Point2D p) {
    // add the point to the set (if it is not already in the set)
    pointsSet.add(p);
  }

  public boolean contains(Point2D p) {
    // does the set contain point p
    return pointsSet.contains(p);
  }

  public void draw() {
    // draw all points to standard draw
    for (Point2D point : pointsSet) {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      StdDraw.point(point.x(), point.y());
    }

  }

  public Iterable<Point2D> range(RectHV rect) {
    // all points that are inside the rectangle (or on the boundary)
    TreeSet<Point2D> inclusivePoints = new TreeSet<Point2D>();
    for (Point2D p : pointsSet) {
      if (rect.contains(p)) {
        inclusivePoints.add(p);
      }

    }
    return inclusivePoints;
  }

  public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to point p; null if the set is empty
    Point2D nearestPoint = null;
    double nearestDistance = 0;
    for (Point2D point : pointsSet) {
      if (nearestPoint == null) {
        nearestPoint = point;
        nearestDistance = point.distanceSquaredTo(p);
      }
      double distance = point.distanceSquaredTo(p);
      if (distance < nearestDistance) {
        nearestDistance = distance;
        nearestPoint = point;
      }
    }
    return nearestPoint;
  }
}
