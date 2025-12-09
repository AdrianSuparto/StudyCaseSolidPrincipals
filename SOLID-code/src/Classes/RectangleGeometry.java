package Classes;

public class RectangleGeometry extends ShapeGeometry {
    private int x1, y1, x2, y2;

    public RectangleGeometry(int x1, int y1, int x2, int y2) {
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
        path.lineTo(x1, y2);
        path.lineTo(x2, y2);
        path.lineTo(x2, y1);
        path.closePath();
    }
}