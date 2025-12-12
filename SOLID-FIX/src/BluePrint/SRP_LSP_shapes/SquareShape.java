package BluePrint.SRP_LSP_shapes;

import BluePrint.DIP_renderer.ShapeRenderer;
import java.awt.Color;

public class SquareShape extends ShapeBase {
    private int x,y,size;

    public SquareShape(int x,int y,int size, Color lineColor) {
        this.x=x; this.y=y; this.size=size;
        setLineColor(lineColor);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        applyLineColor(renderer);
        renderer.drawRect(x,y,size,size);
        if(isFilled()) {
            renderer.setFillColor(getFillColor());
            renderer.fillRect(x,y,size,size);
        }
    }

    @Override
    public void shift(int dx, int dy) { x += dx; y += dy; }

    @Override
    public boolean contains(int px, int py) {
        return px >= x && px <= x+size && py >= y && py <= y+size;
    }

    @Override
    public ShapeBase deepCopy() {
        SquareShape c = new SquareShape(x,y,size,getLineColor());
        if(isFilled()) c.setFillColor(getFillColor());
        return c;
    }

    public void setSize(int size) { this.size = size; }
}
