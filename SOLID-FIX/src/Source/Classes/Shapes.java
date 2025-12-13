package Source.Classes;

import java.awt.Color;

public abstract class Shapes implements Drawable, Movable {
    protected Color color;

    public Shapes(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract boolean contains(int x, int y);

    public abstract Shapes copy();
}