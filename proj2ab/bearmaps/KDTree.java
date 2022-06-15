package bearmaps;
import java.util.List;


public class KDTree implements PointSet {

    private Node root;
    private class Node {
        private Point point;
        private Node left;
        private Node right;

        private boolean isEvenDepth;

        private Node(Point point, boolean isEvenDepth) {
            this.point = point;
            this.left = null;
            this.right = null;
            this.isEvenDepth = isEvenDepth;
        }
    }

    public KDTree(List<Point> points) {
        for (Point i : points) {
            root = put(root, i, true);
        }
    }
    /*
    This put method is for the construction of the k-d tree.
     */
    private Node put(Node x, Point point, boolean isEvenDepth) {
        if (x == null) {
            return new Node(point, isEvenDepth);
        }
        int cmp = compare(x, point);
        if (cmp < 0) {
            x.left = put(x.left, point, !isEvenDepth);
        }
        else {
            x.right = put(x.right, point, !isEvenDepth);
        }

        return x;
    }

    private int compare (Node n, Point goal) {
        if (n.isEvenDepth) {
            if (goal.getX() < n.point.getX()) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else {
            if (goal.getY() < n.point.getY()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    @Override
    public Point nearest (double x, double y) {
        Point goal = new Point(x, y);
        Node best = root;
        best = nearest(root, best, goal);
        return best.point;
    }

    private boolean isBadSideUseful (Node n, Node best, Point goal) {
        Point bestBadSidePoint;
        if (n.isEvenDepth) {
            bestBadSidePoint = new Point(n.point.getX(), goal.getY());
        }
        else {
            bestBadSidePoint = new Point(goal.getX(), n.point.getY());
        }
        if (Point.distance(bestBadSidePoint, goal) < Point.distance(best.point, goal)) {
            return true;
        }
        return false;
    }
    private Node nearest (Node n, Node best, Point goal) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }
        int cmp = compare(n, goal);
        Node goodSide;
        Node badSide;
        if (cmp < 0) {
            goodSide = n.left;
            badSide = n.right;
        }
        else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, best, goal);
        boolean isBadSideUseful = isBadSideUseful(n, best, goal);
        if (isBadSideUseful) {
            best = nearest(badSide, best, goal);
        }
        return best;
    }

    public static void main(String[] args) {
        Point A = new Point(2, 3);
        Point B = new Point(4, 2);
        Point C = new Point(4, 5);
        Point D = new Point(3, 3);
        Point E = new Point(1, 5);
        Point F = new Point(4, 4);

        KDTree t = new KDTree(List.of(A, B, C, D, E, F));
        Point res = t.nearest(0, 7);
        System.out.printf(res.toString());
    }
}
