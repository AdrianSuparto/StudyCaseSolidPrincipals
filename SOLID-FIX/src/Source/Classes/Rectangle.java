package Source.Classes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangle extends Shapes implements Movable, Fillable {
    public int x1, y1, x2, y2;
    private Color fillColor;
    private boolean filled;

    public Rectangle(int x1, int y1, int x2, int y2, Color color) {
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
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);

        g.drawRect(startX, startY, width, height);

        if (filled) fill(g);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(fillColor);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);
        ((Graphics2D) g).fillRect(startX, startY, width, height);
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
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    @Override
    public Shapes copy() {
        Rectangle copy = new Rectangle(x1, y1, x2, y2, color);
        copy.filled = this.filled;
        copy.fillColor = this.fillColor;
        return copy;
    }

    public void setBounds(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}