package SRP_LSP_shapes;

import DIP_renderer.ShapeRenderer;

import java.awt.Color;

public class LineShape1 extends ShapeBase {
    private int x1,y1,x2,y2;

    public LineShape(int x1,int y1,int x2,int y2, Color color) {
        this.x1=x1; this.y1=y1; this.x2=x2; this.y2=y2;
        setLineColor(color);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        applyLineColor(renderer);
        renderer.drawLine(x1,y1,x2,y2);
    }

    @Override
    public boolean contains(int px, int py) {
        double dx = x2 - x1, dy = y2 - y1;
        double length2 = dx*dx + dy*dy;
        if(length2 == 0) return px==x1 && py==y1;
        double t = ((px - x1) * dx + (py - y1) * dy) / length2;
        if(t < 0 || t > 1) return false;
        double projx = x1 + t * dx;
        double projy = y1 + t * dy;
        double dist2 = (px - projx)*(px - projx) + (py - projy)*(py - projy);
        return dist2 <= 9.0; // tolerance (3px)^2
    }

    @Override
    public void shift(int dx, int dy) { x1+=dx; x2+=dx; y1+=dy; y2+=dy; }

    @Override
    public ShapeBase deepCopy() {
        LineShape c = new LineShape(x1,y1,x2,y2,getLineColor());
        return c;
    }

    public void setX2(int x2) { this.x2 = x2; }
    public void setY2(int y2) { this.y2 = y2; }
}
