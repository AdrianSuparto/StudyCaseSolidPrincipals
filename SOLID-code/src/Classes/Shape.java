package Classes;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
    protected ShapeGeometry geometry;
    protected ShapeRenderer renderer;

    public Shape(Color borderColor) {
        this.renderer = new ShapeRenderer(borderColor);
    }

    public abstract void updateGeometry();

    public void draw(Graphics g) {
        updateGeometry();
        renderer.render(g, geometry);
    }

    public void setFillColor(Color color) {
        renderer.setFillColor(color);
    }

    public boolean contains(int x, int y) {
        return geometry.containsPoint(x, y);
    }

    public abstract void shift(int x, int y);
    // Tambahkan method getter untuk renderer di kelas Shape
    public ShapeRenderer getRenderer() {
        return renderer;
    }
    public abstract Shape deepCopy();
}