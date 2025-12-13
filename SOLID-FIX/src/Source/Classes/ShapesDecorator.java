package Source.Classes;

import java.awt.Color;
import java.awt.Graphics;

public class ShapesDecorator implements Drawable, Fillable {
    private final Drawable shapes;
    private Color fillColor;
    private boolean filled;

    public ShapesDecorator(Drawable shape, Color fillColor) {
        this.shapes = shape;
        this.fillColor = fillColor;
        this.filled = true;
    }

    @Override
    public void draw(Graphics g) {
        shapes.draw(g);
        if (filled) fill(g);
    }

    @Override
    public void fill(Graphics g) {
        if (shapes instanceof Fillable) {
            ((Fillable) shapes).fill(g);
        }
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
        if (shapes instanceof Fillable) {
            ((Fillable) shapes).setFillColor(color);
        }
    }

    @Override
    public boolean isFilled() {
        return filled;
    }
}