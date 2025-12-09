package Classes;

import java.awt.Color;

public class TriangleShape extends Shape {
    private int x1, y1, x2, y2, x3, y3;

    public TriangleShape(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.geometry = new TriangleGeometry(x1, y1, x2, y2, x3, y3);
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
        x3 += dx;
        y3 += dy;
        this.geometry = new TriangleGeometry(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public Shape deepCopy() {
        TriangleShape copy = new TriangleShape(x1, y1, x2, y2, x3, y3,
                renderer.getBorderColor());
        if (renderer.isFilled()) {
            copy.setFillColor(renderer.getFillColor());
        }
        return copy;
    }

    // Getters
    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    public int getX3() { return x3; }
    public int getY3() { return y3; }
}