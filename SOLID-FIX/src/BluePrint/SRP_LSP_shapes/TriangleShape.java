package BluePrint.SRP_LSP_shapes;

import BluePrint.DIP_renderer.ShapeRenderer;
import java.awt.Color;

public class TriangleShape extends ShapeBase {
    private int[] x = new int[3];
    private int[] y = new int[3];

    public TriangleShape(int x1,int y1,int x2,int y2,int x3,int y3, Color lineColor) {
        x[0]=x1; x[1]=x2; x[2]=x3;
        y[0]=y1; y[1]=y2; y[2]=y3;
        setLineColor(lineColor);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        applyLineColor(renderer);
        renderer.drawPolygon(x,y,3);
        if(isFilled()) {
            renderer.setFillColor(getFillColor());
            renderer.fillPolygon(x,y,3);
        }
    }

    @Override
    public void shift(int dx, int dy) {
        for(int i=0;i<3;i++){ x[i]+=dx; y[i]+=dy; }
    }

    @Override
    public boolean contains(int px, int py) {
        int x0 = x[0], y0 = y[0], x1 = x[1], y1 = y[1], x2 = x[2], y2 = y[2];
        int area = Math.abs((x0*(y1-y2) + x1*(y2-y0) + x2*(y0-y1)));
        int a1 = Math.abs((px*(y1-y2) + x1*(y2-py) + x2*(py-y1)));
        int a2 = Math.abs((x0*(py-y2) + px*(y2-y0) + x2*(y0-py)));
        int a3 = Math.abs((x0*(y1-py) + x1*(py-y0) + px*(y0-y1)));
        return (a1 + a2 + a3) == area;
    }

    @Override
    public ShapeBase deepCopy() {
        TriangleShape c = new TriangleShape(x[0],y[0],x[1],y[1],x[2],y[2],getLineColor());
        if(isFilled()) c.setFillColor(getFillColor());
        return c;
    }
}
