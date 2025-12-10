package Classes;

import java.awt.Color;

public class SquareShape extends Shape {
    private int x, y;
    private int size;

    public SquareShape(int x, int y, int size, Color color) {
        super(color);
        this.x = x;
        this.y = y;
        this.size = size;
        this.geometry = new SquareGeometry(x, y, size);
    }

    @Override
    public void updateGeometry() {
        geometry.buildGeometry();
    }

    @Override
    public void shift(int dx, int dy) {
        x += dx;
        y += dy;
        this.geometry = new SquareGeometry(x, y, size);
    }

    @Override
    public Shape deepCopy() {
        SquareShape copy = new SquareShape(x, y, size,
                renderer.getBorderColor());
        if (renderer.isFilled()) {
            copy.setFillColor(renderer.getFillColor());
        }
        return copy;
    }

    // Tambahkan method getter di kelas SquareShape
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}