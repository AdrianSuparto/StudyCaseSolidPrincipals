package Classes;

import java.awt.Color;

public class CircleShape extends Shape {
    private int centerX, centerY;
    private int radius;

    public CircleShape(int x, int y, int diameter, Color color) {
        super(color);
        this.radius = diameter / 2;  // ✅ diameter → radius
        this.centerX = x + radius;    // ✅ x adalah koordinat kiri
        this.centerY = y + radius;    // ✅ y adalah koordinat atas
        this.geometry = new CircleGeometry(centerX, centerY, radius);
    }

    @Override
    public void updateGeometry() {
        geometry.buildGeometry();
    }

    @Override
    public void shift(int dx, int dy) {
        centerX += dx;
        centerY += dy;
        this.geometry = new CircleGeometry(centerX, centerY, radius);
    }

    @Override
    public Shape deepCopy() {
        CircleShape copy = new CircleShape(
                centerX - radius,
                centerY - radius,
                radius * 2,  // ✅ Perbaikan: diameter = radius * 2
                renderer.getBorderColor()
        );
        if (renderer.isFilled()) {
            copy.setFillColor(renderer.getFillColor());
        }
        return copy;
    }

    // Getters
    public int getCenterX() { return centerX; }
    public int getCenterY() { return centerY; }
    public int getRadius() { return radius; }
}