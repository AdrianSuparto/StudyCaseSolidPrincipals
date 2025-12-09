package Classes;

public class LineGeometry extends ShapeGeometry {
    private int x1, y1, x2, y2;

    public LineGeometry(int x1, int y1, int x2, int y2) {
        super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        buildGeometry();
    }

    @Override
    public void buildGeometry() {
        path.reset();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        // Simplified hit detection for line
        double distance = pointToLineDistance(x, y, x1, y1, x2, y2);
        return distance < 3.0; // 3 pixels tolerance
    }

    private double pointToLineDistance(int px, int py,
                                       int x1, int y1, int x2, int y2) {
        double A = px - x1;
        double B = py - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double lenSq = C * C + D * D;
        double param = (lenSq != 0) ? dot / lenSq : -1;

        double xx, yy;

        if (param < 0) {
            xx = x1;
            yy = y1;
        } else if (param > 1) {
            xx = x2;
            yy = y2;
        } else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }

        double dx = px - xx;
        double dy = py - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }
}