package BluePrint.SRP_LSP_shapes;

import BluePrint.ISP_interface.Copyable;
import BluePrint.ISP_interface.Drawable;
import BluePrint.ISP_interface.HitTest;
import BluePrint.ISP_interface.Movable;
import BluePrint.ISP_interface.*;
import java.awt.Color;
import BluePrint.DIP_renderer.ShapeRenderer;

public abstract class ShapeBase implements Drawable, Movable, HitTest, Copyable<ShapeBase> {
    private Color lineColor;
    private Color fillColor; // null => not filled

    public Color getLineColor() { return lineColor; }
    public void setLineColor(Color lineColor) { this.lineColor = lineColor; }

    public Color getFillColor() { return fillColor; }
    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

    public boolean isFilled() { return fillColor != null; }

    // shortcut to draw via renderer inside concrete classes
    protected void applyLineColor(ShapeRenderer renderer) {
        renderer.setColor(getLineColor());
    }

    protected void applyFillColor(ShapeRenderer renderer) {
        renderer.setFillColor(getFillColor());
    }

    // concrete shapes implement draw, shift, contains, deepCopy
}
