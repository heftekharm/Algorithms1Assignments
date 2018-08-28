import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
  private static final byte VER = 1;
  private static final byte HOR = -1;

  private static class Node {
    private final Point2D point;
    private final RectHV rect;
    private Node rtn;
    private Node lbn;
    private final byte ax;

    public Node(Point2D p, RectHV r, byte ax) {
      this.point = p;
      this.rect = r;
      this.ax = ax;
    }
  }

  private Node root;
  private int size;

  public KdTree() {
    // construct an empty set of points
    root = null;
    size = 0;
  }

  public boolean isEmpty() {
    // is the set empty?
    return root == null;
  }

  public int size() {
    // number of points in the set
    if (isEmpty()) {
      return 0;
    }

    //int size = increaseSize(root, 0);
    return size;

  }

  private int increaseSize(Node node, int size) {
    if (node == null) {
      return size;

    }
    size = increaseSize(node.rtn, size);
    size = increaseSize(node.lbn, size);
    return size + 1;
  }

  /*
   * public void insert(Point2D p) { // add the point to the set (if it is not
   * already in the set) root = insert(root, p, new RectHV(0, 0, 1, 1), VER); }
   * 
   * private Node insert(Node node, Point2D p, RectHV rect, boolean ax) { if (node
   * == null) { return new Node(p, rect, ax); }
   * 
   * if (node.point.equals(p)) { return node; }
   * 
   * double xmin = node.rect.xmin(); double xmax = node.rect.xmax(); double ymin =
   * node.rect.ymin(); double ymax = node.rect.ymax();
   * 
   * if (node.ax == VER) { if (p.x() >= node.point.x()) { node.rtn =
   * insert(node.rtn, p, new RectHV(node.point.x(), ymin, xmax, ymax), HOR); }
   * else { node.lbn = insert(node.lbn, p, new RectHV(xmin, ymin, node.point.x(),
   * ymax), HOR); } } else { // So Its HOR if (p.y() >= node.point.y()) { node.rtn
   * = insert(node.rtn, p, new RectHV(xmin, node.point.y(), xmax, ymax), VER);
   * 
   * } else { node.lbn = insert(node.lbn, p, new RectHV(xmin, ymin, xmax,
   * node.point.y()), VER);
   * 
   * }
   * 
   * } return node;
   * 
   * }
   */

  public void insert(Point2D p) {
    // add the point to the set (if it is not already in the set)
    if (root == null) {
      root = new Node(p, new RectHV(0, 0, 1, 1), VER);
      size++;
    } else {
      root = insert(root, p);
    }
  }

  private Node insert(Node node, Point2D p) {
    if (node.point.equals(p)) {
      return node;
    }

    if (node.ax == VER) {
      if (p.x() >= node.point.x()) {
        node.rtn = node.rtn == null ? childIsNull(node, p, HOR, 1) : insert(node.rtn, p);
      } else {
        node.lbn = node.lbn == null ? childIsNull(node, p, HOR, 3) : insert(node.lbn, p);
      }
    } else {
      // So Its HOR
      if (p.y() >= node.point.y()) {
        node.rtn = node.rtn == null ? childIsNull(node, p, VER, 0) : insert(node.rtn, p);

      } else {
        node.lbn = node.lbn == null ? childIsNull(node, p, VER, 2) : insert(node.lbn, p);

      }

    }
    return node;

  }

  private Node childIsNull(Node parentnode, Point2D p, byte ax, int pos) {
    // pos is clockwise 0 1 2 3:TOP RIGHT BOTOOM LEFT
    // if pos is 5 it means root node
    double xmin = pos == 1 ? parentnode.point.x() : parentnode.rect.xmin();
    double xmax = pos == 3 ? parentnode.point.x() : parentnode.rect.xmax();
    double ymin = pos == 0 ? parentnode.point.y() : parentnode.rect.ymin();
    double ymax = pos == 2 ? parentnode.point.y() : parentnode.rect.ymax();
    size++;
    return new Node(p, new RectHV(xmin, ymin, xmax, ymax), ax);
  }

  public boolean contains(Point2D p) {
    boolean res = nodeContainsPoint(root, p);
    return res;
  }

  private boolean nodeContainsPoint(Node node, Point2D point) {
    if (node == null) {
      return false;
    }
    if (node.point.equals(point)) {
      return true;
    }
    if (node.ax == VER) {
      if (point.x() > node.point.x()) {
        return nodeContainsPoint(node.rtn, point);

      } else {
        return nodeContainsPoint(node.lbn, point);

      }
    }
    if (node.ax == HOR) {
      if (point.y() > node.point.y()) {
        return nodeContainsPoint(node.rtn, point);

      } else {
        return nodeContainsPoint(node.lbn, point);

      }
    }
    return false;

  }

  public void draw() {
    draw(root);
  }

  private void draw(Node node) {
    if (node == null) {

      return;
    }
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    StdDraw.point(node.point.x(), node.point.y());
    if (node.ax == VER) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.setPenRadius();
      StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.setPenRadius();
      StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
    }
    // StdDraw.setPenColor(StdDraw.BLACK);
    // StdDraw.setPenRadius(0.02);
    // StdDraw.point(0.5+d, 1-Math.abs(d));
    // StdDraw.line(x0, , x1, y1);
    draw(node.lbn);
    draw(node.rtn);

  }

  /**
   * 
   * @param rect
   *          the rectangle to be searched
   * @return all points that are inside the rectangle
   */

  public Iterable<Point2D> range(RectHV rect) {
    // all points that are inside the rectangle (or on the boundary)
    ArrayList<Point2D> results = new ArrayList<>();
    pointIsInsideRect(results, root, rect);

    return results;
  }

  private void pointIsInsideRect(ArrayList<Point2D> results, Node node, RectHV rect) {
    if (node == null) {
      return;

    }
    if (rect.contains(node.point)) {
      results.add(node.point);

    }
    if (node.ax == HOR) {
      if (rect.ymax() > node.point.y()) {
        pointIsInsideRect(results, node.rtn, rect);

      }
      if (rect.ymin() < node.point.y()) {
        pointIsInsideRect(results, node.lbn, rect);

      }
    } else if (node.ax == VER) {
      if (rect.xmax() > node.point.x()) {
        pointIsInsideRect(results, node.rtn, rect);

      }
      if (rect.xmin() < node.point.x()) {
        pointIsInsideRect(results, node.lbn, rect);

      }
    }

  }

  /**
   * finds the nearest to point to query point
   * 
   * @param p
   *          query point
   * @return nearest point to query point
   */

  public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to point p; null if the set is empty
    Node nearest = findNearest(root, root, p);

    return nearest != null ? nearest.point : null;

  }

  private Node findNearest(Node node, Node nearest, Point2D qp) {
    if (node == null) {
      return nearest;

    }
    double nearestPointDistance = nearest.point.distanceSquaredTo(qp);
    if (node.rect.distanceSquaredTo(qp) <= nearestPointDistance) {
      if (node.point.distanceSquaredTo(qp) < nearestPointDistance) {
        nearest = node;
      }
      boolean serachRtFirst = node.ax == VER && qp.x() - node.point.x() > 0;
      serachRtFirst = serachRtFirst || ((node.ax == HOR) && (qp.y() - node.point.y() > 0));

      if (serachRtFirst) {
        nearest = findNearest(node.rtn, nearest, qp);
        nearest = findNearest(node.lbn, nearest, qp);
      } else {
        nearest = findNearest(node.lbn, nearest, qp);
        nearest = findNearest(node.rtn, nearest, qp);
      }

    }

    return nearest;

  }

}
