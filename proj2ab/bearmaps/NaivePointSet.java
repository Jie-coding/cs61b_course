package bearmaps;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NaivePointSet implements PointSet {
    Set<Point> SetofPoints;

    public NaivePointSet(List<Point> points) {
        SetofPoints = new HashSet<>();
        for (Point i : points) {
            SetofPoints.add(i);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point best = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Point target = new Point(x, y);
        for (Point i : SetofPoints) {
            if (Point.distance(target, i) < Point.distance(target, best)) {
                best = i;
            }
        }
        return best;
    }



    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.toString());
    }
}
