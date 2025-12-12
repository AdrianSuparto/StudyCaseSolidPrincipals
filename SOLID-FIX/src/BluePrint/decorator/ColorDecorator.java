package BluePrint.decorator;

import BluePrint.SRP_LSP_shapes.ShapeBase;
import BluePrint.DIP_renderer.ShapeRenderer;
import java.awt.Color;

/**
 * Simple BluePrint.app.decorator that temporarily overrides line color (without mutating original).
 */
public class ColorDecorator extends ShapeBase {
    private final ShapeBase inner;
    private final Color overrideLine;

    public ColorDecorator(ShapeBase inner, Color overrideLine) {
        this.inner = inner;
        this.overrideLine = overrideLine;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        Color old = inner.getLineColor();
        inner.setLineColor(overrideLine);
        inner.draw(renderer);
        inner.setLineColor(old);
    }

    @Override
    public void shift(int dx, int dy) { inner.shift(dx,dy); }

    @Override
    public boolean contains(int x, int y) { return inner.contains(x,y); }

    @Override
    public ShapeBase deepCopy() { return inner.deepCopy(); }
}
