package BluePrint.SRP_LSP_shapes;

import BluePrint.DIP_renderer.ShapeRenderer;
import java.awt.Color;

public class CircleShape extends ShapeBase {
    private int x, y, radius; // x,y = top-left of bounding square

    public CircleShape(int x, int y, int radius, Color lineColor) {
        this.x = x; this.y = y; this.radius = radius;
        setLineColor(lineColor);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        applyLineColor(renderer);
        int d = radius*2;
        renderer.drawOval(x,y,d,d);
        if(isFilled()) {
            renderer.setFillColor(getFillColor());
            renderer.fillOval(x,y,d,d);
        }
    }

    @Override
    public boolean contains(int px, int py) {
        double cx = x + radius;
        double cy = y + radius;
        double dist2 = Math.pow(px - cx, 2) + Math.pow(py - cy, 2);
        return dist2 <= Math.pow(radius, 2);
    }

    @Override
    public void shift(int dx, int dy) { x += dx; y += dy; }

    @Override
    public ShapeBase deepCopy() {
        CircleShape c = new CircleShape(x,y,radius,getLineColor());
        if(isFilled()) c.setFillColor(getFillColor());
        return c;
    }

    public void setRadius(int r) { this.radius = r; }
}
