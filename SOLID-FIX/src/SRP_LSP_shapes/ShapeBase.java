package SRP_LSP_shapes;

import ISP_interface.Copyable;
import ISP_interface.Drawable;
import ISP_interface.HitTest;
import ISP_interface.Movable;

import java.awt.Color;

import DIP_renderer.ShapeRenderer;

public abstract class ShapeBase implements Drawable, Movable, HitTest, Copyable<ShapeBase> {
    private Color lineColor;
    private Color fillColor; // null => not filled

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public boolean isFilled() {
        return fillColor != null;
    }

    protected void applyLineColor(ShapeRenderer renderer) {
        renderer.setColor(getLineColor());
    }

    protected void applyFillColor(ShapeRenderer renderer) {
        renderer.setFillColor(getFillColor());
    }

}
