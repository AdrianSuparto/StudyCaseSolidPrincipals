package Classes;

public class TriangleGeometry extends ShapeGeometry {
    private int x1, y1, x2, y2, x3, y3;

    public TriangleGeometry(int x1, int y1, int x2, int y2, int x3, int y3) {
        super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        buildGeometry();
    }

    @Override
    public void buildGeometry() {
        path.reset();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.closePath();
    }

    @Override
    public boolean containsPoint(int x, int y) {
        // Using barycentric coordinate technique
        float alpha = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) /
                ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        float beta = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) /
                ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        float gamma = 1.0f - alpha - beta;

        return alpha >= 0 && beta >= 0 && gamma >= 0;
    }
}