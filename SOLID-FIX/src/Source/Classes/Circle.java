package Source.Classes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle extends Shapes implements Movable, Fillable {
    public int x1, y1, x2, y2;
    private Color fillColor;
    private boolean filled;

    public Circle(int x1, int y1, int x2, int y2, Color color) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.fillColor = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int diameter = Math.abs(x2 - x1);
        g.drawOval(x1, y1, diameter, diameter);

        if (filled) fill(g);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(fillColor);
        int diameter = Math.abs(x2 - x1);
        ((Graphics2D) g).fillOval(x1, y1, diameter, diameter);
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
        x1 += xShift;
        y1 += yShift;
        x2 += xShift;
        y2 += yShift;
    }

    @Override
    public boolean contains(int x, int y) {
        int centerX = x1 + (x2 - x1) / 2;
        int centerY = y1 + (y2 - y1) / 2;
        int radius = Math.abs(x2 - x1) / 2;
        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return distance <= radius;
    }

    @Override
    public Shapes copy() {
        Circle copy = new Circle(x1, y1, x2, y2, color);
        copy.filled = this.filled;
        copy.fillColor = this.fillColor;
        return copy;
    }

    // Getter dan setter untuk koordinat
    public void setBounds(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}