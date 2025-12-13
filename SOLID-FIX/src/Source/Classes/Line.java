package Source.Classes;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shapes implements  Movable {
    public int x1, y1, x2, y2;

    public Line(int x1, int y1, int x2, int y2, Color color) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void shift(int xShift, int yShift) {
        x1 += xShift;
        y1 += yShift;
        x2 += xShift;
        y2 += yShift;
    }

    @Override
    public boolean contains(int x, int y) {
        double tolerance = 3.0;
        double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) /
                Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return distance <= tolerance;
    }

    @Override
    public Shapes copy() {
        return new Line(x1, y1, x2, y2, color);
    }

    public void setEndPoints(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}