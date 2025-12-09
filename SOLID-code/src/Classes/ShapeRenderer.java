package Classes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class ShapeRenderer {
    private Color borderColor;
    private Color fillColor;
    private boolean filled;

    public ShapeRenderer(Color borderColor) {
        this.borderColor = borderColor;
        this.filled = false;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        this.filled = true;
    }

    public void render(Graphics g, ShapeGeometry geometry) {
        Graphics2D g2d = (Graphics2D) g;

        if (filled && fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fill(geometry.getPath());
        }

        g2d.setColor(borderColor);
        g2d.draw(geometry.getPath());
    }

    // Getter methods
    public Color getBorderColor() {
        return borderColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public boolean isFilled() {
        return filled;
    }
}