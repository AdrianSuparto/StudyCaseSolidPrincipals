package SRP_LSP_shapes;

import DIP_renderer.ShapeRenderer;
import java.awt.Color;

public class RectangleShape extends ShapeBase {
    private int x1, y1, x2, y2;

    public RectangleShape(int x1, int y1, int x2, int y2, Color lineColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        setLineColor(lineColor);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        applyLineColor(renderer);
        int x = Math.min(x1, x2), y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1), h = Math.abs(y2 - y1);
        renderer.drawRect(x, y, w, h);
        if (isFilled()) {
            renderer.setFillColor(getFillColor());
            renderer.fillRect(x, y, w, h);
        }
    }

    @Override
    public void shift(int dx, int dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

    @Override
    public boolean contains(int x, int y) {
        int minx = Math.min(x1, x2), maxx = Math.max(x1, x2);
        int miny = Math.min(y1, y2), maxy = Math.max(y1, y2);
        return x >= minx && x <= maxx && y >= miny && y <= maxy;
    }

    @Override
    public ShapeBase deepCopy() {
        RectangleShape c = new RectangleShape(x1, y1, x2, y2, getLineColor());
        if (isFilled()) c.setFillColor(getFillColor());
        return c;
    }
}
