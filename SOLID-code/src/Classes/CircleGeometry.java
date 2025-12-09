package Classes;

import java.awt.geom.Ellipse2D;

public class CircleGeometry extends ShapeGeometry {
    private int centerX, centerY;
    private int radius;

    public CircleGeometry(int centerX, int centerY, int radius) {
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        buildGeometry();
    }

    @Override
    public void buildGeometry() {
        path.reset();
        Ellipse2D.Float ellipse = new Ellipse2D.Float(
                centerX - radius, centerY - radius,
                radius * 2, radius * 2
        );
        path.append(ellipse, false);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        double dx = x - centerX;
        double dy = y - centerY;
        return (dx * dx + dy * dy) <= (radius * radius);
    }
}