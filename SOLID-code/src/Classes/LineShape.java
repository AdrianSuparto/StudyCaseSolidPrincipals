package Classes;

import java.awt.Color;

public class LineShape extends Shape {
    private int x1, y1, x2, y2;

    public LineShape(int x1, int y1, int x2, int y2, Color color) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.geometry = new LineGeometry(x1, y1, x2, y2);
    }

    @Override
    public void updateGeometry() {
        geometry.buildGeometry();
    }

    @Override
    public void shift(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        this.geometry = new LineGeometry(x1, y1, x2, y2);
    }

    @Override
    public Shape deepCopy() {
        LineShape copy = new LineShape(x1, y1, x2, y2, renderer.getBorderColor());
        // Line biasanya tidak di-fill
        return copy;
    }

    // Getters
    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
}