package Source.Classes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Triangle extends Shapes implements Movable, Fillable {
    public int[] xPoints = new int[3];
    public int[] yPoints = new int[3];
    private Color fillColor;
    private boolean filled;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        super(color);
        setPoints(x1, y1, x2, y2, x3, y3);
        this.fillColor = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawPolygon(xPoints, yPoints, 3);
        if (filled) fill(g);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(fillColor);
        ((Graphics2D) g).fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public boolean isFilled() {
        return filled;
    }

    @Override
    public void shift(int xShift, int yShift) {
        for (int i = 0; i < 3; i++) {
            xPoints[i] += xShift;
            yPoints[i] += yShift;
        }
    }

    @Override
    public boolean contains(int x, int y) {
        Polygon polygon = new Polygon(xPoints, yPoints, 3);
        return polygon.contains(x, y);
    }

    @Override
    public Shapes copy() {
        Triangle copy = new Triangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1],
                xPoints[2], yPoints[2], color);
        copy.filled = this.filled;
        copy.fillColor = this.fillColor;
        return copy;
    }

    public void setPoints(int x1, int y1, int x2, int y2, int x3, int y3) {
        xPoints[0] = x1;
        yPoints[0] = y1;
        xPoints[1] = x2;
        yPoints[1] = y2;
        xPoints[2] = x3;
        yPoints[2] = y3;
    }
}